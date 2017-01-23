import com.sun.imageio.spi.OutputStreamImageOutputStreamSpi;

import javax.net.ssl.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Dax on 22.01.2017.
 */
public class Main {
    private static String keyStoreFilePath = System.getProperty("user.dir") + "/src/trust.jks";
    private static String password = "projekt";
    static final int portNumber = 8888;
    public static void main(String[] arstring) {
        System.setProperty("javax.net.ssl.keyStore", keyStoreFilePath);
        System.setProperty("javax.net.ssl.keyStorePassword", password);

        try {
            connect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    static void connect() throws IOException, NoSuchAlgorithmException {
        SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket serverSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(portNumber);
        SSLContext sc = SSLContext.getInstance("TLSv1.2");
        SSLSocket socket = (SSLSocket) serverSocket.accept();
        socket.setEnabledCipherSuites(sc.getServerSocketFactory().getSupportedCipherSuites());

        InputStream is = socket.getInputStream();

        OutputStream os = socket.getOutputStream();

        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        String out = null;
        while((out = bf.readLine()) != null){
            System.out.println(out);
            System.out.flush();
        }

        socket.close();
    }
}
