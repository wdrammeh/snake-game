package snake;

import java.awt.event.KeyEvent;

/**
 * Direction determines the course our snake should take.
 * This is to be dictated by the user on the go.
 */
public class Direction {
    private char path;
    public static final char RIGHT = 'R';
    public static final char LEFT = 'L';
    public static final char UP = 'U';
    public static final char DOWN = 'D';


    public Direction(char path){
        this.path = path;
    }

    public char getPath() {
        return path;
    }

    public void setPath(int path) {
        switch (path){
            case KeyEvent.VK_RIGHT: {
                if (this.path != LEFT) {
                    this.path = RIGHT;
                }
                break;
            }
            case KeyEvent.VK_LEFT: {
                if (this.path != RIGHT) {
                    this.path = LEFT;
                }
                break;
            }
            case KeyEvent.VK_UP: {
                if (this.path != DOWN) {
                    this.path = UP;
                }
                break;
            }
            case KeyEvent.VK_DOWN: {
                if (this.path != UP) {
                    this.path = DOWN;
                }
                break;
            }
        }
    }

}
