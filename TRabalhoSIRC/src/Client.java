import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) {
        try {
            // Generate a random AES key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecretKey secretKey = keyGenerator.generateKey();

            // ESTABELECENDOA CONEXAO COM O SERVIDOR
            Socket socket = new Socket("localhost", 12345);
            OutputStream outputStream = socket.getOutputStream();

            // ENVIAR A CHAVE  AES PARA O SERVIDOR
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(secretKey);

            // SIFRAR E MANDAR SMS PARA O SERVIDOR
            String plaintextMessage = "J3I3L J0S3 CH0V3QU3, BEM VINDO!";

            Cipher cipher = Cipher.getInstance("AES");
            System.out.println(cipher);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedMessage = cipher.doFinal(plaintextMessage.getBytes(StandardCharsets.UTF_8));
            outputStream.write(encryptedMessage);

            System.out.println("plaintextMessage : " + plaintextMessage);
//            System.out.println("AES Chave: " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
//            System.out.println("Mensagem Sifrada: " + Base64.getEncoder().encodeToString(encryptedMessage));

            // Close the connection
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
