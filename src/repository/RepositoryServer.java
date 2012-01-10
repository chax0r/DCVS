package repository;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import util.DCVSConstants;

public class RepositoryServer {

    public static void main(String[] args) {

        ServerSocket repositorySocket = null;
        Socket storeSocket = null;

        ObjectInputStream in = null;

        File dcvsFolder = new File(DCVSConstants.REPOSITORY_FILE_LOC);
        if (!dcvsFolder.exists()) {
            dcvsFolder.mkdir();
        }

        MgrRequestHandler mgrRequest = new MgrRequestHandler();
        Thread t1 = new Thread(mgrRequest);
        t1.start();

        ClientRequestHandler clientRequestHandler = new ClientRequestHandler();
        Thread t2 = new Thread(clientRequestHandler);
        t2.start();


        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }
}
