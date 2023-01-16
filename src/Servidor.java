import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

class Servidor {
    public static void main(String[] argv) throws Exception {
        String capitalizedSentence = null;
        ServerSocket welcomeSocket = new ServerSocket(1234);

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("Cliente conectado");
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            byte[] tramaBytes = inFromClient.readLine().getBytes();
            Trama trama = Trama.fromBytes(tramaBytes);
            // verificar checksum
            if (verificarChecksum(trama)) {
                // convertir data de hexadecimal a string
                trama.setData(fromHex(trama.getData()));
                // realizar operación y asignar el resultado a la variable capitalizedSentence
                assert capitalizedSentence != null;
                outToClient.writeBytes(capitalizedSentence);
                System.out.println(capitalizedSentence);
                connectionSocket.close();
            } else {
                outToClient.writeBytes("Checksum incorrecto");
                connectionSocket.close();
            }
        }
    }

    public static String fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static boolean verificarChecksum(Trama trama) {
        int sum = trama.getCantidadBytes();
        for (int i = 0; i < trama.getData().length(); i++) {
            sum += trama.getData().charAt(i);
        }
        return (sum & 0xFF) == trama.getChecksum();
    }
}