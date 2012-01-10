package mgr;

import java.io.Serializable;

public class CheckoutFile implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String fileName;
    private int latestVersion;
    private byte[] myByteArray;

    public CheckoutFile(String fileName, int latestVersion, byte[] myByteArray) {
        super();
        this.fileName = fileName;
        this.latestVersion = latestVersion;
        this.myByteArray = myByteArray;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(int latestVersion) {
        this.latestVersion = latestVersion;
    }

    public byte[] getMyByteArray() {
        return myByteArray;
    }

    public void setMyByteArray(byte[] myByteArray) {
        this.myByteArray = myByteArray;
    }
}
