import java.io.*;
import java.math.BigInteger;
import java.net.*;

class Cliente {
    public static void main(String[] argv) throws Exception {
        String sentence;
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket;
        clientSocket = new Socket("Cliente1", 6760);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        sentence = inFromUser.readLine();
        Trama trama = new Trama(sentence.length(), sentence, 0);
        // convertir data a hexadecimal
        trama.setData(toHex(trama.getData()));
        // calcular checksum
        trama.setChecksum(calcularChecksum(trama));
        // enviar trama al servidor
        outToServer.writeBytes(trama.getCantidadBytes() + trama.getData() + trama.getChecksum());
        modifiedSentence = inFromServer.readLine();
        System.out.println("FROM SERVER: " + modifiedSentence);
        clientSocket.close();
    }

    public static String toHex(String arg) {
        return String.format("%x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
    }

    public static int calcularChecksum(Trama trama) {
        int sum = trama.getCantidadBytes();
        for (int i = 0; i < trama.getData().length(); i++) {
            sum += trama.getData().charAt(i);
        }
        return sum % 256;
    }
}