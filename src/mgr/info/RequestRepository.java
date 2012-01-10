package mgr.info;

import java.io.Serializable;

public class RequestRepository implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String fileName;
    private String replicaName;
    private Integer latestVersion;

    public RequestRepository(String fileName, String replicaName, Integer latestVersion) {
        super();
        this.fileName = fileName;
        this.replicaName = replicaName;
        this.latestVersion = latestVersion;
    }

    public String getFileName() {
        return fileName;
    }

    public Integer getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(Integer latestVersion) {
        this.latestVersion = latestVersion;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getReplicaName() {
        return replicaName;
    }

    public void setReplicaName(String replicaName) {
        this.replicaName = replicaName;
    }
}
