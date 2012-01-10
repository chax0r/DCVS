package client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import util.DCVSConstants;

import mgr.info.CheckinFileInfo;
import mgr.info.FileInfo;
import mgr.info.MetaLog;

public class Checkin {

    /**
     * @param args
     */
    public static void start(String[] args) {
        // TODO: Hardcode the IP address and port number of the Manager ?
        Socket clientSocket = null;
        BufferedReader rd = null;
        ObjectOutputStream out = null;
        String checkinFileName = "";
        String canonicalPath = "";

        Long sendtime = null;
        try {

            File orgFile = new File(args[0]);
            canonicalPath = orgFile.getCanonicalPath();
            System.out.println(canonicalPath);
            Integer lastIndex = canonicalPath.lastIndexOf("/");
            checkinFileName = canonicalPath.substring(lastIndex + 1, canonicalPath.length());
            canonicalPath = canonicalPath.substring(0, lastIndex);


            //Check for meta files
            Boolean metaFilesExit = false;

            File dcvsFolder = new File(canonicalPath + "/.dcvs");

            MetaLog metaLogFile = null;

            //if the folder dcvs is inexistent we assume that the user is checking in the file
            //for the first time
            System.out.println("dcvsFolder.exists() " + dcvsFolder);
            if (dcvsFolder.exists()) {
                String hiddenSrcFile = "." + checkinFileName + "~";
                System.out.println(hiddenSrcFile);

                File srcMetaFile = new File(canonicalPath + "/.dcvs/" + hiddenSrcFile);

                File logMetaFile = new File(canonicalPath + "/.dcvs/.logMetaFile-" + checkinFileName);

                if (!srcMetaFile.exists() || !logMetaFile.exists()) {
                    System.out.println("\nError.");
                    //	System.out.println(srcMetaFile.getPath() + "-->" + srcMetaFile.exists());
                    //	System.out.println(logMetaFile.getPath() + "-->" + logMetaFile.exists());
                } else {

                    metaFilesExit = true;
                    FileInputStream fileIn = new FileInputStream(logMetaFile);
                    ObjectInputStream createObj = new ObjectInputStream(fileIn);
                    try {
                        metaLogFile = (MetaLog) createObj.readObject();

                        System.out.println("File Name --> " + metaLogFile.getFileName());
                        System.out.println("MajorVersion -->" + metaLogFile.getMajorVersion());
                        System.out.println("MinorVersion -->" + metaLogFile.getMinorVersion());
                    } catch (ClassNotFoundException e) {

                        e.printStackTrace();
                    }

                }

            } else {

                metaFilesExit = false;
                System.out.println("lst");
            }

            //connect to the manager using the ip and port
            InetAddress addr = InetAddress.getByName("localhost");
            SocketAddress sockaddr = new InetSocketAddress(addr, DCVSConstants.MANAGER_CHECKIN_PORT_NUMBER);
            clientSocket = new Socket();
            clientSocket.connect(sockaddr, 2000);

            rd = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new ObjectOutputStream(clientSocket.getOutputStream());

            File myFile = new File(args[0]);
            CheckinFileInfo checkinFileInfo = new CheckinFileInfo(args[0], myFile.length(), metaFilesExit);

            System.out.println("Sending...First FileInfo");
            sendtime = System.currentTimeMillis();
            out.writeObject(checkinFileInfo);
            out.flush();

            if (metaFilesExit) {

                out.writeObject(metaLogFile);
                out.flush();

                System.out.println("File Name --> " + metaLogFile.getFileName());
                System.out.println("MajorVersion -->" + metaLogFile.getMajorVersion());
                System.out.println("MinorVersion -->" + metaLogFile.getMinorVersion());
            }
            System.out.println("Sent successfully");

            String ack = rd.readLine();
            System.out.println(ack);

            //Create a diff file here

            if (ack != null && ack.equals("Alls well, send the file")) {

                System.out.println("Time taken for transfer : " + (System.currentTimeMillis() - sendtime));


                byte[] mybytearray = new byte[(int) myFile.length()];
                FileInputStream fis = new FileInputStream(myFile);
                BufferedInputStream bis = new BufferedInputStream(fis);
                bis.read(mybytearray, 0, mybytearray.length);

                System.out.println(mybytearray.length);

                FileInfo newFileInfo = new FileInfo(myFile.getName(), myFile.length(), mybytearray, metaFilesExit);

                //fileInfo.setMyByteArray(mybytearray);
                System.out.println(newFileInfo.getMyByteArray().length);


                out.writeObject(newFileInfo);
                out.flush();
                fis.close();


            } else {
                System.out.println("Screwed somewhere!!");
            }


            clientSocket.close();

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
