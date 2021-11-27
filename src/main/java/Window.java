import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;

public class Window extends JFrame implements MouseListener, MouseMotionListener, KeyListener {
    //private Logger log = Logger.getLogger("logger");
    private int xMove = 0;
    private int yMove = 0;
    public static final int WIDTH = 404, HEIGHT = 720;
    public Design designSelectMode = new Design(0, 0, WIDTH, HEIGHT, Color.BLACK, this, this, 0);
    public Design infoJoin = new Design(0, 0, WIDTH, HEIGHT, Color.BLACK, this, this, 0);
    public Design createJoin = new Design(0, 0, WIDTH, HEIGHT, Color.BLACK, this, this, 0);
    public Design infoCreate = new Design(0, 0, WIDTH, HEIGHT, Color.BLACK, this, this, 0);

    public Design waitingRoom = new Design(0, 0, WIDTH, HEIGHT, Color.BLACK, this, this, 1);
    public Design backgroundForDesign = new Design(0, 0, WIDTH, HEIGHT, Color.BLACK, this, this, 1);
    public Design backgroundForBoard = new Design();
    public Board board;

    // private JTextArea jPanelPaticipa = new JTextArea();


    public Window() throws HeadlessException {
        createPanels();
        init();
    }

    public void init() {
        setTitle("Tetris");
        setSize(WIDTH, HEIGHT);
        setBackground(Color.BLACK);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setFocusable(true);
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }

    public void createPanels() {
        //Data panel selection mode
        designSelectMode.addButtonOrTextField(new JButton("SINGLEPLAYER"), 176, 372, 183, 47, Color.decode("#FCA215"), Color.WHITE, this);
        designSelectMode.addButtonOrTextField(new JButton("MULTIPLAYER"), 176, 435, 183, 47, Color.decode("#8C1D8F"), Color.WHITE, this);
        designSelectMode.addLabel("src/main/java/images/First.png", 0, 0, WIDTH, HEIGHT, this, this);

        //Data panel for create or join a game
        createJoin.addButtonOrTextField(new JButton("CREATE"), 110, 336, 184, 47, Color.decode("#8C1D8F"), Color.WHITE, this);
        createJoin.addButtonOrTextField(new JButton("JOIN"), 110, 421, 184, 47, Color.decode("#8C1D8F"), Color.WHITE, this);
        createJoin.addLabel("src/main/java/images/CreateJoin.png", 0, 0, WIDTH, HEIGHT, this, this);

        //Data panel for join multiplayer
        infoJoin.addButtonOrTextField(new JButton("START"), 110, 470, 184, 47, Color.decode("#8C1D8F"), Color.WHITE, this);
        infoJoin.addButtonOrTextField(new JTextField(), 96, 261, 213, 43, Color.WHITE, Color.GRAY, this);
        infoJoin.addButtonOrTextField(new JTextField(), 96, 361, 212, 44, Color.WHITE, Color.GRAY, this);
        infoJoin.removeBorder(infoJoin.getElements().get(1));
        infoJoin.removeBorder(infoJoin.getElements().get(2));
        infoJoin.addLabel("src/main/java/images/Info.png", 0, 0, WIDTH, HEIGHT, this, this);

        //Data panel for create multiplayer
        infoCreate.addButtonOrTextField(new JButton("START"), 110, 557, 183, 47, Color.decode("#8C1D8F"), Color.WHITE, this);
        infoCreate.addButtonOrTextField(new JTextField(), 96, 261, 213, 45, Color.WHITE, Color.GRAY, this);
        infoCreate.addButtonOrTextField(new JTextField(), 96, 361, 213, 45, Color.WHITE, Color.GRAY, this);
        infoCreate.addButtonOrTextField(new JTextField(), 95, 458, 213, 45, Color.WHITE, Color.GRAY, this);
        infoJoin.removeBorder(infoCreate.getElements().get(1));
        infoJoin.removeBorder(infoCreate.getElements().get(2));
        infoJoin.removeBorder(infoCreate.getElements().get(3));
        infoCreate.addLabel("src/main/java/images/DataGame.png", 0, 0, WIDTH, HEIGHT, this, this);


        backgroundForDesign.addLabel("src/main/java/images/2.png", 0, 0, WIDTH, HEIGHT, this, this);
        waitingRoom.addLabel("src/main/java/images/Loanding.gif", 0, 0, WIDTH, HEIGHT, this, this);

        add(designSelectMode);
    }

    public void startSingleplayer() {
        designSelectMode.setVisible(false);
        board = new Board();
        board.setPreferredSize(new Dimension(415, 610));
        addKeyListener(board);
        backgroundForBoard.setBackground(Color.BLACK);
        backgroundForBoard.setBounds(0, 114, 415, 610);
        backgroundForBoard.addKeyListener(this);
        backgroundForBoard.add(board);
        add(backgroundForDesign);
        add(backgroundForBoard);
    }

    public void startCreateJoin() {
        designSelectMode.setVisible(false);
        add(createJoin);
    }

    public void startJoin() {
        createJoin.setVisible(false);
        add(infoJoin);
    }

    public void startCreate() {
        createJoin.setVisible(false);
        add(infoCreate);
    }

    public void startWaitingRoom() {
        infoJoin.setVisible(false);
        infoCreate.setVisible(false);
        add(waitingRoom);
    }

    public void startMultiplayer(Board b) {
        waitingRoom.setVisible(false);
        b.setPreferredSize(new Dimension(415, 610));
        addKeyListener(b);
        backgroundForBoard.setBackground(Color.BLACK);
        backgroundForBoard.setBounds(0, 114, 415, 610);
        backgroundForBoard.add(b);
        add(backgroundForDesign);
        add(backgroundForBoard);
        b.cambiomulti();
        // add(jPanelPaticipa);
    }

   /* public void agregar(String nom){
        jPanelPaticipa.append(nom + " \n");
    }

    public JTextArea getParticipantes(){
        return jPanelPaticipa;
    }

    public void deleteParticipantes(String participante){
        String auxi = jPanelPaticipa.getText();
        if(auxi.contains(participante)) {
            auxi.replaceAll(participante, "");
        }
        jPanelPaticipa.setText(auxi);
    }*/


    public String getNamePlayer() {
        JTextField name = (JTextField) infoJoin.getElements().get(1);
        System.out.println(name.getText());
        return name.getText();
    }


    public Design getDesignSelectMode() {
        return designSelectMode;
    }

    public Design getInfoJoin() {
        return infoJoin;
    }

    public Design getCreateJoin() {
        return createJoin;
    }

    public Design getInfoCreate() {
        return infoCreate;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() > 345 && e.getX() < 369 && e.getY() > 23 && e.getY() < 54) {
            System.exit(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        yMove = e.getY();
        xMove = e.getX();
        //System.out.println(xMove + " " + yMove);
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
    public void mouseDragged(MouseEvent e) {
        int newX = e.getXOnScreen();
        int newY = e.getYOnScreen();
        this.setLocation(newX - xMove, newY - yMove);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
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
}

