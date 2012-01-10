package mgr.lookuptable;

import java.util.Map;

public class ReplicaEntry {
	
	private String fileName;
	private String replicaName;
	private Long replicaLength;
	private Integer versionNumber;
	//private String repositoryName;
	private Map<String,Boolean>addressMap;
	
	
	public ReplicaEntry(String fileName, String replicaName,
						Long replicaLength,Integer versionNumber, 
						Map<String,Boolean>addressMap) {
		super();
		this.fileName = fileName;
		this.replicaName = replicaName;
		this.replicaLength = replicaLength;
		this.versionNumber = versionNumber;
		this.addressMap = addressMap;
	}
	
	


	public Long getReplicaLength() {
		return replicaLength;
	}




	public String getFileName() {
		return fileName;
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


	public Integer getVersionNumber() {
		return versionNumber;
	}


	public void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
	}


	public Map<String, Boolean> getAddressMap() {
		return addressMap;
	}


	public void setAddressMap(Map<String, Boolean> addressMap) {
		this.addressMap = addressMap;
	}


	

}
