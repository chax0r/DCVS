package mgr.runnables;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import mgr.Command;
import mgr.info.CheckinFileInfo;
import mgr.info.CheckoutFileInfo;
import mgr.info.ShowAllInfo;
import mgr.lookuptable.LookUpTable;
import util.DCVSConstants;

public class ResolveRequest implements Runnable {

    private Socket serverServiceSocket;
    private LookUpTable lookupTable;

    public ResolveRequest(Socket serverServiceSocket, LookUpTable lookupTable) {

        this.serverServiceSocket = serverServiceSocket;
        this.lookupTable = lookupTable;
    }

    @Override
    public void run() {


        try {

            ObjectInputStream in = new ObjectInputStream(serverServiceSocket.getInputStream());

            //Get the FileInfo object from the client
            Command command = (Command) in.readObject();

            System.out.println(command.getCommand());

            //	in.close();

            if (DCVSConstants.CHECKIN_COMMAND.equals(command.getCommand())) {
//				CheckinFileInfo checkinFileInfo = (CheckinFileInfo)command;
//				CheckinThread checkinThread = new CheckinThread(in,
//						lookupTable,checkinFileInfo);
//				Thread t = new Thread(checkinThread);
//				t.start();
            } else if (DCVSConstants.CHECKOUT_COMMAND.equals(command.getCommand())) {
                CheckoutFileInfo checkoutFileInfo = (CheckoutFileInfo) command;

                //CheckOutThread checkoutThread = new CheckOutThread(serverServiceSocket,checkoutFileInfo);
                //Thread t = new Thread(checkoutThread);
                //t.start();

            } else if (DCVSConstants.SHOW_ALL_COMMAND.equals(command.getCommand())) {
                ShowAllInfo showAllInfo = (ShowAllInfo) command;
//				ShowAllReplicasThread showAllReplicasThread = new ShowAllReplicasThread(showAllInfo.getFileName(),
//																					lookupTable,serverServiceSocket);
//				Thread t = new Thread(showAllReplicasThread);
//				t.start();
            } else {
                System.out.println("Error!!");
            }

        } catch (IOException e) {

            e.printStackTrace();
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

    }
}
