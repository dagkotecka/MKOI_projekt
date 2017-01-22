/**
 * Created by Dax on 20.01.2017.
 */

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;

//import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        //get("/hello", (req, res) -> "Hello World");
        try {
            SecretKey privKeyServer = getPrivateKey((System.getProperty("user.dir")) +"/src/main/java/server.der");
            SecretKey privKeyClient = getPrivateKey((System.getProperty("user.dir")) +"/src/main/java/client.der");
            AESHelper aesHelper = new AESHelper(privKeyServer, privKeyClient);

            String enc = aesHelper.Encrypt();
            System.out.println("ENCRYPTED: " + enc);
            System.out.println("DECRYPTED: " + aesHelper.Decrypt(enc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SecretKey getPrivateKey(String fileName) throws Exception {

        File keyFile = new File(fileName);
        DataInputStream dateInputStream = new DataInputStream(new FileInputStream(keyFile));
        byte[] keyBytes = new byte[(int) keyFile.length()];
        dateInputStream.readFully(keyBytes);
        dateInputStream.close();

        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        keyBytes = sha.digest(keyBytes);

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        return secretKeySpec;
    }
}

