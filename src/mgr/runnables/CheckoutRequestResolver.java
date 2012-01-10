package mgr.runnables;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import mgr.lookuptable.LookUpTable;

import util.DCVSConstants;

public class CheckoutRequestResolver implements Runnable {

    private LookUpTable lookupTable;

    public CheckoutRequestResolver(LookUpTable lookupTable) {
        super();
        this.lookupTable = lookupTable;
    }

    @Override
    public void run() {
        ServerSocket checkoutRequestSocket = null;
        Socket checkoutSocket = null;

        try {

            System.out.println("Inside  checkoutRequestSocket ");
            checkoutRequestSocket = new ServerSocket(DCVSConstants.MANAGER_CHECKOUT_PORT_NUMBER);

            while (true) {

                checkoutSocket = checkoutRequestSocket.accept();
                Thread thread = new Thread(new CheckOutThread(checkoutSocket, this.lookupTable));
                thread.start();

            }
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}
