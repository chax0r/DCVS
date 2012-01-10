package mgr.info;

import java.io.Serializable;

import mgr.Command;

import util.DCVSConstants;

public class CheckinFileInfo implements Command, Serializable {

    private String fileName;
    private Long fileLength;
    private Boolean metaFileExists;

    public CheckinFileInfo(String fileName, Long fileLength,
            Boolean metaFileExists) {

        this.fileName = fileName;
        this.fileLength = fileLength;
        this.metaFileExists = metaFileExists;
    }

    public String getFileName() {
        return fileName;
    }

    public Long getFileLength() {
        return fileLength;
    }

    public Boolean getMetaFileExists() {
        return metaFileExists;
    }

    @Override
    public String getCommand() {

        return DCVSConstants.CHECKIN_COMMAND;
    }
}
