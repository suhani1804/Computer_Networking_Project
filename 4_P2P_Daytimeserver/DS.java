import java.net.*;
import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

public class DS{
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(9876);

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                String request = new String(receivePacket.getData(), 0, receivePacket.getLength());

                if (request.equals("RequestTime")) {
                    // Respond with current date and time
                    String response = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    byte[] sendData = response.getBytes();

                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                    socket.send(sendPacket);
                } else if (request.equals("RequestTimeZone")) {
                    // Respond with current time zone
                    String response = TimeZone.getDefault().getID();
                    byte[] sendData = response.getBytes();

                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                    socket.send(sendPacket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
