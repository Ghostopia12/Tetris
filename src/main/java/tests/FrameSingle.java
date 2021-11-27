package tests;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class FrameSingle extends JFrame {
    public static final int WIDTH = 445, HEIGHT = 629;
    //private Board board;
    private Logger log = Logger.getLogger("logger");


    public FrameSingle() {
        setName("Tetris");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        //board = new Board();
        //addKeyListener(board);
        //add(board);
        JMenuBar menuBar = new JMenuBar();
        JMenu mnuImagen = new JMenu("Opciones");
        JMenuItem item = new JMenuItem("Multiplayer");
        JMenuItem item2 = new JMenuItem("Acerca de");
        JMenuItem item3 = new JMenuItem("Salir");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Se inicia multi");
                FrameMulti multi = new FrameMulti();
                setVisible(false);
            }
        });
        item2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Se jugara un battle royale de jugadores");
            }
        });
        item3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mnuImagen.add(item);
        mnuImagen.add(item2);
        mnuImagen.add(item3);
        menuBar.add(mnuImagen);
        this.setJMenuBar(menuBar);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FrameSingle();
    }

}
