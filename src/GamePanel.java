import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class GamePanel extends JPanel implements ActionListener {
    static final int screenWidth = 800;
    static final int screenHeight = 560;
    static final int unitSize = 80;
    static final int gameUnits = (screenWidth*screenHeight) / unitSize;
    static final int delay = 75;
    final int x[] = new int[10];
    final int y[] = new int[7];
    int playerX = 1;
    int playerY = 1;
    int monsterX = 1;
    int monsterY = 6;
    boolean gameWin = false;
    boolean running = false;
    Timer timer;
    Random random;
    char[][] maze = {
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', '#'},
            {'#', ' ', '#', ' ', '#', ' ', '#', '#', ' ', '#'},
            {'#', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
            {'#', ' ', '#', '#', '#', '#', ' ', '#', ' ', '#'},
            {'#', ' ', ' ', ' ', ' ', ' ', ' ', '#', 'O', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}};


    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }
    public void startGame() {
        running=true;
        timer = new Timer(delay, this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if(gameWin == true) {
            System.out.println(true);
            g.setColor(Color.yellow);
            g.drawString("You have won!", 20, 20);
        }else {
            for (int y = 0; y < maze.length; y++) {
                for (int x = 0; x < maze[y].length; x++) {
                    if (maze[y][x] == '#') {
                        g.setColor(Color.white);
                        g.drawRect(x * unitSize, y * unitSize, unitSize, unitSize);
                    } else if (x == playerX && y == playerY) {
                        g.setColor(Color.blue);
                        g.drawOval(x * unitSize, y * unitSize, unitSize, unitSize);
                    } else if (maze[y][x] == 'O') {
                        g.setColor(Color.yellow);
                        g.drawOval(x * unitSize, y * unitSize, unitSize, unitSize);
                    } else if (x == monsterX && y == monsterY) {
                        g.setColor(Color.red);
                        g.drawOval(x * unitSize, y * unitSize, unitSize, unitSize);
                    }

                }
            }
        }

    }
    public void move(char dir) {
        if(dir == 'W') {
            playerX--;
            if(checkCollission() == "wallCollision") {
                playerX++;
                System.out.println("Wall collision");
            }else if(checkCollission() == "goalCollision") {
                gameWin = true;
            }
            else {
                System.out.println("Going west");
                repaint();
            }
        }else if(dir == 'E') {
            playerX++;
            if(checkCollission() == "wallCollision") {
                playerX--;
            }else if(checkCollission() == "goalCollision") {
                gameWin = true;
            }else {
                System.out.println("Going east");
                repaint();
            }
        }else if(dir == 'S') {
            playerY++;
            if(checkCollission() == "wallCollision") {
                playerY--;
            }else if(checkCollission() == "goalCollision") {
                gameWin = true;
            }
            else {
                System.out.println("Going down");
                repaint();
            }
        }else if(dir == 'N') {
            playerY--;
            if(checkCollission() == "wallCollision") {
                playerY++;
            }else if(checkCollission() == "goalCollision") {
                gameWin = true;

            }else {
                System.out.println("Going north");
                repaint();
            }
        }

    }

    public String checkCollission() {
        System.out.println(playerY + " " + playerX);
        if(maze[playerY][playerX] == '#') {
            return "wallCollision";
        }else if(maze[playerY][playerX] == 'O') {
            gameWin = true;
            repaint();
            return "goalCollision";

        }
        else if(playerY == monsterY && playerX == monsterX) {
            return "monsterCollision";
        }else {return "noCollision"; }
    }
    public void gameOver(Graphics g) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {


    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    move('W');

                }
                case KeyEvent.VK_RIGHT -> {
                    move('E');
                }
                case KeyEvent.VK_DOWN -> {
                    move('S');
                }
                case KeyEvent.VK_UP -> {
                    move('N');
                }
            }
        }
    }
}
