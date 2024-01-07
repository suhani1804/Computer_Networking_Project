import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class H2 {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();

            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 9876;

            // Requesting current date and time from H1
            String requestTimeFromH1 = "RequestTime";
            byte[] sendDataTimeFromH1 = requestTimeFromH1.getBytes();

            DatagramPacket sendPacketTimeFromH1 = new DatagramPacket(sendDataTimeFromH1, sendDataTimeFromH1.length, serverAddress, serverPort);
            socket.send(sendPacketTimeFromH1);

            // Receiving current date and time from H1
            byte[] receiveDataTimeFromH1 = new byte[1024];
            DatagramPacket receivePacketTimeFromH1 = new DatagramPacket(receiveDataTimeFromH1, receiveDataTimeFromH1.length);
            socket.receive(receivePacketTimeFromH1);

            String responseTimeFromH1 = new String(receivePacketTimeFromH1.getData(), 0, receivePacketTimeFromH1.getLength());
            System.out.println("H2 received data and time from H1: " + responseTimeFromH1);

            // Calculate time difference
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date localTimeOnH2 = new Date();
            String localTimeStr = dateFormat.format(localTimeOnH2);
            System.out.println("H2 local time: " + localTimeStr);

            Date timeFromH1 = dateFormat.parse(responseTimeFromH1);
            long timeDifferenceMillis = localTimeOnH2.getTime() - timeFromH1.getTime();
            System.out.println("Time difference between H2 and H1: " + timeDifferenceMillis + " milliseconds");

            // Requesting time zone from H1
            String requestTimeZoneFromH1 = "RequestTimeZone";
            byte[] sendDataTimeZoneFromH1 = requestTimeZoneFromH1.getBytes();

            DatagramPacket sendPacketTimeZoneFromH1 = new DatagramPacket(sendDataTimeZoneFromH1, sendDataTimeZoneFromH1.length, serverAddress, serverPort);
            socket.send(sendPacketTimeZoneFromH1);

            // Receiving time zone from H1
            byte[] receiveDataTimeZoneFromH1 = new byte[1024];
            DatagramPacket receivePacketTimeZoneFromH1 = new DatagramPacket(receiveDataTimeZoneFromH1, receiveDataTimeZoneFromH1.length);
            socket.receive(receivePacketTimeZoneFromH1);

            String timeZoneFromH1 = new String(receivePacketTimeZoneFromH1.getData(), 0, receivePacketTimeZoneFromH1.getLength());
            System.out.println("H2 received time zone from H1: " + timeZoneFromH1);

            // Compare time zone difference
            String localTimeZoneOnH2 = TimeZone.getDefault().getID();
            System.out.println("H2 local time zone: " + localTimeZoneOnH2);

            if (timeZoneFromH1.equals(localTimeZoneOnH2)) {
                System.out.println("H1 and H2 are in the same time zone.");
            } else {
                System.out.println("H1 and H2 are in different time zones.");
                System.out.println("Time zone difference: " + (timeZoneFromH1.equals(localTimeZoneOnH2) ? "0" : "Unknown"));
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
