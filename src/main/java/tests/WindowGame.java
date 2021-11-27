package tests;

import javax.swing.*;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class WindowGame extends JFrame implements ActionListener {
    public static final int WIDTH = 445, HEIGHT = 629;
    private Logger log = Logger.getLogger("logger");
    private JButton single = new JButton("Single");
    private JButton multi = new JButton("Multi");
    private JPanel jpanel;


    public WindowGame() {
        setName("Tetris");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        single.setBounds(50, 50, 150, 150);
        single.addActionListener(this::actionPerformed);
        multi.setBounds(150, 50, 150, 150);
        multi.addActionListener(this::actionPerformed);
        jpanel = new JPanel();
        jpanel.setLayout(null);
        jpanel.setBackground(Color.white);
        this.getContentPane().add(jpanel);
        JMenuBar menuBar = new JMenuBar();
        JMenu mnuImagen = new JMenu("Opciones");
        JMenuItem item = new JMenuItem("Acerca de");
        JMenuItem item2 = new JMenuItem("Salir");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Se jugara un battle royale de jugadores");
            }
        });
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mnuImagen.add(item);
        mnuImagen.add(item2);
        menuBar.add(mnuImagen);
        this.setJMenuBar(menuBar);
        jpanel.add(single);
        jpanel.add(multi);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clic = (JButton) e.getSource();
        if (clic.equals(single)) {
            FrameSingle single = new FrameSingle();
            setVisible(false); //esto permite eliminar la ventana aterior
        }
        if (clic.equals(multi)) {
            FrameMulti multi = new FrameMulti();
            setVisible(false); //esto permite eliminar la ventana aterior
        }

    }

    public static void main(String[] args) {
        new WindowGame();
    }
}
