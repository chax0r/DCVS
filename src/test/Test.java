package test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import mgr.info.MetaLog;
import client.Checkin;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		MetaLog metaLog = new MetaLog("some file name",1,2);
//		
//		FileOutputStream fileOut;
//		try {
//			fileOut = new FileOutputStream("./.dcvs/.logMetaFile");
//			ObjectOutputStream createObj = new ObjectOutputStream(fileOut);
//			createObj.writeObject(metaLog);
//		} catch (FileNotFoundException e) {
//			
//			e.printStackTrace();
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}
//		
		
		
		
		Checkin.start(args);
		

	}

}
