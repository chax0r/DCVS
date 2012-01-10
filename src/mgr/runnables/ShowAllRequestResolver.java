package mgr.runnables;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import util.DCVSConstants;
import mgr.lookuptable.LookUpTable;

public class ShowAllRequestResolver implements Runnable {

    private LookUpTable lookupTable;

    public ShowAllRequestResolver(LookUpTable lookupTable) {

        this.lookupTable = lookupTable;
    }

    @Override
    public void run() {
        ServerSocket showAllRequestSocket = null;
        Socket showAllServiceSocket = null;

        try {

            System.out.println("Inside  showallRequestSocket ");
            showAllRequestSocket = new ServerSocket(DCVSConstants.MANAGER_SHOWALL_PORT_NUMBER);

            while (true) {

                showAllServiceSocket = showAllRequestSocket.accept();
                Thread thread = new Thread(new CheckOutThread(showAllServiceSocket, this.lookupTable));
                thread.start();

            }
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}
