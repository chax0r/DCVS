package mgr.info;

import java.io.Serializable;
import java.util.List;

public class ReplicaInfo implements Serializable {

    private String fileName;
    private String versionName;
    private Boolean deleteOlderVersion;
    private List<DeleteInfo> deleteInfoList;
    private byte[] replicaData;

    public ReplicaInfo(String fileName, String versionName, Boolean delOlderVer,
            byte[] replicaData, List<DeleteInfo> deleteInfoList) {
        super();
        this.fileName = fileName;
        this.versionName = versionName;
        this.deleteOlderVersion = delOlderVer;
        this.replicaData = replicaData;
        this.deleteInfoList = deleteInfoList;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public byte[] getReplicaData() {
        return replicaData;
    }

    public void setReplicaData(byte[] replicaData) {
        this.replicaData = replicaData;
    }

    public Boolean getDeleteOlderVersion() {
        return deleteOlderVersion;
    }

    public void setDeleteOlderVersion(Boolean deleteOlderVersion) {
        this.deleteOlderVersion = deleteOlderVersion;
    }
}
