/**
 * Created by Dax on 20.01.2017.
 */
import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

//import static spark.Spark.*;

public class Main {
    public static void main(String[] args) throws Exception {
        //get("/hello", (req, res) -> "Hello World");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
    }

    public static String getKey(String fileName) throws Exception {


        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String everything;
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } finally {
            br.close();
        }
        return everything;
    }
}

