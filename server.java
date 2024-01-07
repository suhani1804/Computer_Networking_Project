import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class server {
    public static void main(String[] args) {
        try {
        	System.out.println("Enter port number : ");
        	Scanner sc = new Scanner(System.in);
        	int port = sc.nextInt();
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server waiting for client on port "+port);
            sc.close();
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected from: " + socket.getInetAddress().getHostAddress());
                handleClient(socket);
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void handleClient(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String data = reader.readLine();
            String[] parts = data.split(",");
            int minLength = Integer.parseInt(parts[0]);
            int maxLength = Integer.parseInt(parts[1]);
            String s = parts[2];
            String[] character = s.split("");

            Set<String> combinations = generateCombinations(minLength, maxLength, character);
            writeToFile(combinations);

            sendFileToClient(writer);

            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<String> generateCombinations(int minLength, int maxLength, String[] character) {
        Set<String> combinations = new HashSet<>();

        System.out.println("generating combinations");
        for (int length = minLength; length <= maxLength; length++) {
            generateCombinationsOfEachLength("", length, maxLength, character, combinations);
        }

        return combinations;
    }

    private static void generateCombinationsOfEachLength(String current, int minLength, int maxLength, String[] characters, Set<String> combinations) {
        if (minLength == 0) {
            combinations.add(current);
            return;
        }
        for (String c : characters) {
        	generateCombinationsOfEachLength(current + c, minLength - 1, maxLength, characters, combinations);
        }

        if (minLength < maxLength) {
        	generateCombinationsOfEachLength(current, minLength, maxLength - 1, characters, combinations);
        }
    }

    private static void writeToFile(Set<String> combinations) {
        try (PrintWriter writer = new PrintWriter("result.txt")) {
            for (String combination : combinations) {
                writer.println(combination);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void sendFileToClient(BufferedWriter writer) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("result.txt"))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                writer.write(line + "\n");
                writer.flush();
            }
            writer.write("EOF\n"); 
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
