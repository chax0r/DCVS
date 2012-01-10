package mgr.runnables;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import mgr.lookuptable.LookUpTable;

import repository.SendThread;
import util.DCVSConstants;

public class CheckinRequestResolver implements Runnable {

    private LookUpTable lookUpTable;

    public CheckinRequestResolver(LookUpTable lookUpTable) {
        super();
        this.lookUpTable = lookUpTable;
    }

    @Override
    public void run() {

        ServerSocket checkinRequestSocket = null;
        Socket checkinSocket = null;

        try {
            System.out.println("Inside  checkinRequestSocket ");
            checkinRequestSocket = new ServerSocket(DCVSConstants.MANAGER_CHECKIN_PORT_NUMBER);

            while (true) {

                checkinSocket = checkinRequestSocket.accept();
                Thread thread = new Thread(new CheckinThread(checkinSocket, lookUpTable));
                thread.start();

            }
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}
