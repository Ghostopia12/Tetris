import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Servidor {

    //Relacionado con el socket.
    private static HashMap<String, PrintWriter> connectedClients = new HashMap<>();
    private  static int MAX_CONNECTED;
    private static int PORT;
    private static ServerSocket server;
    private static volatile boolean exit = false;
    private HashMap clienteColl = new HashMap();

    public Servidor(int port, int cantidad) {
        //getRandomPort();
        MAX_CONNECTED = cantidad;
        PORT = port;
        start();
    }

    public static void start() {
        new Thread(new ServerHandler()).start();
    }

    private static int getRandomPort() {
        int port = 1234;
        PORT = port;
        return port;
    }

    private static class ServerHandler implements Runnable {

        @Override
        public void run() {
            System.out.println("El servidor tiene el puerto: " + PORT);
            try {
                server = new ServerSocket(PORT);
                while (!exit) {
                    if (connectedClients.size() <= MAX_CONNECTED) {
                        new Thread(new ClientHandler(server.accept())).start();

                    }
                }
            } catch (Exception e) {

            }
        }
    }


    private static void difusionMensaje(String message) {
        for (PrintWriter p : connectedClients.values()) {
            p.println(message);
        }
    }

    private static class ClientHandler implements Runnable {

        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String name;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                while (true) {
                    name = in.readLine();
                    if (name == null) {
                        return;
                    }
                    if (!name.isEmpty() && !connectedClients.keySet().contains(name)) {
                        break;
                    } else {
                        out.println("Invalido");
                    }

                }
                System.out.println("Se unió al juego.- " + name);
                connectedClients.put(name, out);
                String message;
                if (connectedClients.size() == MAX_CONNECTED) {
                    difusionMensaje("Full");
                }
                while ((message = in.readLine()) != null && !exit) {
                    if (!message.isEmpty()) {
                        if (message.toLowerCase().equals("/salir")) {
                            break;
                        }

                        difusionMensaje(message);

                    }
                }
            } catch (Exception e) {

            } finally {
                if (name != null) {
                    connectedClients.remove(name);
                    difusionMensaje(name + " salió del juego");
                }
            }
        }
    }

}
