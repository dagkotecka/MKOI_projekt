package com.mkoi.client;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.MessageDigest;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;


public class MainActivity extends AppCompatActivity {

    Button sendFileBtn, loadFileBtn, listServerBtn;
    ListView clientListView, serverListView;
    private ObjectInputStream sInput; // to read from the socket
    private ObjectOutputStream sOutput; // to write on the socket
    public Socket socket;
    SecretKey clientPrivateKey;
    SecretKey serverPrivateKey;
    String encryptedString;

    private static String password = "projekt";
    static final int portNumber = 8888;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendFileBtn = (Button) findViewById(R.id.sendFileBtn);
        loadFileBtn = (Button) findViewById(R.id.loadFileBtn);
        listServerBtn = (Button) findViewById(R.id.listServerFilesBtn);
        clientListView = (ListView) findViewById(R.id.clientFilesListView);
        serverListView = (ListView) findViewById(R.id.serverFilesListView);
        //try {

        //clientPrivateKey = getPrivateKey(getClientKey("client.der"));
        //serverPrivateKey = getPrivateKey(getClientKey("server.der"));


        //AESHelper aesHelper = new AESHelper(serverPrivateKey, clientPrivateKey);
        //String encrypted = aesHelper.Encrypt();

        //System.out.println(encrypted);
        //System.out.println(aesHelper.Decrypt(encrypted));

        //} catch (Exception e) {
        //    e.printStackTrace();
        //}

        System.setProperty("javax.net.ssl.trustStore", String.valueOf(getApplicationContext().getResources().openRawResource(R.raw.trust)));
        System.setProperty("javax.net.ssl.trustStorePassword", "projekt");

        connect();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void connect() {
        Runnable test = new Runnable() {

            public void run() {


                SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
                try {
                    socket = (SSLSocket) sslSocketFactory.createSocket("10.0.2.2", 8888);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                OutputStream outputStream = null;
                try {
                    outputStream = socket.getOutputStream();
                    outputStream.write("Hello MotherFucker".getBytes());
                    outputStream.flush();
                    socket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };

        new Thread(test).start();
    }

    public String getClientKey(String fileName) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(fileName)));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static SecretKey getPrivateKey(String value) throws Exception {
        byte[] keyBytes = value.getBytes();

        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        keyBytes = sha.digest(keyBytes);

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        return secretKeySpec;
    }



    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
