package mgr.info;

import java.io.Serializable;
import java.util.Set;

import util.DCVSConstants;

import mgr.Command;
import mgr.lookuptable.ReplicaEntry;

public class ShowAllInfo implements Command, Serializable {

    private String fileName;
    Set<ReplicaEntry> replicaEntries;

    public ShowAllInfo(String fileName, Set<ReplicaEntry> replicaEntries) {
        this.fileName = fileName;
        this.replicaEntries = replicaEntries;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Set<ReplicaEntry> getReplicaEntries() {
        return replicaEntries;
    }

    public void setReplicaEntries(Set<ReplicaEntry> replicaEntries) {
        this.replicaEntries = replicaEntries;
    }

    @Override
    public String getCommand() {

        return DCVSConstants.SHOW_ALL_COMMAND;
    }
}
