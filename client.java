import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ClientGUI extends JFrame {

    private JTextField minLengthField, maxLengthField, characterField;

    public ClientGUI() {
        setTitle("Client GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        setContentPane(new JLabel(new ImageIcon("bg.jpg")));
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 153, 255));
        JLabel headerLabel = new JLabel("Client GUI", SwingConstants.CENTER);
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        headerPanel.add(headerLabel);

        add(headerPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 30));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.setOpaque(false);

        inputPanel.add(new JLabel("Min Length:"));
        minLengthField = new JTextField();
        inputPanel.add(minLengthField);

        inputPanel.add(new JLabel("Max Length:"));
        maxLengthField = new JTextField();
        inputPanel.add(maxLengthField);

        inputPanel.add(new JLabel("Character:"));
        characterField = new JTextField();
        inputPanel.add(characterField);

        add(inputPanel, BorderLayout.CENTER);

       
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton sendButton = new JButton("Send to Server");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendDataToServer();
            }
        });
        buttonPanel.add(sendButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void sendDataToServer() {
        try {
            Socket socket = new Socket("127.0.0.1", 3000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String min = minLengthField.getText();
            String max = maxLengthField.getText();
            String character = characterField.getText();

            writer.write(min + "," + max + "," + character + "\n");
            writer.flush();

            receiveFileFromServer(reader);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveFileFromServer(BufferedReader reader) throws IOException {
        String line;
        StringBuilder result = new StringBuilder();
        try (PrintWriter fileWriter = new PrintWriter("received_result.txt")) {
            while ((line = reader.readLine()) != null && !line.equals("EOF")) {
                fileWriter.println(line);
                result.append(line).append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, "Data received from server", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI();
            }
        });
    }
}
