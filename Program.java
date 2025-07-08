import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Program {

    public static class MazeGame extends JFrame implements KeyListener {

        private final char[][] maze = {
    {'S', '.', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.'},
    {'#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#'},
    {'.', '.', '.', '.', '.', '#', '.', '.', '.', '#', '.', '.'},
    {'#', '#', '#', '#', '.', '#', '#', '#', '.', '#', '#', '.'},
    {'.', '.', '.', '#', '.', '.', '.', '#', '.', '.', '.', '.'},
    {'#', '#', '.', '#', '#', '#', '.', '#', '#', '#', '.', '#'},
    {'.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.'},
    {'#', '#', '#', '#', '#', '#', '.', '#', '.', '#', '#', '#'},
    {'.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.', '.'},
    {'#', '#', '#', '#', '.', '#', '.', '#', '#', '#', '.', '#'},
    {'.', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.'},
    {'#', '.', '#', '.', '#', '#', '#', '#', '#', '#', '.', 'E'}
};

        //Usefull variables
        private int playerRow = 0;
        private int playerCol = 0;
        private final int CELL_SIZE = 40;

        private boolean[][] visited;
        private final List<Cell> tracePath = new ArrayList<>(); //trased path
// user window setup
        public MazeGame() {
            setTitle("Maze Puzzle Game");
            setSize(maze[0].length * CELL_SIZE + 20, maze.length * CELL_SIZE + 40);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null); //window on the middle of your screen
            addKeyListener(this);
            setFocusable(true);
            setVisible(true);

            visited = new boolean[maze.length][maze[0].length];
            visited[playerRow][playerCol] = true;
        }
 //adding Graphics
        @Override // Error checking
        public void paint(Graphics g) { //Graphics g built in swing function
            super.paint(g);

            for (int row = 0; row < maze.length; row++) {
                for (int col = 0; col < maze[0].length; col++) {
                    int x = col * CELL_SIZE + 10;//+ 10 & + 30   to leave space for title bar
                    int y = row * CELL_SIZE + 30;

                  
    char cell = maze[row][col];

switch (cell) {
    case '#':
        g.setColor(new Color(60, 60, 60)); // Dark gray 
        break;
    case 'S':
    
        g.setColor(new Color(255, 215, 0)); // Golden 
        break;
    case 'E':
        g.setColor(new Color(0, 200, 0));   // green 
        break;
    default:
        g.setColor(new Color(220, 220, 220)); // Light gray 
        break;
}
g.fillRect(x, y, CELL_SIZE, CELL_SIZE);


                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                }
            }

            g.setColor(Color.BLUE);
            //player's Path (trace line)
            for (int i = 0; i < tracePath.size() -1; i++) {
                Cell from = tracePath.get(i);//current cell
                Cell to = tracePath.get(i + 1);//next cell

                int x1 = from.col * CELL_SIZE + 10 + CELL_SIZE / 2;
                int y1 = from.row * CELL_SIZE + 30 + CELL_SIZE / 2;
                int x2 = to.col * CELL_SIZE + 10 + CELL_SIZE / 2;
                int y2 = to.row * CELL_SIZE + 30 + CELL_SIZE / 2;

                g.drawLine(x1, y1, x2, y2);
            }
        //player's look
            g.setColor(Color.RED);
            int px = playerCol * CELL_SIZE + 10;
            int py = playerRow * CELL_SIZE + 30;
            int playerSize = CELL_SIZE - 13;
            g.fillOval(px + 4, py + 7, playerSize, playerSize);
        }
       
        // Handling arrow keys
        @Override
        public void keyPressed(KeyEvent e) {
            boolean moved = false;

            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    moved = tryMove(playerRow - 1, playerCol);
                    break;
                case KeyEvent.VK_DOWN:
                    moved = tryMove(playerRow + 1, playerCol);
                    break;
                case KeyEvent.VK_LEFT:
                    moved = tryMove(playerRow, playerCol - 1);
                    break;
                case KeyEvent.VK_RIGHT:
                    moved = tryMove(playerRow, playerCol + 1);
                    break;
                default:
                    // invalid key
                    break;
            }

            if (!moved && isStuck()) {
                JOptionPane.showMessageDialog(this, "🚫 You are stuck and cannot escape. Play again!");
            }
        }
        //movement of player
        private boolean tryMove(int newRow, int newCol) {
            if (newRow < 0 || newRow >= maze.length || newCol < 0 || newCol >= maze[0].length)
                return false;
            if (maze[newRow][newCol] == '#') //wall check
                return false;
            if (visited[newRow][newCol])// Already Visited cell
                return false;

            tracePath.add(new Cell(playerRow, playerCol));
            playerRow = newRow;
            playerCol = newCol;
            visited[playerRow][playerCol] = true;

            repaint();

            if (maze[playerRow][playerCol] == 'E') {
                JOptionPane.showMessageDialog(this, "\uD83C\uDF89 You escaped the maze!");
            }

            return true;
        }

        // stuck detection 
        private boolean isStuck() {
            int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
            };

            int escapeOptions = 0;

            for (int[] dir : directions) {
                int newRow = playerRow + dir[0];
                int newCol = playerCol + dir[1];

                if (newRow >= 0 && newRow < maze.length &&
                    newCol >= 0 && newCol < maze[0].length &&
                    maze[newRow][newCol] != '#' &&
                    !visited[newRow][newCol]) {

                    escapeOptions++;

                    // Check if this move possible 
                    if (canEscapeFrom(newRow, newCol)) {
                        return false; // There's a way forward
                    }
                }
            }

            return true; // Fully stuck or all paths are dead ends
        }

        // Look ahead one step to see if the given cell leads anywhere
        private boolean canEscapeFrom(int row, int col) {
            int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
            };

            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (newRow >= 0 && newRow < maze.length &&
                    newCol >= 0 && newCol < maze[0].length &&
                    maze[newRow][newCol] != '#' &&
                    !visited[newRow][newCol]) {
                    return true; 
                }
            }
            return false; 
        }
        private static class Cell {
            int row, col;
            Cell(int row, int col) {
                this.row = row;
                this.col = col;
            }
        }

        @Override public void keyReleased(KeyEvent e) {}
        @Override public void keyTyped(KeyEvent e) {}

        public static void main(String[] args) {
            new MazeGame();
        }

        
    }
}


