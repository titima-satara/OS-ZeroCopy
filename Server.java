
import java.net.*;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.*;

/**
 *
 * @author Titima
 */
public class Server {

    public static void main(String[] args) {
        int port = 9876;
        try {

            ServerSocketChannel ServerSocket = null;
            SocketChannel Client = null;
            
            System.out.println("Server waiting for client on port "
                    + port);

            while (true) {

                ServerSocket = ServerSocketChannel.open();
                InetSocketAddress e = new InetSocketAddress(port);
                ServerSocket.socket().bind(e);
                Client = ServerSocket.accept();

                Thread t = new ThreadS(Client, port);

                t.start();

                ServerSocket.close();

            }

        } catch (IOException e) {
            System.out.println(e);
        }

    }
}

class ThreadS extends Thread {

    int port;
    SocketChannel client;

    public ThreadS(SocketChannel client, int port) {
        this.client = client;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            String path = "C:\\Server\\111.mp4";
            File file = new File(path);

            long fileSize = file.length();

            FileChannel fileChannel = new FileInputStream(file).getChannel();

            long startTime = System.currentTimeMillis();

            long start = 0, status = 0;

            long limit = fileSize;
            while (start != limit) {
                status = fileChannel.transferTo(start, limit, client);
                start += status;
            }

            long end = System.currentTimeMillis();
            long time = end - startTime;

            fileChannel.close();

            System.out.printf("Success!! " + " time :   %.2f   seconds", time / 1000.0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            client.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
