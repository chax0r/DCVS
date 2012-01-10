package mgr;

import java.net.*;
import java.io.*;

public class FileClient {

    public static void main(String[] args) throws IOException {
        int filesize = 6022386; // filesize temporary hardcoded

        long start = System.currentTimeMillis();
        int bytesRead;
        int current = 0;
        // localhost for testing
        Socket sock = new Socket("127.0.0.1", 13267);
        System.out.println("Connecting...");

        // receive file
        byte[] mybytearray = new byte[filesize];
        InputStream is = sock.getInputStream();
        FileOutputStream fos = new FileOutputStream("source-copy.pdf");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bytesRead = is.read(mybytearray, 0, mybytearray.length);
        current = bytesRead;

        // thanks to A. Cádiz for the bug fix
        do {
            bytesRead =
                    is.read(mybytearray, current, (mybytearray.length - current));
            if (bytesRead >= 0) {
                current += bytesRead;
            }
        } while (bytesRead > -1);

        bos.write(mybytearray, 0, current);
        bos.flush();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        bos.close();
        sock.close();
    }
}
