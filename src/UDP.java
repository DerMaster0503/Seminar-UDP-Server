import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Scanner;

public class UDP extends Thread {

    int port;
    DatagramSocket socket;
    SQL sql;

    public UDP(int port, SQL sql) throws SocketException {
        this.port = port;
        this.sql = sql;
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

            if  (payload.startsWith("save")){
                String[] strings = payload.split(" ");
                try {
                    sql.addReadings(Integer.parseInt(strings[1]), strings[2], Integer.parseInt(strings[3]));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            // save room_id measure_type value
            System.out.println("From " + from + ": " + payload);

            if (payload.contains("exit")) {
                try {
                    Main.stopServices();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

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
