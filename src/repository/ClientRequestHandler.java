package repository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import util.DCVSConstants;

public class ClientRequestHandler implements Runnable {

    @Override
    public void run() {

        ServerSocket repositorySocket = null;
        Socket sendSocket = null;

        try {
            repositorySocket = new ServerSocket(DCVSConstants.REPOSITORY_SEND_PORT_NUMBER);

            while (true) {

                sendSocket = repositorySocket.accept();
                SendThread sendThread = new SendThread(sendSocket);
                Thread thread = new Thread(sendThread);
                thread.start();

            }
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}
