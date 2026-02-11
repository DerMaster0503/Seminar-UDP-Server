import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) throws Exception {
        int port = 12345;

        DatagramSocket socket = new DatagramSocket(null);
        socket.bind(new InetSocketAddress("::", port));

        System.out.println("UDP Server lauscht auf Port " + port);

        byte[] buffer = new byte[1024];

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String payload = new String(
                    packet.getData(),
                    packet.getOffset(),
                    packet.getLength(),
                    StandardCharsets.UTF_8
            );

            String from = packet.getAddress().getHostAddress();

            System.out.println("Von " + from + ": " + payload);

        }
    }
}