package repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import mgr.info.ReplicaInfo;
import util.DCVSConstants;

public class StoreThread implements Runnable {

    private Socket storeSocket;

    public StoreThread(Socket storeSocket) {

        this.storeSocket = storeSocket;
    }

    @Override
    public void run() {

        ObjectInputStream in = null;
        try {

            System.out.println("Inside store thread");
            in = new ObjectInputStream(storeSocket.getInputStream());
            ReplicaInfo fileInfo = (ReplicaInfo) in.readObject();

            File checkinDirFile = new File(DCVSConstants.REPOSITORY_FILE_LOC + "/" + fileInfo.getFileName());
            System.out.println("checkinDirFile.exists()" + checkinDirFile.exists());

            if (!checkinDirFile.exists()) {
                boolean t = checkinDirFile.mkdirs();
                System.out.println("t--" + t);
            }

            String storeFileLocation = DCVSConstants.REPOSITORY_FILE_LOC + "/" + fileInfo.getFileName() + "/" + fileInfo.getVersionName();

            File storeFile = new File(storeFileLocation);
            storeFile.createNewFile();
            System.out.println(storeFile.getCanonicalPath());
            System.out.println(storeFile.exists());
            FileOutputStream fout = new FileOutputStream(storeFile);
            fout.write(fileInfo.getReplicaData());
            fout.close();
            System.out.println("done");
            System.out.println(storeSocket.isClosed());
            PrintWriter writer = new PrintWriter(storeSocket.getOutputStream(), true);

            writer.println("Alls Well");
        } catch (IOException e) {

            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
