package repository;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.File;
import util.DCVSConstants;


import mgr.CheckoutFile;
import mgr.info.RequestRepository;

public class SendThread implements Runnable {

    private Socket sendSocket;

    public SendThread(Socket sendSocket) {
        super();
        this.sendSocket = sendSocket;
    }

    @Override
    public void run() {

        //ObjectInputStream in = null;
        ObjectOutputStream checkout = null;
        RequestRepository reqRep = null;
        FileInputStream fis = null;
        byte[] mybytearray = null;

        //TODO : Code to send the file to the client
        try {
            System.out.println("Inside Sendthread.." + sendSocket.isClosed());

            ObjectInputStream in = new ObjectInputStream(sendSocket.getInputStream());

            System.out.println(sendSocket.isClosed());
            reqRep = (RequestRepository) in.readObject();
            System.out.println("Inside Sendthread, read the object");
//			in.close();
            //got the filename and replicaName. Now Search for the file.
            File checkinDirFile = new File(DCVSConstants.REPOSITORY_FILE_LOC + "/" + reqRep.getFileName());

            if (!checkinDirFile.exists()) {

                System.out.println("Error!!!!");

            } else {

                String storeFileLocation = DCVSConstants.REPOSITORY_FILE_LOC + "/" + reqRep.getFileName();
                File checkoutFile = new File(storeFileLocation + "/" + reqRep.getReplicaName());
                if (checkoutFile.exists()) {

                    fis = new FileInputStream(checkoutFile);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    mybytearray = new byte[(int) checkoutFile.length()];
                    bis.read(mybytearray, 0, mybytearray.length);
                    //need to figure out how to get latest version avail.
                    CheckoutFile checkFile = new CheckoutFile(reqRep.getFileName(), reqRep.getLatestVersion(), mybytearray);
                    checkout = new ObjectOutputStream(sendSocket.getOutputStream());
                    checkout.writeObject(checkFile);
                    //						checkout.close();
                    fis.close();
                } else {
                    System.out.println("File Not Found.");
                }

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


