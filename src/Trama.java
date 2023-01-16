import java.util.Arrays;

class Trama {
    private int cantidadBytes;
    private String data;
    private int checksum;

    public Trama(int cantidadBytes, String data, int checksum) {
        this.cantidadBytes = cantidadBytes;
        this.data = data;
        this.checksum = checksum;
    }

    public int getCantidadBytes() {
        return cantidadBytes;
    }

    public void setCantidadBytes(int cantidadBytes) {
        this.cantidadBytes = cantidadBytes;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getChecksum() {
        return checksum;
    }

    public void setChecksum(int checksum) {
        this.checksum = checksum;
    }
    public static Trama fromBytes(byte[] tramaBytes) {
        // parsear el arreglo de bytes para extraer la cantidad de bytes, los datos y el checksum
        int cantidadBytes = (int) tramaBytes[0];
        byte[] dataBytes = Arrays.copyOfRange(tramaBytes, 1, tramaBytes.length - 1);
        String data = new String(dataBytes);
        int checksum = (int) tramaBytes[tramaBytes.length - 1];
        // crear un nuevo objeto Trama con la información extraída
        return new Trama(cantidadBytes, data, checksum);
    }

}
