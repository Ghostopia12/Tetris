
import tests.WindowGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class Board extends JPanel implements KeyListener {

    //private Logger log = Logger.getLogger("logger");
    public static final int FPS = 60;
    private int delay = 1000 / FPS;

    public static int BOARD_WIDTH = 10;
    public static int BOARD_HEIGHT = 20;
    public static int BLOCK_SIZE = 30;
    private Timer looper;
    private Color[][] board = new Color[BOARD_HEIGHT][BOARD_WIDTH];
    private Random random = new Random();
    private boolean gameOver = false;
    private boolean pause = false;
    public boolean line = false;
    public boolean multi = false;
    public int borradas = 0;

    private static Shape nextShape;
    private ArrayList<String> namesPlayers = new ArrayList<>();


    private Color[] colors = {Color.decode("#ed1c24"), Color.decode("#ff7f27"), Color.decode("#fff200"),
            Color.decode("#22b14c"), Color.decode("#00a2e8"), Color.decode("#a349a4"), Color.decode("#3f48cc")};
    private Shape[] shapes = new Shape[7];
    private Shape currentShape;
    private int score = 0;

    private Timer buttonLapse = new Timer(300, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonLapse.stop();
        }
    });


    public Board() {
        // create game looper
        looper = new Timer(delay, new GameLooper());

        shapes[0] = new Shape(new int[][]{
                {1, 1, 1, 1} // I shape;
        }, this, colors[0]);

        shapes[1] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 1, 0}, // T shape;
        }, this, colors[1]);

        shapes[2] = new Shape(new int[][]{
                {1, 1, 1},
                {1, 0, 0}, // L shape;
        }, this, colors[2]);

        shapes[3] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 0, 1}, // J shape;
        }, this, colors[3]);

        shapes[4] = new Shape(new int[][]{
                {0, 1, 1},
                {1, 1, 0}, // S shape;
        }, this, colors[4]);

        shapes[5] = new Shape(new int[][]{
                {1, 1, 0},
                {0, 1, 1}, // Z shape;
        }, this, colors[5]);

        shapes[6] = new Shape(new int[][]{
                {1, 1},
                {1, 1}, // O shape;
        }, this, colors[6]);
        startGame();
    }

    private void update() {

        if (pause || gameOver) {
            return;
        }
        currentShape.update();
    }

    public int getBorradas() {
        return borradas;
    }

    public void aumBorradas(){
        borradas= borradas + 1;
    }

    public void setBorradas(int borradas) {
        this.borradas = borradas;
    }

    public void masBorradas() {
        borradas+=1;
    }

    public void setNextShape() {
        int index = random.nextInt(shapes.length);
        int colorIndex = random.nextInt(colors.length);
        nextShape = new Shape(shapes[index].getCoords(), this, colors[colorIndex]);
    }

    public void setCurrentShape() {
        currentShape = nextShape;
        setNextShape();

        for (int row = 0; row < currentShape.getCoords().length; row++) {
            for (int col = 0; col < currentShape.getCoords()[0].length; col++) {
                if (currentShape.getCoords()[row][col] != 0) {
                    if (board[currentShape.getY() + row][currentShape.getX() + col] != null) {
                        gameOver = true;
                        stopGame();
                    }
                }
            }
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(-5, -5, getWidth(), getHeight());

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {

                if (board[row][col] != null) {
                    g.setColor(board[row][col]);
                    g.fillRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }

            }
        }
        g.setColor(nextShape.getColor());
        for (int row = 0; row < nextShape.getCoords().length; row++) {
            for (int col = 0; col < nextShape.getCoords()[0].length; col++) {
                if (nextShape.getCoords()[row][col] != 0) {
                    g.fillRect(col * BLOCK_SIZE + 320, row * BLOCK_SIZE + 50, Board.BLOCK_SIZE, Board.BLOCK_SIZE);
                }
            }
        }//dibuja siguiente figura
        currentShape.render(g);
        if (pause) {
            String gamePausedString = "GAME PAUSED";
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monserrat", Font.BOLD, 30));
            g.drawString(gamePausedString, 35, WindowGame.HEIGHT / 2);
        }
        if (gameOver) {
            String gameOverString = "GAME OVER";
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monserrat", Font.BOLD, 30));
            g.drawString(gameOverString, 50, Window.HEIGHT / 2);
            if (multi == true) {
                System.exit(0);
            }
        }
        g.setColor(Color.WHITE);

        g.setFont(new Font("Monserrat", Font.BOLD, 20));

        g.drawString("SCORE", Window.WIDTH - 80, Window.HEIGHT / 2);
        g.drawString(score + "", Window.WIDTH - 80, Window.HEIGHT / 2 + 30);

        g.setColor(Color.WHITE);

        for (int i = 0; i < namesPlayers.size(); i++) {

            if (namesPlayers != null) {
                g.drawString(namesPlayers.get(i), Window.WIDTH - 80, (Window.HEIGHT / 2)+10);
            }

        }


        for (int i = 0; i <= BOARD_HEIGHT; i++) {
            g.drawLine(0, i * BLOCK_SIZE, BOARD_WIDTH * BLOCK_SIZE, i * BLOCK_SIZE);
        }
        for (int j = 0; j <= BOARD_WIDTH; j++) {
            g.drawLine(j * BLOCK_SIZE, 0, j * BLOCK_SIZE, BOARD_HEIGHT * BLOCK_SIZE);
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (currentShape.getCaidalibre() == false) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                currentShape.speedUp();
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (currentShape.isStopKeys() != true) {
                    currentShape.setDeltaX(-1);
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (currentShape.isStopKeys() != true) {
                    currentShape.setDeltaX(1);
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (currentShape.isStopKeys() != true) {
                    currentShape.rotateShape();
                }
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            currentShape.speedULTRAMEGAFAST();
        }
        if (multi == false) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                startGame();
            }
            if (e.getKeyCode() == KeyEvent.VK_P && !buttonLapse.isRunning() && !gameOver) {//cambios
                buttonLapse.start();
                pause = !pause;
            }
        }
    }

    public void cambiomulti() {
        multi = !multi;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentShape.speedDown();
        }
    }

    public Color[][] getBoard() {
        return board;
    }

    public void setBoard(Color[][] board) {
        this.board = board;
    }

    public void addScore() {
        score += 50;
    }

    public void stopGame() {
        score = 0;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = null;
            }
        }
        looper.stop();
    }


    public void startGame() {
        stopGame();
        setNextShape();
        setCurrentShape();
        gameOver = false;
        looper.start();
    }

    public boolean isMulti() {
        return multi;
    }

    /*public void win(){
        Graphics g = null;
        super.paintComponent(g);
        String gameOverString = "You Win";
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monserrat", Font.BOLD, 30));
        g.drawString(gameOverString, 50, Window.HEIGHT / 2);
    }*/

    public void up() {
        currentShape.subir();
    }

    public boolean isLine() {
        return line;
    }

    public void setLine(boolean line) {
        this.line = line;
    }

    public int getBoardWidth() {
        return BOARD_WIDTH;
    }

    public int getBoardHeight() {
        return BOARD_HEIGHT;
    }

    public void setBoardHeight(int boardHeight) {
        BOARD_HEIGHT = boardHeight;
    }

    public ArrayList<String> getNamesPlayers() {
        return namesPlayers;
    }

    public void setNamesPlayers(ArrayList<String> namesPlayers) {
        this.namesPlayers = namesPlayers;
    }

    public boolean getGameOver(){
        return gameOver;
    }

    class GameLooper implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            update();
            repaint();
        }

    }

}