package com.mkoi.client;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * Created by Dax on 22.01.2017.
 */

public class AESHelper {


    SecretKey privateServerKey;
    SecretKey privateClientKey;

    public AESHelper(SecretKey serverKey, SecretKey clientKey){
        this.privateClientKey = clientKey;
        this.privateServerKey = serverKey;
    }

    public String Encrypt() throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {

        Cipher cipher = Cipher.getInstance("AES");

        System.out.println("Private Client Key: " + Base64Own.encode(privateClientKey.getEncoded()).toString());
        System.out.println("Private Server Key: " + Base64Own.encode(privateServerKey.getEncoded()).toString());

        cipher.init(Cipher.ENCRYPT_MODE, privateServerKey);
        byte[] encryptedByteClientKey = cipher.doFinal(privateClientKey.getEncoded());
        return Base64Own.encode(encryptedByteClientKey);
    }

    public String Decrypt(String encryptedMessage) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, privateServerKey);
        byte[] decodedByteClientKey = Base64Own.decode(encryptedMessage);
        byte[] decryptedByteClientKey = cipher.doFinal(decodedByteClientKey);
        return Base64Own.encode(decryptedByteClientKey);

    }
}
