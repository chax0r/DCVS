package mgr.runnables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Map;

import util.DCVSConstants;

import mgr.info.ReplicaInfo;

public class SendToRepository implements Runnable {

    private String repositoryName;
    private ReplicaInfo replicaInfo;
    private Map<String, Boolean> addrOfReplica;

    public SendToRepository(String repositoryName, ReplicaInfo replicaInfo, Map<String, Boolean> addrOfReplica) {
        super();
        this.repositoryName = repositoryName;
        this.replicaInfo = replicaInfo;
        this.addrOfReplica = addrOfReplica;
    }

    @Override
    public void run() {

        InetAddress addr;
        try {
            System.out.println("sent data to rep");
            addr = InetAddress.getByName(repositoryName);
            SocketAddress sockaddr = new InetSocketAddress(addr, DCVSConstants.REPOSITORY_STORE_PORT_NUMBER);
            Socket clientSocket = new Socket();
            clientSocket.connect(sockaddr, 2000);

            BufferedReader rd = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

            out.writeObject(replicaInfo);

            System.out.println("sent data to rep");

            String ack = rd.readLine();
            out.close();
            if (ack.equals("Alls well")) {
                this.addrOfReplica.put(repositoryName, true);
            } else {
                this.addrOfReplica.put(repositoryName, false);
            }

        } catch (UnknownHostException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }



    }
}
