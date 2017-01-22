package com.mkoi.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class MainActivity extends AppCompatActivity {

    Button sendFileBtn, loadFileBtn, listServerBtn;
    ListView clientListView, serverListView;
    private ObjectInputStream sInput; // to read from the socket
    private ObjectOutputStream sOutput; // to write on the socket
    public Socket socket;
    SecretKey clientPrivateKey;
    SecretKey serverPrivateKey;
    String encryptedString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendFileBtn = (Button)findViewById(R.id.sendFileBtn);
        loadFileBtn = (Button)findViewById(R.id.loadFileBtn);
        listServerBtn = (Button)findViewById(R.id.listServerFilesBtn);
        clientListView = (ListView)findViewById(R.id.clientFilesListView);
        serverListView = (ListView)findViewById(R.id.serverFilesListView);
        try {

            clientPrivateKey = getPrivateKey(getClientKey("client.der"));
            serverPrivateKey = getPrivateKey(getClientKey("server.der"));

            //SecretKey skClient = getPrivateKey(clientPrivateKey);
            //SecretKey skServer = getPrivateKey(serverPrivateKey);


            AESHelper aesHelper = new AESHelper(serverPrivateKey, clientPrivateKey);
            String encrypted = aesHelper.Encrypt();

            System.out.println(encrypted);
            System.out.println(aesHelper.Decrypt(encrypted));

        } catch (Exception e) {
            e.printStackTrace();
        }
        connect();



    }
    public void connect() {
        Runnable test = new Runnable() {

            public void run() {

                InetAddress serverAddr = null;
                try {
                    serverAddr = InetAddress.getByName("10.0.2.2");
                } catch (UnknownHostException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Log.i("ClientActivity", "C: Connecting...");
                try {
                    socket = new Socket("10.0.2.2", 8888);
                    DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());

                    // Send first message
                    dOut.writeByte(1);
                    dOut.writeUTF("This is the first type of message.");
                    dOut.flush(); // Send off the data                }

                } catch (Exception e) {
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



        //File keyFile = new File(fileName);
        //DataInputStream dateInputStream = new DataInputStream(new FileInputStream(keyFile));
        byte[] keyBytes = value.getBytes("UTF-8");


        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        keyBytes = sha.digest(keyBytes);

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        return secretKeySpec;
    }
}


