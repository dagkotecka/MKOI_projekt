import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.*;

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
        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CTS/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, privateServerKey, ivspec);
        byte[] encryptedByteClientKey = cipher.doFinal(privateClientKey.getEncoded());
        return new BASE64Encoder().encode(encryptedByteClientKey);
    }

    public String Decrypt(String encryptedMessage) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CTS/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateServerKey, ivspec);
        byte[] decodedByteClientKey = new BASE64Decoder().decodeBuffer(encryptedMessage);
        byte[] decryptedByteClientKey = cipher.doFinal(decodedByteClientKey);
        return new String(decryptedByteClientKey);

    }
}
