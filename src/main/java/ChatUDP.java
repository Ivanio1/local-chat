import javax.swing.*;

public class ChatUDP extends JFrame {
    private class Receiver extends Thread {
        @Override
        public void start() {
            super.start();
            System.out.println("hello");
        }
    }

    private void frameDraw(JFrame frame) {

    }

    private void antistatic() {
        new Receiver().start();
    }

    public static void main(String[] args) {
        new ChatUDP().antistatic();
    }
}
