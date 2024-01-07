import java.io.*;
import java.net.Socket;

public class client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 3000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            sendDataToServer(writer);
            receiveFileFromServer(reader);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendDataToServer(BufferedWriter writer) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.print("Enter Min Length: ");
        String min = consoleReader.readLine();

        System.out.print("Enter Max Length: ");
        String max = consoleReader.readLine();

        System.out.print("Enter Character: ");
        String character = consoleReader.readLine();
        
        System.out.print("sending data to server \n");
        writer.write(min + "," + max + "," + character + "\n");
        writer.flush();
    }

    private static void receiveFileFromServer(BufferedReader reader) throws IOException {
        String line;
        System.out.print("receiving data from server \n");
        try (PrintWriter fileWriter = new PrintWriter("received_result.txt")) {
            while ((line = reader.readLine()) != null && !line.equals("EOF")) {
                fileWriter.println(line);
            }
        }
    }
}

