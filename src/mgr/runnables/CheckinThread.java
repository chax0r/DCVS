package mgr.runnables;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import mgr.FileLookupEntry;
import mgr.info.CheckinFileInfo;
import mgr.info.DeleteInfo;
import mgr.info.FileInfo;
import mgr.info.MetaLog;
import mgr.info.ReplicaInfo;
import mgr.lookuptable.LookUpTable;
import mgr.lookuptable.ReplicaEntry;
import util.DCVSConstants;

public class CheckinThread implements Runnable {

    private Socket serverServiceSocket;
    private LookUpTable lookupTable;
    private ObjectInputStream in;

    public CheckinThread(Socket socket, LookUpTable lookUpTable) {
        this.serverServiceSocket = socket;
        this.lookupTable = lookUpTable;
    }

    @Override
    public void run() {



        // TODO: code for reading file from client and passing it over the
        // repositories
        try {

            System.out.println("Inside checkin thread");




            in = new ObjectInputStream(serverServiceSocket.getInputStream());



            CheckinFileInfo checkinFileInfo = (CheckinFileInfo) in.readObject();

            System.out.println("File Name --> " + checkinFileInfo.getFileName());
            System.out.println("File Size -->" + checkinFileInfo.getFileLength());
            System.out.println("MetaFileExists -->" + checkinFileInfo.getMetaFileExists());



            //Get the FileInfo object from the client
            //FileInfo fileInfo = (FileInfo) in.readObject();





            String ack;
            Boolean problem = false;


            //if MetaFile exists at the client, we need to compare the major versions
            //if MetaFile does not exit, we assume that the user is checking in the file for
            //the first time. In this case we need to check if there is any entry in the lookup
            //table for that file, if it is there, then we need to report an error to the client
            //if there is no entry, then our assumption is right.

            if (checkinFileInfo.getMetaFileExists()) {

                MetaLog metaLog = (MetaLog) in.readObject();

                System.out.println("File Name --> " + metaLog.getFileName());
                System.out.println("MajorVersion -->" + metaLog.getMajorVersion());
                System.out.println("MinorVersion -->" + metaLog.getMinorVersion());

                //Validate the log file and then send the ack.
                //we need to check that the major version in metalog is equivalent to the latestVersion
                //for that particular filename
                //else report error



                problem = validateVersion(metaLog);

                if (!problem) {

                    reportClient("Alls well, send the file", in);
                    FileInfo actualFile = (FileInfo) in.readObject();
                    updateLookupEntry(checkinFileInfo, actualFile);
                    //checkinTheFile(in,whereToStore);

                } else {

                    reportClient("Error while checking in the file, please check the file again", in);

                }

            } else {

                Boolean entryExists = checkFileEntry(checkinFileInfo.getFileName());

                System.out.println("entryExists" + entryExists);

                if (!entryExists) {

                    reportClient("Alls well, send the file", in);
                    FileInfo actualFile = (FileInfo) in.readObject();
                    System.out.println("actualFile" + actualFile.getFileName());
                    createLookupEntry(checkinFileInfo, actualFile);
                    //	checkinTheFile(in,whereToStore);
                } else {

                    reportClient("Error while checking in the file, please check the file again", in);

                }
            }//end- if-else of metaFile exists



            serverServiceSocket.close();

        } catch (IOException ioe) {


            ioe.printStackTrace();

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

    }

    /*
     * This method creates an entry in the lookup table
     */
    private Map<String, Boolean> createLookupEntry(CheckinFileInfo checkinFile, FileInfo fileInfo) {

        synchronized (lookupTable) {

            Map<String, Long> lessSpace = new HashMap<String, Long>(3);

            Map<String, Boolean> fileSizeInfo = lookupTable.getRepositorySizeInfo(fileInfo.getFileLength());


            List<DeleteInfo> deleteInfoList;
            Map<String, List<DeleteInfo>> repDeleteInfo = new HashMap<String, List<DeleteInfo>>(3);



            String replicaName = fileInfo.getFileName() + System.currentTimeMillis();

            System.out.println("replicaName" + replicaName);

            Map<String, Boolean> addrOfReplica = new HashMap<String, Boolean>(3);

            System.out.println("replicaName" + fileSizeInfo.size());


            for (Map.Entry<String, Boolean> fs : fileSizeInfo.entrySet()) {

                System.out.println("fs -->" + fs.getKey());
                ReplicaInfo replicaInfo = new ReplicaInfo(fileInfo.getFileName(),
                        replicaName, fs.getValue(),
                        fileInfo.getMyByteArray(),
                        repDeleteInfo.get(fs.getKey()));

                System.out.println("Creating sentorep");
                SendToRepository sendToRepository = new SendToRepository(fs.getKey(), replicaInfo, addrOfReplica);
                Thread t = new Thread(sendToRepository);
                t.start();

            }

            ReplicaEntry replicaEntry = new ReplicaEntry(fileInfo.getFileName(),
                    replicaName, fileInfo.getFileLength(),
                    1, addrOfReplica);

            //List<ReplicaEntry>replicaEntries = new ArrayList<ReplicaEntry>();
            TreeMap<Integer, ReplicaEntry> replicaEntries = new TreeMap<Integer, ReplicaEntry>();

            replicaEntries.put(1, replicaEntry);
            //replicaEntries.add(replicaEntry);


            FileLookupEntry fileLookupEntry = new FileLookupEntry(fileInfo.getFileName(), replicaEntries, 1);

            lookupTable.getFileLookupTable().put(fileInfo.getFileName(), fileLookupEntry);

            return fileSizeInfo;

        }



    }

    private boolean isReplicaOnAtleastOneRepository(ReplicaEntry value) {
        //	value.get

        return false;
    }

    /*
     * This private method reads the FileInfo object sent by the client
     * Extracts the byte array from the user, creates a temp file and then
     * spawns threads and sends the file to the repositories
     */
    private void checkinTheFile(ObjectInputStream in, Map<String, Boolean> whereToStore) throws IOException, ClassNotFoundException {

        FileInfo actualFile = (FileInfo) in.readObject();

        File constructFile = new File("./" + actualFile.getFileName());

        FileOutputStream fileOutputStream = new FileOutputStream(constructFile);

        fileOutputStream.write(actualFile.getMyByteArray());

        fileOutputStream.close();

        synchronized (this.lookupTable) {

            for (Map.Entry<String, Boolean> store : whereToStore.entrySet()) {

                if (store.getValue()) {
                    //	TransferThread transferThread = new TransferThread(actualFile,DCVSConstants.REPOSITORY_ONE);
                    //Thread thread = new Thread(transferThread);
                    //thread.start();
                }

            }


        }

        //spawn threads and send the file to the repositories



    }

    /*
     * This method reports the client error or success.
     */
    private void reportClient(String errorMsg, ObjectInputStream in) throws IOException {

        PrintWriter writer = new PrintWriter(serverServiceSocket.getOutputStream(), true);

        writer.println(errorMsg);


    }

    private Boolean checkFileEntry(String fileName) {
        synchronized (lookupTable) {

            return lookupTable.getFileLookupTable().get(fileName) == null ? false : true;

        }

    }

    /*
     * This method validates the version of the file that is requestd for checking in
     * with the version information in the lookup table
     */
    private Boolean validateVersion(MetaLog metaLog) throws IOException, ClassNotFoundException {

        synchronized (this.lookupTable) {
            return !(metaLog.getMajorVersion().equals(lookupTable.getFileLookupTable().get(metaLog.getFileName()).getLatestVersionNumber()));
        }
    }

    private Map<String, Boolean> updateLookupEntry(CheckinFileInfo checkinFileInfo, FileInfo fileInfo) {

        synchronized (this.lookupTable) {

            FileLookupEntry fileLookupEntry = lookupTable.getFileLookupTable().get(fileInfo.getFileName());

            fileLookupEntry.setLatestVersionNumber(fileLookupEntry.getLatestVersionNumber() + 1);

            //TODO: Need to put some algo to get the repositories where we need to store this replica
            //also some algo to generate random replica names.

            Map<String, Boolean> fileSizeInfo = lookupTable.getRepositorySizeInfo(fileInfo.getFileLength());


            String replicaName = fileInfo.getFileName() + System.currentTimeMillis();


            Map<String, Boolean> addrOfReplica = new HashMap<String, Boolean>();

            for (Map.Entry<String, Boolean> fs : fileSizeInfo.entrySet()) {

                ReplicaInfo replicaInfo = new ReplicaInfo(fileInfo.getFileName(),
                        replicaName, fs.getValue(),
                        fileInfo.getMyByteArray(),
                        new ArrayList<DeleteInfo>());

                SendToRepository sendToRepository = new SendToRepository(fs.getKey(), replicaInfo, addrOfReplica);
                Thread t = new Thread(sendToRepository);
                t.start();

            }

            ReplicaEntry replicaEntry = new ReplicaEntry(fileInfo.getFileName(), replicaName,
                    fileInfo.getFileLength(),
                    fileLookupEntry.getLatestVersionNumber() + 1,
                    addrOfReplica);


            fileLookupEntry.getReplicaEntries().put(replicaEntry.getVersionNumber(), replicaEntry);
            return fileSizeInfo;

        }

    }
}
