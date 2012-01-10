package client;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.lang.Integer;

import util.DCVSConstants;


import mgr.CheckoutFile;
import mgr.info.CheckOutRequest;
import mgr.info.CheckoutFileInfo;
import mgr.info.MetaLog;
import mgr.info.RequestRepository;

public class Checkout {

    public static void start(String[] args) {

        Socket clientSocket = null;
        Socket clientRepositorySocket = null;
        ObjectInputStream rd = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        String checkoutFileName = "";
        String canonicalPath = "";

        try {

            Integer version = Integer.parseInt(args[1]);

            InetAddress addr = InetAddress.getByName("localhost");
            SocketAddress sockaddr = new InetSocketAddress(addr, DCVSConstants.MANAGER_CHECKOUT_PORT_NUMBER);
            clientSocket = new Socket();
            clientSocket.connect(sockaddr, 2000);

            //		rd = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());

            CheckoutFileInfo fileInfo = new CheckoutFileInfo(args[0], version);
//This request goes to manager 	    
            out.writeObject(fileInfo);
            out.flush();


            System.out.println("Request sent successfully");

            Long sendtime = System.currentTimeMillis();

            System.out.println("socket close :" + clientSocket.isClosed());
            rd = new ObjectInputStream(clientSocket.getInputStream());
            String ack = (String) rd.readObject();

            System.out.println("\n\n" + ack + "\n\n" + clientSocket.isClosed());

            if (ack != null && ack.equals("Valid request, Sending file")) {

//Following is handled by the repository, So insert code to connect to repository 		  
                System.out.println("\n\n" + ack + "\n\n");
// 	    	ObjectInputStream reqIS = null;
// 	    	reqIS = new ObjectInputStream(clientSocket.getInputStream());
                CheckOutRequest request = (CheckOutRequest) rd.readObject();
// 	    	reqIS.close();
//create socket for repository connection. 	    	
                System.out.println("\n\n" + ack + "\n\n");
                InetAddress repositoryAddr = InetAddress.getByName(request.getServerIP());
                SocketAddress repositorySocketAddr = new InetSocketAddress(repositoryAddr, request.getPort());
                clientRepositorySocket = new Socket();
                clientRepositorySocket.connect(repositorySocketAddr, 2000);
//Request for the file 	    	
                System.out.println("\n\n" + ack + "\n\n");
                ObjectOutputStream requestOut = new ObjectOutputStream(clientRepositorySocket.getOutputStream());
                RequestRepository repReq = new RequestRepository(fileInfo.getFileName(), request.getFileName(), request.getLatestVersion());

                requestOut.writeObject(repReq);
                System.out.println("\n\nrequest to repository\n\n");
                //	     	requestOut.close();
                ObjectInputStream ois = new ObjectInputStream(clientRepositorySocket.getInputStream());
                CheckoutFile fileData = (CheckoutFile) ois.readObject();
                //	    	ois.close();
                System.out.println("\n\nGot the file data\n\n");
                File constructFile = new File("./" + args[0]);
                FileOutputStream fileOutputStream = new FileOutputStream(constructFile);
                fileOutputStream.write(fileData.getMyByteArray());
                fileOutputStream.close();
                File dcvsFolder = new File("./.dcvs");
                if (!dcvsFolder.exists()) {

                    dcvsFolder.mkdir();
                }

                //minor version set to 1... look afterwards if any changes
                MetaLog metaLog = new MetaLog(fileData.getFileName(), fileData.getLatestVersion(), 1);

                File logMetaFile = new File(".dcvs/.logMetaFile-" + fileData.getFileName());
                logMetaFile.createNewFile();
                FileOutputStream fileLogOutputStream = new FileOutputStream(logMetaFile);
                ObjectOutputStream createObj = new ObjectOutputStream(fileLogOutputStream);
                createObj.writeObject(metaLog);
                fileLogOutputStream.close();

                File srcMetaFile = new File(".dcvs/." + fileData.getFileName() + "~");
                srcMetaFile.createNewFile();
                FileOutputStream fileSrcOutputStream = new FileOutputStream(srcMetaFile);
                fileSrcOutputStream.write(fileData.getMyByteArray());
                System.out.println("Time taken for transfer : " + (System.currentTimeMillis() - sendtime));
                fileSrcOutputStream.close();


            }


        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        } catch (UnknownHostException e) {
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
