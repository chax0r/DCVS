package mgr.info;

import java.io.Serializable;

public class MetaLog implements Serializable {

    private String fileName;
    private Integer majorVersion;
    private Integer minorVersion;

    public MetaLog(String fileName, Integer majorVersion, Integer minorVersion) {
        super();
        this.fileName = fileName;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(Integer majorVersion) {
        this.majorVersion = majorVersion;
    }

    public Integer getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(Integer minorVersion) {
        this.minorVersion = minorVersion;
    }
}
