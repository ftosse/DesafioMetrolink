class Trama {
    private int cantidadBytes;
    private String data;
    private int checksum;

    public Trama(int cantidadBytes, String data, int checksum) {
        this.cantidadBytes = cantidadBytes;
        this.data = data;
        this.checksum = checksum;
    }

    public Trama() {

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
}
