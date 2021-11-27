
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class Cliente implements ActionListener, MouseListener, KeyListener {

    //Relacionado con el socket.
    private static Socket clientSocket;
    private static int PORT;
    private PrintWriter out;
    private static String clientName = "";


    private static Window gameTetris;
    private static boolean line = false;
    private static boolean full = false;
    public static Board b;
    public String name;

    public Cliente() {
        gameTetris = new Window();
        addMouseListener();
    }

    public void start(String name) {
        try {
            //PORT = 1234;
            clientName = name;
            clientSocket = new Socket("26.140.232.222", PORT);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            new Thread(new Listener()).start();
            new Thread(new BoardData()).start();
            out.println(clientName);
        } catch (Exception e) {
        }
    }

    public void addMouseListener() {
        gameTetris.getDesignSelectMode().getElements().get(0).addMouseListener(this);
        gameTetris.getDesignSelectMode().getElements().get(1).addMouseListener(this);
        gameTetris.getCreateJoin().getElements().get(0).addMouseListener(this);
        gameTetris.getCreateJoin().getElements().get(1).addMouseListener(this);
        gameTetris.getInfoCreate().getElements().get(0).addMouseListener(this);
        gameTetris.infoJoin.getElements().get(0).addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gameTetris.getDesignSelectMode().getElements().get(0)) {
            gameTetris.startSingleplayer();
        }

        if (e.getSource() == gameTetris.getDesignSelectMode().getElements().get(1)) {
            gameTetris.startCreateJoin();
        }

        if (e.getSource() == gameTetris.getCreateJoin().getElements().get(0)) {
            gameTetris.startCreate();
        }
        if (e.getSource() == gameTetris.getCreateJoin().getElements().get(1)) {
            gameTetris.startJoin();
        }

        if (e.getSource() == gameTetris.getInfoCreate().getElements().get(0)) {
            JTextField x = (JTextField) gameTetris.getInfoCreate().getElements().get(2);
            PORT = Integer.parseInt(x.getText());
            JTextField xx = (JTextField) gameTetris.getInfoCreate().getElements().get(1);
            String name = xx.getText();
            JTextField z = (JTextField) gameTetris.getInfoCreate().getElements().get(3);
            int cant = Integer.parseInt(z.getText());
            Servidor servidor = new Servidor(PORT, cant);
            start(name);
            gameTetris.startWaitingRoom();
        }

        if (e.getSource() == gameTetris.infoJoin.getElements().get(0)) {
            JTextField xx = (JTextField) gameTetris.getInfoJoin().getElements().get(1);
            name = xx.getText();
            JTextField x = (JTextField) gameTetris.getInfoJoin().getElements().get(2);
            System.out.println(x.getText());
            PORT = Integer.parseInt(x.getText());
            start(name);
            gameTetris.startWaitingRoom();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    public class Listener implements Runnable {

        private BufferedReader in;

        @Override
        public void run() {

            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String read;
                while (true) {
                    read = in.readLine();
                    if (read != null && !(read.isEmpty())) {
                        System.out.println(read);
                        if (read.equals("Full")) {
                            b = new Board();
                            full = true;
                            b.addKeyListener(Cliente.this);
                            gameTetris.startMultiplayer(b);
                        }
                        if (read.equals("Delete")) {
                            if (line == true) {
                                line = false;
                            } else {
                                b.setBoardHeight(b.getBoardHeight() - 1);
                                b.up();
                            }
                        }
                    }
                }
                /*if (b.getGameOver() == true){
                    gameTetris.deleteParticipantes(b.getName());
                }*/
            } catch (Exception e) {
                return;
            }

        }


    }

    public class BoardData implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    if (full == true) {
                        if (b.isLine() == true) {
                            out.println("Delete");
                            line = true;
                            b.setLine(false);
                        }
                    }
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }
    }


    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        try {

            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

    }

}
