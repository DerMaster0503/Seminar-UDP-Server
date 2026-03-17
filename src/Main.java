import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        int port = 12345;

        DatagramSocket socket = new DatagramSocket(null);
        socket.bind(new InetSocketAddress("::", port));

        System.out.println("UDP Server listening to Port " + port);

        SQL sql = new SQL("sensor_user", "sensor_pass");

        byte[] buffer = new byte[1024];

        Scanner scanner = new Scanner(System.in);

        while (!scanner.hasNext()) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String payload = new String(
                    packet.getData(),
                    packet.getOffset(),
                    packet.getLength(),
                    StandardCharsets.UTF_8
            );

            String from = packet.getAddress().getHostAddress();

            System.out.println("From " + from + ": " + payload);
        }
        System.out.println("Scanner reading: "+scanner.next());
        System.out.println("Stopping Services");
        sql.close();
        System.out.println("Stopping UDP Server");
        socket.close();
        System.out.println("UDP Server stopped");
    }
}


