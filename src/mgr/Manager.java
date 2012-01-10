package mgr;

import java.net.ServerSocket;
import java.net.Socket;

import mgr.lookuptable.LookUpTable;
import mgr.runnables.CheckinRequestResolver;
import mgr.runnables.CheckoutRequestResolver;
import mgr.runnables.ShowAllRequestResolver;

public class Manager {

    private LookUpTable lookupTable;

    public Manager() {
        lookupTable = new LookUpTable();
    }

    public static void main(String[] args) {

        Manager mgr = new Manager();

        Thread t1 = new Thread(new CheckinRequestResolver(mgr.getLookupTable()));
        t1.start();

        Thread t2 = new Thread(new CheckoutRequestResolver(mgr.getLookupTable()));
        t2.start();

        Thread t3 = new Thread(new ShowAllRequestResolver(mgr.getLookupTable()));
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        
    }

    /**
     * @return the lookupTable
     */
    public LookUpTable getLookupTable() {
        return lookupTable;
    }
}
