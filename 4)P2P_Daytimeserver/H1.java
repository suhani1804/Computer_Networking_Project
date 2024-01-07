import java.net.*;

public class H1 {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();

            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 9876;

            // Requesting current date and time from DS
            String requestTime = "RequestTime";
            byte[] sendDataTime = requestTime.getBytes();

            DatagramPacket sendPacketTime = new DatagramPacket(sendDataTime, sendDataTime.length, serverAddress, serverPort);
            socket.send(sendPacketTime);

            // Receiving current date and time from DS
            byte[] receiveDataTime = new byte[1024];
            DatagramPacket receivePacketTime = new DatagramPacket(receiveDataTime, receiveDataTime.length);
            socket.receive(receivePacketTime);

            String responseTime = new String(receivePacketTime.getData(), 0, receivePacketTime.getLength());
            System.out.println("H1 received data and time: " + responseTime);

            // Requesting time zone from DS
            String requestTimeZone = "RequestTimeZone";
            byte[] sendTimeZone = requestTimeZone.getBytes();

            DatagramPacket sendPacketTimeZone = new DatagramPacket(sendTimeZone, sendTimeZone.length, serverAddress, serverPort);
            socket.send(sendPacketTimeZone);

            // Receiving time zone from DS
            byte[] receiveDataTimeZone = new byte[1024];
            DatagramPacket receivePacketTimeZone = new DatagramPacket(receiveDataTimeZone, receiveDataTimeZone.length);
            socket.receive(receivePacketTimeZone);

            String responseTimeZone = new String(receivePacketTimeZone.getData(), 0, receivePacketTimeZone.getLength());
            System.out.println("H1 received time zone: " + responseTimeZone);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
