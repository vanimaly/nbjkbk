import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Server {
    public static void main(String[] args) {
        try {
            // Start the server
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Esperando a Conexao ...");

            // Accept a client connection
            Socket clientSocket = serverSocket.accept();
            InputStream inputStream = clientSocket.getInputStream();

            // Receive the AES key from the client
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            SecretKey secretKey = (SecretKey) objectInputStream.readObject();

            // Decrypt and display the message
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encryptedMessage = inputStream.readAllBytes();
            byte[] decryptedMessage = cipher.doFinal(encryptedMessage);
            String plaintextMessage = new String(decryptedMessage, StandardCharsets.UTF_8);

            System.out.println("Received Message: " + plaintextMessage);
            System.out.println("AES Key: " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
            System.out.println("Encrypted Message: " + Base64.getEncoder().encodeToString(encryptedMessage));

            // Close the connection
            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
