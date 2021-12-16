import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.channels.*;
import java.nio.channels.SocketChannel;

public class Client{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String serverIp = "192.168.56.1";
        int port = 9876;
    	try {
                SocketChannel socketChannel = SocketChannel.open();
                SocketAddress socketAddr = new InetSocketAddress(serverIp, port);
                socketChannel.connect(socketAddr);
                
                Thread t = new ThreadC(socketChannel ,port );

                t.run();
    	}
    	catch (UnknownHostException e) {
    	    System.out.println(e);
    	}
    	catch (IOException e) {
    	    System.out.println(e);
    	}
    }
}

class ThreadC extends Thread {

    SocketChannel server;
    int port;

    public ThreadC(SocketChannel socketChannel ,int port ){
        this.server = socketChannel;
        this.port = port;
    }

    @Override
     public void run() {
        try {
            String filePath = "/home/titima/ดาวน์โหลด/Client/111.mp4";

            File file = new File(filePath);
            long limit = Long.MAX_VALUE;

            FileChannel fileChannel = new FileOutputStream(file).getChannel();
            long start = System.currentTimeMillis();
            System.out.println("start request");
            System.out.println("Wait a moment please...");

            fileChannel.transferFrom(server, 0,limit);

            long end = System.currentTimeMillis();
            long time = end - start;
            fileChannel.close();

            System.out.printf("Success!! " + "time :   %.2f   seconds" , time/1000.0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        try{
            server.close();
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}
