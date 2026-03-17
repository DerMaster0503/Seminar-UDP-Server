import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDP extends Thread {

    int port;
    DatagramSocket socket;

    public UDP(int port) throws SocketException {
        this.port = port;
        startServer();
    }

    @Override
    public void run() {
        super.run();
        System.out.println("Started Thread");
        byte[] buffer = new byte[1024];
        while (!Thread.currentThread().isInterrupted()){
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String payload = new String(
                    packet.getData(),
                    packet.getOffset(),
                    packet.getLength(),
                    StandardCharsets.UTF_8
            );

            String from = packet.getAddress().getHostAddress();

            System.out.println("From " + from + ": " + payload);
        }
        System.out.println("Stopped Thread");
    }

    public void startServer() throws SocketException {
        System.out.println("Starting Server on "+port);
        socket = new DatagramSocket(new InetSocketAddress("::", port));
        //socket.bind(new InetSocketAddress("::", port));
        System.out.println("UDP Server listening to  "+socket.getLocalAddress() + ":" + port);
    }

    public void close(){
        this.interrupt();
        System.out.println("Stopping UDP Server");
        socket.close();
        System.out.println("UDP Server stopped");
    }

}
