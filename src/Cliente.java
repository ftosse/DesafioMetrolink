import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.nio.charset.StandardCharsets;

class Cliente {
    public static void main(String[] argv) {
        String sentence;
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        try {
            Socket clientSocket = new Socket("localhost", 1234);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            sentence = inFromUser.readLine();
            //System.out.println(sentence);
            Trama trama = new Trama(sentence.length(), sentence, 0);
            System.out.println("Cantidad de bytems: "+ trama.getCantidadBytes());
            // convertir data a hexadecimal
            trama.setData(toHex(trama.getData()));
            System.out.println("data: "+ trama.getData());
            // calcular checksum
            trama.setChecksum(calcularChecksum(trama));
            System.out.println("Checksum: "+ trama.getChecksum());
            // enviar trama al servidor
            String serverOut = trama.getCantidadBytes() + trama.getData() + trama.getChecksum();
            System.out.println("concatenado : "+ serverOut);
            outToServer.writeBytes(serverOut);
            System.out.println("Salida del cliente:"+serverOut);
            clientSocket.close();
            modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);
            //clientSocket.close();
        } catch (UnknownHostException e) {
            System.err.println("No se pudo conectar al host: " + e);
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e);
        }
    }

    public static String toHex(String arg) {
        return String.format("%x", new BigInteger(1, arg.getBytes(StandardCharsets.US_ASCII)));
    }

    public static int calcularChecksum(Trama trama) {
        int sum = trama.getCantidadBytes();
        for (int i = 0; i < trama.getData().length(); i++) {
            sum += trama.getData().charAt(i);
        }
        return sum & 0xFF;
    }
}
