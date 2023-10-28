import javax.swing.*;
import java.awt.*;

public class ChatUDP extends JFrame {
    private JTextArea textAreaMain;
    private JTextField textFieldMessage;
    private final String FRAME_TITLE = "Local chat";
    private final int FRAME_LOC_X = 100;
    private final int FRAME_LOC_Y = 100;
    private final int FRAME_WIDTH = 400;
    private final int FRAME_HEIGHT = 400;
    private final int PORT = 9999;
    private final String IP_BROADCAST = "10.0.1.255";

    private class Receiver extends Thread {
        @Override
        public void start() {
            super.start();
            System.out.println("hello");
        }
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
        btnSend.addActionListener(e ->{
            textAreaMain.append("YES");
        });
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle(FRAME_TITLE);
        frame.setLocation(FRAME_LOC_X,FRAME_LOC_Y);
        frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        frame.setResizable(false);
        frame.getContentPane().add(BorderLayout.NORTH,scrollPane);
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
