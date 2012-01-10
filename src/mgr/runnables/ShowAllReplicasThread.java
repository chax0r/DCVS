package mgr.runnables;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;

import mgr.info.ShowAllInfo;
import mgr.lookuptable.LookUpTable;
import mgr.lookuptable.ReplicaEntry;

public class ShowAllReplicasThread implements Runnable {

    private String fileName;
    private LookUpTable lookUpTable;
    private Socket serverServiceSocket;

    public ShowAllReplicasThread(LookUpTable lookUpTable,
            Socket socket) {
        super();
        this.lookUpTable = lookUpTable;
        this.serverServiceSocket = socket;
    }

    @Override
    public void run() {

        synchronized (this.lookUpTable) {



            ObjectInputStream in;
            try {
                in = new ObjectInputStream(serverServiceSocket.getInputStream());
                ShowAllInfo showAllInfo = (ShowAllInfo) in.readObject();

                String fileName = showAllInfo.getFileName();

                Map<Integer, ReplicaEntry> replicaEntries = this.lookUpTable.getFileLookupTable().get(fileName).
                        getReplicaEntries();

                ShowAllInfo showAllInfoToClient = new ShowAllInfo(fileName, new HashSet<ReplicaEntry>(replicaEntries.values()));

                ObjectOutputStream out = new ObjectOutputStream(serverServiceSocket.getOutputStream());
                out.writeObject(showAllInfoToClient);
                out.close();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (ClassNotFoundException e) {

                e.printStackTrace();
            }

        }
    }
}
