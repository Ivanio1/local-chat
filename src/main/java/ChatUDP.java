import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.util.regex.*;

public class ChatUDP extends JFrame {
    private JTextArea textAreaMain;
    private JTextField textFieldMessage;
    private final String FRAME_TITLE = "Local chat";
    private final int FRAME_LOC_X = 100;
    private final int FRAME_LOC_Y = 100;
    private final int FRAME_WIDTH = 400;
    private final int FRAME_HEIGHT = 400;
    private final int PORT = 9999;
    private final String IP_BROADCAST = "192.168.0.255";

    private class Receiver extends Thread {
        @Override
        public void start() {
            super.start();
            try {
                customize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void customize() throws Exception {
        DatagramSocket receiveSocket = new DatagramSocket(PORT);
        Pattern regex = Pattern.compile("[\u0020-\uFFFF]");
        while(true){
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
            receiveSocket.receive(receivePacket);
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String sentence = new String(receivePacket.getData());
            Matcher m = regex.matcher(sentence);
            textAreaMain.append(IPAddress.toString()+":"+port+" - ");
            while(m.find()){
                textAreaMain.append(sentence.substring(m.start(),m.end()));
            }
            textAreaMain.append("\r\n");
            textAreaMain.setCaretPosition(textAreaMain.getText().length());
        }
    }

    private void btnSendHandler() throws Exception {
        DatagramSocket sendSocket = new DatagramSocket();
        InetAddress IPAdress = InetAddress.getByName(IP_BROADCAST);
        byte[] sendData;
        String sentence = textFieldMessage.getText();
        textFieldMessage.setText("");
        sendData = sentence.getBytes("UTF-8");
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAdress, PORT);
        sendSocket.send(sendPacket);

    }

    private void frameDraw(JFrame frame) {
        textFieldMessage = new JTextField();
        textAreaMain = new JTextArea(FRAME_HEIGHT / 19, 50);
        JScrollPane scrollPane = new JScrollPane(textAreaMain);
        scrollPane.setLocation(0, 0);
        textAreaMain.setLineWrap(true);
        textAreaMain.setEditable(false);
        JButton btnSend = new JButton();
        btnSend.setText("Send");
        btnSend.setToolTipText("Broadcast a message");
        btnSend.addActionListener(e -> {
            try {
                btnSendHandler();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle(FRAME_TITLE);
        frame.setLocation(FRAME_LOC_X, FRAME_LOC_Y);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);
        frame.getContentPane().add(BorderLayout.NORTH, scrollPane);
        frame.getContentPane().add(BorderLayout.CENTER, textFieldMessage);
        frame.getContentPane().add(BorderLayout.EAST, btnSend);
        frame.setVisible(true);
    }

    private void antistatic() {
        frameDraw(new ChatUDP());
        new Receiver().start();
    }

    public static void main(String[] args) {
        ChatUDP chatUDP = new ChatUDP();
        chatUDP.antistatic();
    }
}
