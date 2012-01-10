package mgr.info;

import java.io.Serializable;

public class CheckOutRequest implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String serverIP;
    private Integer port;
    private String fileName;	//replicaName
    private Integer latestVersion;

    public CheckOutRequest(String serverIP, Integer port, String fileName,
            Integer latestVersion) {
        super();
        this.serverIP = serverIP;
        this.port = port;
        this.fileName = fileName;
        this.latestVersion = latestVersion;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(Integer latestVersion) {
        this.latestVersion = latestVersion;
    }
}
