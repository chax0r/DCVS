package mgr.info;

import java.io.Serializable;

public class FileInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileName;
	private Long fileLength;
    private byte [] myByteArray;
	private Boolean metaFileExists;
	
	public FileInfo(String fileName, long length,byte [] mybytearray,Boolean metaFileExists) {
		this.fileName = fileName;
		this.fileLength = length;
		this.myByteArray = mybytearray;
		this.metaFileExists = metaFileExists;
	}
	
	
	public FileInfo(String fileName, Long fileLength, Boolean metaFileExists) {
		super();
		this.fileName = fileName;
		this.fileLength = fileLength;
		this.metaFileExists = metaFileExists;
	}
	

	public String getFileName(){
		return this.fileName;
	}
	
	
	public void setFileName(String name){
		this.fileName = name;
	}
	
	public Long getFileLength(){
		return this.fileLength;
	}
	
	public void setFileLength(Long length){
		this.fileLength = length;
	}

	public Boolean getMetaFileExists() {
		return metaFileExists;
	}

	public void setMetaFileExists(Boolean metaFileExists) {
		this.metaFileExists = metaFileExists;
	}


	public byte[] getMyByteArray() {
		return myByteArray;
	}

}
