package mgr.runnables;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Map;

import mgr.FileLookupEntry;
import mgr.info.CheckOutRequest;
import mgr.info.CheckoutFileInfo;
import mgr.lookuptable.LookUpTable;
import mgr.lookuptable.ReplicaEntry;
import util.DCVSConstants;

public class CheckOutThread implements Runnable {

	private Socket checkoutServiceSocket;
	private LookUpTable lookupTable;
	
	public CheckOutThread(Socket checkoutServiceSocket,LookUpTable lookupTable) {
		super();
		this.checkoutServiceSocket = checkoutServiceSocket;
		this.lookupTable = lookupTable;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		// TODO: code for reading file from client and passing it over the
		// repositories
		try {

			in = new ObjectInputStream(checkoutServiceSocket.getInputStream());
			
			CheckoutFileInfo fileInfo = (CheckoutFileInfo) in.readObject();
			System.out.println("Inside checkout now...");
			//fileInfo = (CheckoutFileInfo) in.readObject();
			System.out.println("Able to read checkoutFileInfo...");
			//got the filename and version number. Now Search for the file.
			FileLookupEntry fileEntry = lookupTable.getFileLookupTable().get(fileInfo.getFileName());
			System.out.println("Filelookup entry:	" + fileEntry.getFileName());
			ReplicaEntry replicaEntry = lookupTable.getFileLookupTable().get(fileInfo.getFileName()).getReplicaEntries().get(fileInfo.getVersion());
			
			System.out.println("socket close: " + checkoutServiceSocket.isClosed());
			
//			PrintWriter writer = new PrintWriter(checkoutServiceSocket.getOutputStream(),true);
	//		writer.println("Valid request, Sending file");
		
			out = new ObjectOutputStream(checkoutServiceSocket.getOutputStream());
			String ACK = "Valid request, Sending file";
			out.writeObject(ACK);
			
			System.out.println("Ack sent.");
			
			String serverIP = null;
			for (Map.Entry<String, Boolean>addr:replicaEntry.getAddressMap().entrySet()){
				if(addr.getValue()){
					
					serverIP = addr.getKey();
					break;
				}
					
		    String replicaName = replicaEntry.getReplicaName();
		    Integer latestVersion = lookupTable.getFileLookupTable().get(fileInfo.getFileName()).getLatestVersionNumber();
			CheckOutRequest request = new CheckOutRequest(serverIP, DCVSConstants.REPOSITORY_SEND_PORT_NUMBER, replicaName, latestVersion);
			
//			out = new ObjectOutputStream(checkoutServiceSocket.getOutputStream());
			out.writeObject(request);
//			
		System.out.println("sent request");
			}
					
		} catch(NumberFormatException ex){
			ex.printStackTrace();
		}catch (UnknownHostException e) {
			e.printStackTrace();
	    } catch (SocketTimeoutException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	

