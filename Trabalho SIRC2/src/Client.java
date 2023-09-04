import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) {
        try {
            String serverAddress = "127.0.0.1"; // Replace with the server's IP address
            int serverPort = 12345;

            // Create a fixed secret key for encryption and decryption
            String secretKeyStr = "1234567896321569"; // Replace with your secret key
            SecretKey secretKey = new SecretKeySpec(secretKeyStr.getBytes(StandardCharsets.UTF_8), "AES");

            // Create a socket to connect to the server
            Socket socket = new Socket(serverAddress, serverPort);

            // Create input and output streams
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            // Read the text message from a file
            String messageFilePath = "C:\\Users\\J3I3L CH0V3QU3\\IdeaProjects\\Trabalho SIRC2\\message.txt";
            String message = readTextFromFile(messageFilePath);

            // Encrypt the message
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));

            // Send the encrypted message to the server
            objectOutputStream.writeObject(encryptedData);

            // Close the socket
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readTextFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append("\n"); // Add newline character between lines
        }
        reader.close();
        return stringBuilder.toString();
    }
}
