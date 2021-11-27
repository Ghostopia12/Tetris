package tests;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server {
    private JFrame ventana = null;
    private JTextArea chat = null;
    private JTextArea txtparticipantes = null;
    private JPanel areaChat = null;
    private JPanel participantes = null;
    private JScrollPane scroll = null;
    private JScrollPane partiscroll = null;
    private ServerSocket servidor = null;
    private Socket socket = null;
    private BufferedReader lector = null;
    Logger log = Logger.getLogger("logger");

    public Server() {
        interfaz();
    }

    public void interfaz() {
        ventana = new JFrame("Servidor");
        chat = new JTextArea(10, 12);
        chat.setEditable(false);
        scroll = new JScrollPane(chat);
        txtparticipantes = new JTextArea(10, 12);
        txtparticipantes.setEditable(false);
        partiscroll = new JScrollPane(txtparticipantes);
        areaChat = new JPanel();
        areaChat.setLayout(new GridLayout(1, 1));
        participantes = new JPanel();
        participantes.setLayout(new GridLayout(1, 1));
        ventana.setLayout(new BorderLayout());
        areaChat.add(scroll);
        participantes.add(partiscroll);
        ventana.add(areaChat, BorderLayout.WEST);
        ventana.add(participantes, BorderLayout.EAST);
        ventana.setSize(500, 300);
        ventana.setVisible(true);
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Thread principal = new Thread(new Runnable() {
            public void run() {
                try {
                    servidor = new ServerSocket(9000);
                    log.info("Se conecta al puerto " + 9000);
                    while (true) {
                        socket = servidor.accept();
                        leer();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        principal.start();
    }

    public void leer() {
        Thread hilo_leer = new Thread(new Runnable() {
            public void run() {
                try {
                    lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (true) {
                        String mensaje_recibido = lector.readLine();
                        chat.append("Cliente dice: " + mensaje_recibido + "\n");
                        log.info("Mensaje: " + mensaje_recibido + ", recibido por otro cliente");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        hilo_leer.start();
    }

    public static void main(String[] args) {
        new Server();
    }
}
