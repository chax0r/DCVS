package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import mgr.Command;

public class ShowAllVersions implements Command {

    public static void main(String[] args) {




        try {
            InetAddress addr = InetAddress.getByName("localhost");
            SocketAddress sockaddr = new InetSocketAddress(addr, Integer.parseInt(args[0]));
            Socket clientSocket = new Socket();
            clientSocket.connect(sockaddr, 2000);

            BufferedReader rd = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.write(args[1]);



        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public String getCommand() {
        // TODO Auto-generated method stub
        return null;
    }
}
