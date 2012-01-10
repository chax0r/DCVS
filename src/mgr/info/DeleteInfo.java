package mgr.info;

public class DeleteInfo {

    private String fileName;
    private String replicaName;

    public DeleteInfo(String fileName, String replicaName) {
        super();
        this.fileName = fileName;
        this.replicaName = replicaName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getReplicaName() {
        return replicaName;
    }
}
