import java.awt.*;
import java.util.logging.Logger;

public class Shape {
    //private Logger log = Logger.getLogger("logger");
    private int x = 4, y = 0;
    private int normal = 600;
    private int fast = 50;
    private int ULTRAMEGAFAST = 10;
    private boolean caidalibre =false;
    private int delayTimeForMovement = normal;
    private long beginTime = 0;
    private int deltaX = 0;
    private boolean collision = false;
    private int[][] coords;
    private Board board;
    private Color[][] contenedor;
    private Color color;
    private int delay;
    private long time, lastTime;
    private int[][] reference;
    private boolean moveX = false;
    private int timePassedFromCollision = -1;
    private static Boolean reduceSize = false;
    private boolean stopKeys = false;


    public Shape(int[][] coords, Board board, Color color) {
        this.coords = coords;
        this.board = board;
        contenedor = new Color[board.getBoardHeight()][board.getBoardWidth()];
        this.color = color;
        deltaX = 0;
        x = 4;  
        y = 0;
        delay = normal;
        time = 0;
        lastTime = System.currentTimeMillis();
        reference = new int[coords.length][coords[0].length];


    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void reset() {
        this.x = 4;
        this.y = 0;
        collision = false;
    }

    long deltaTime;


    public void update() {
        moveX = true;
        deltaTime = System.currentTimeMillis() - lastTime;
        time += deltaTime;
        lastTime = System.currentTimeMillis();
        checkLine();

        if (collision && timePassedFromCollision > 500) {
            for (int row = 0; row < coords.length; row++) {
                for (int col = 0; col < coords[0].length; col++) {
                    if (coords[row][col] != 0) {
                        board.getBoard()[y + row][x + col] = color;
                    }
                }
            }
            checkLine();
            board.setCurrentShape();
            timePassedFromCollision = -1;
        }
            // check moving horizontal
            if (!(x + deltaX + coords[0].length > Board.BOARD_WIDTH) && !(x + deltaX < 0)) {

                for (int row = 0; row < coords.length; row++) {
                    for (int col = 0; col < coords[row].length; col++) {
                        if (coords[row][col] != 0) {
                            if (board.getBoard()[y + row][x + deltaX + col] != null) {
                                moveX = false;
                            }

                        }
                    }
                }

                if (moveX) {

                    x += deltaX;
                }

            }
        // Check position + height(number of row) of shape
        if (caidalibre == true){
            if (timePassedFromCollision == -1) {
                if (!(y + 2 + coords.length >= Board.BOARD_HEIGHT + 2)) {

                    for (int row = 0; row < coords.length; row++) {
                        for (int col = 0; col < coords[row].length; col++) {
                            if (coords[row][col] != 0) {

                                if (board.getBoard()[y + 1 + row][x + col] != null) {
                                    collision();
                                    stopKeys = true;
                                }
                            }
                        }
                    }
                    if (time > delay) {
                        y++;
                        time = 0;
                    }
                } else {
                    collision();
                    stopKeys = false;
                    caidalibre = false;
                }
            } else {
                timePassedFromCollision += deltaTime;
            }
        }
        if (timePassedFromCollision == -1) {
            if (!(y + 1 + coords.length > Board.BOARD_HEIGHT)) {

                for (int row = 0; row < coords.length; row++) {
                    for (int col = 0; col < coords[row].length; col++) {
                        if (coords[row][col] != 0) {

                            if (board.getBoard()[y + 1 + row][x + col] != null) {
                                collision();
                                stopKeys = true;
                            }
                        }
                    }
                }
                if (time > delay) {
                    y++;
                    time = 0;
                }
            } else {
                collision();
                stopKeys = false;
            }
        } else {
            timePassedFromCollision += deltaTime;
        }

        deltaX = 0;
    }

    private void collision() {
        collision = true;
        timePassedFromCollision = 0;
    }

    private void checkLine() {
        int size = board.getBoard().length - 1;

        for (int i = board.getBoard().length - 1; i > 0; i--) {
            int count = 0;
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                if (board.getBoard()[i][j] != null) {
                    count++;
                }
                board.getBoard()[size][j] = board.getBoard()[i][j];
            }
            if (count < board.getBoard()[0].length) {
                size--;
            }
            if (count == board.getBoard()[0].length) {
                board.addScore();
                board.setLine(true);
            }
        }
    }

    public void subir(){
        for (int i = board.getBoard().length - 1; i > 0; i--) {
            for (int j = 0; j < board.getBoard()[0].length; j++) {
            }
        }
        board.setBoard(contenedor);
    }


    public void rotateShape() {

        int[][] rotatedShape = null;

        rotatedShape = transposeMatrix(coords);

        rotatedShape = reverseRows(rotatedShape);

        if ((x + rotatedShape[0].length > Board.BOARD_WIDTH) || (y + rotatedShape.length > Board.BOARD_HEIGHT)) {
            return;
        }

        for (int row = 0; row < rotatedShape.length; row++) {
            for (int col = 0; col < rotatedShape[row].length; col++) {
                if (rotatedShape[row][col] != 0) {
                    if (board.getBoard()[y + row][x + col] != null) {
                        stopKeys = true;
                        return;
                    }
                }
            }
        }
        stopKeys = false;
        coords = rotatedShape;
    }

    private int[][] transposeMatrix(int[][] matrix) {
        int[][] temp = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                temp[j][i] = matrix[i][j];
            }
        }
        return temp;
    }

    private int[][] reverseRows(int[][] matrix) {

        int middle = matrix.length / 2;

        for (int i = 0; i < middle; i++) {
            int[] temp = matrix[i];

            matrix[i] = matrix[matrix.length - i - 1];
            matrix[matrix.length - i - 1] = temp;
        }

        return matrix;

    }

    public void render(Graphics g) {
        g.setColor(color);
        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[0].length; col++) {
                if (coords[row][col] != 0) {
                    g.fillRect(col * Board.BLOCK_SIZE + x * Board.BLOCK_SIZE, row * Board.BLOCK_SIZE + y * Board.BLOCK_SIZE, Board.BLOCK_SIZE, Board.BLOCK_SIZE);
                }
            }
        }

    }


    public static Boolean getReduceSize() {
        return reduceSize;
    }

    public static void setReduceSize(Boolean reduceSize) {
        Shape.reduceSize = reduceSize;
    }

    public Color getColor() {
        return color;
    }

    public void setDeltaX(int deltaX) {
        this.deltaX = deltaX;
    }

    public void speedUp() {
        delay = fast;
    }

    public void abajo() {
        y = board.getBoardHeight();
    }

    public void speedDown() {
        delay = normal;
    }

    public void speedULTRAMEGAFAST() {
        caidalibre = true;
        delay = ULTRAMEGAFAST;
    }
    public boolean getCaidalibre(){
        return caidalibre;
    }
    public int[][] getCoords() {
        return coords;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public boolean isStopKeys() {
        return stopKeys;
    }
}