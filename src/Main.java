import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    static SQL sql;
    static UDP udp;

    public static void main(String[] args) throws Exception {

        sql = new SQL("sensor_user", "sensor_pass");

        System.out.println();

        udp = new UDP(12345, sql);

        udp.start();

        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNext()) {}
        stopServices();
    }

    public static void stopServices() throws SQLException {
        System.out.println("Stopping Services");
        sql.close();
        System.out.println();
        udp.close();
    }

}


