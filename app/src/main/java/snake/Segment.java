package snake;

/**
 * A segment is a single, separate part of a snake's body.
 * A snakes moves (runs) by actually dragging its segments
 * along the appropriate direction.
 * @see Serpent#run(char, Egg)
 */
public class Segment {
    int x;
    int y;


    public Segment(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void move(int dx, int dy){
        x += dx;
        y += dy;
    }

    public void translate(Segment seg){
        x = seg.x;
        y = seg.y;
    }

}
