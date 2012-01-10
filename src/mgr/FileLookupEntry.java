package mgr;

import java.util.Map;
import java.util.TreeMap;

import mgr.lookuptable.ReplicaEntry;

public class FileLookupEntry implements Comparable<FileLookupEntry> {

    private String fileName;
    private TreeMap<Integer, ReplicaEntry> replicaEntries;
    private Integer latestVersionNumber;

    public FileLookupEntry(String fileName, TreeMap<Integer, ReplicaEntry> replicaEntries,
            Integer latestVersionNumber) {
        super();
        this.fileName = fileName;
        this.replicaEntries = replicaEntries;
        this.latestVersionNumber = latestVersionNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public TreeMap<Integer, ReplicaEntry> getReplicaEntries() {
        return replicaEntries;
    }

    public Integer getLatestVersionNumber() {
        return latestVersionNumber;
    }

    public void setLatestVersionNumber(Integer latestVersionNumber) {
        this.latestVersionNumber = latestVersionNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
        result = prime * result + ((latestVersionNumber == null) ? 0 : latestVersionNumber.hashCode());
        result = prime * result + ((replicaEntries == null) ? 0 : replicaEntries.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        FileLookupEntry other = (FileLookupEntry) obj;
        if (fileName == null) {
            if (other.fileName != null) {
                return false;
            }
        } else if (!fileName.equals(other.fileName)) {
            return false;
        }
        if (latestVersionNumber == null) {
            if (other.latestVersionNumber != null) {
                return false;
            }
        } else if (!latestVersionNumber.equals(other.latestVersionNumber)) {
            return false;
        }
        if (replicaEntries == null) {
            if (other.replicaEntries != null) {
                return false;
            }
        } else if (!replicaEntries.equals(other.replicaEntries)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(FileLookupEntry o) {

        if (this.getReplicaEntries().size() > o.getReplicaEntries().size()) {
            return -1;
        } else if (this.getReplicaEntries().size() < o.getReplicaEntries().size()) {
            return 1;
        }

        return 0;
    }
}
