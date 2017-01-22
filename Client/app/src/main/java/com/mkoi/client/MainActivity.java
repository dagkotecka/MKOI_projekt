package com.mkoi.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendFileBtn = (Button)findViewById(R.id.sendFileBtn);
        loadFileBtn = (Button)findViewById(R.id.loadFileBtn);
        listServerBtn = (Button)findViewById(R.id.listServerFilesBtn);
        clientListView = (ListView)findViewById(R.id.clientFilesListView);
        serverListView = (ListView)findViewById(R.id.serverFilesListView);
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

}
