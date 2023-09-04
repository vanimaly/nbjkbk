import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) {
        try {
            int port = 12345;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) throws IOException {
        try {
            // Create a fixed secret key for encryption and decryption
            String secretKeyStr = "1234567896321569"; // Replace with your secret key
            SecretKey secretKey = new SecretKeySpec(secretKeyStr.getBytes(StandardCharsets.UTF_8), "AES");

            // Create input and output streams
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            // Read the encrypted message from the client
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            byte[] encryptedMessage = (byte[]) objectInputStream.readObject();

            // Decrypt the message
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedMessage = cipher.doFinal(encryptedMessage);

            // Convert the decrypted bytes to a string
            String plaintextMessage = new String(decryptedMessage, StandardCharsets.UTF_8);

            // Display the decrypted message
            System.out.println("Decrypted Message: " + plaintextMessage);

            // Close the client connection
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
