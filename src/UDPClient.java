import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class UDPClient {

    public static void main(String[] args) throws Exception {
        BufferedReader user_in = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket socket = new DatagramSocket();
        InetAddress IP = InetAddress.getByName("127.0.0.1");

        System.out.print("Enter Data to send to server: ");
        String message = user_in.readLine();

        byte[] outData = message.getBytes();
        DatagramPacket sendPkt = new DatagramPacket(outData, outData.length, IP, 12345);
        socket.send(sendPkt);

        socket.close();
    }

}
