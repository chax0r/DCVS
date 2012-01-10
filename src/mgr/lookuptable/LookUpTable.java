package mgr.lookuptable;

import java.util.HashMap;
import java.util.Map;

import mgr.FileLookupEntry;

import util.DCVSConstants;

public final class LookUpTable {
	
	Map<String,FileLookupEntry>fileLookupTable;
	
	Map<String,Long> repositorySize;
	

	public LookUpTable() {
		fileLookupTable = new HashMap<String,FileLookupEntry>();
		repositorySize = new HashMap<String,Long>();
		repositorySize.put(DCVSConstants.REPOSITORY_ONE, new Long( 0));
		repositorySize.put(DCVSConstants.REPOSITORY_TWO, new Long(0));
		repositorySize.put(DCVSConstants.REPOSITORY_THREE, new Long(0));
		
	}

	/**
	 * @return the lookupTable
	 */
	public Map<String, FileLookupEntry> getFileLookupTable() {
		return fileLookupTable;
	}


	public Map<String, Long> getRepositorySize() {
		return repositorySize;
	}

	public Map<String, Boolean> getRepositorySizeInfo(Long fileLength) {
		
		Map<String,Boolean> sizeInfo = new HashMap<String,Boolean>(3);
		
		for(Map.Entry<String,Long>size:this.repositorySize.entrySet()){
			
			if(DCVSConstants.REPOSITORY_SIZE.compareTo((size.getValue() + fileLength)) > 0){
				sizeInfo.put(size.getKey(),true);
				
				
			}else{
				sizeInfo.put(size.getKey(), false);
			}
			
		}
		
		return sizeInfo;
	}
	
	

	/**
	 * @return the lookupTable
	 */
	
	

}
