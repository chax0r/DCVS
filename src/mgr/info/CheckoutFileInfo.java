package mgr.info;

import java.io.Serializable;

import mgr.Command;

import util.DCVSConstants;

public class CheckoutFileInfo implements Command, Serializable {

    private String fileName;
    private int version;

    public CheckoutFileInfo(String fileName, int version) {
        this.fileName = fileName;
        this.version = version;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String getCommand() {

        return DCVSConstants.CHECKOUT_COMMAND;
    }
}
