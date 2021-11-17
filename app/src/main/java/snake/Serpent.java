package snake;

import java.util.ArrayList;

/**
 * A snake (serpent) is a definite, ordered collection of segments.
 * These segments make up the snake's body with the head as its
 * first element, and the tail, the last.
 */
public class Serpent {
    private ArrayList<Segment> body;


    public Serpent(){
        body = new ArrayList<>();
        for (int i = 2; i >= 0; i--) {
            body.add(new Segment(i * Nest.UNIT_SIZE, 0));
        }
    }

    public Segment segmentAt(int i){
        return body.get(i);
    }

    public int length(){
        return body.size();
    }

    public Segment head() {
        return segmentAt(0);
    }

    public Segment tail() {
        return segmentAt(length() - 1);
    }

    /**
     * Moves this serpent in the given direction, dir.
     * This is a single advancement along a box of the nest
     * in search of the present, given egg.
     * If the egg is found, eaten the snake will grow, and
     * the egg will regenerate and reposition itself within the nest,
     * as specified by {@link Egg#replace()}.
     */
    public void run(char dir, Egg egg){
        for (int i = length() - 1; i > 0; i--) {
            body.get(i).translate(body.get(i - 1));
        }
        final Segment head = head();
        switch (dir){
            case Direction.RIGHT:{
                head.move(Nest.UNIT_SIZE, 0);
                break;
            }
            case Direction.LEFT:{
                head.move(-Nest.UNIT_SIZE, 0);
                break;
            }
            case Direction.UP:{
                head.move(0, -Nest.UNIT_SIZE);
                break;
            }
            case Direction.DOWN:{
                head.move(0, Nest.UNIT_SIZE);
                break;
            }
        }
        if (head().x == egg.x && head().y == egg.y) {
//            Toolkit.getDefaultToolkit().beep();
            grow();
            egg.replace();
        }
    }

    public void grow(){
        final Segment tail = tail();
        body.add(new Segment(tail.x, tail.y));
    }

}
