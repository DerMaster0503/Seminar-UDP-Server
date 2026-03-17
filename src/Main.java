import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        SQL sql = new SQL("sensor_user", "sensor_pass");

        System.out.println();

        UDP udp = new UDP(12345);

        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNext()) {}

        System.out.println("Stopping Services");
        sql.close();
        System.out.println();
        udp.close();
    }
}


