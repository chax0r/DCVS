package repository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import util.DCVSConstants;

public class MgrRequestHandler implements Runnable {

    @Override
    public void run() {

        ServerSocket repositorySocket = null;
        Socket storeSocket = null;

        try {
            repositorySocket = new ServerSocket(DCVSConstants.REPOSITORY_STORE_PORT_NUMBER);

            while (true) {

                storeSocket = repositorySocket.accept();
                StoreThread storeThread = new StoreThread(storeSocket);
                Thread thread = new Thread(storeThread);
                thread.start();

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
