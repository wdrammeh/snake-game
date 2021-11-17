package snake;

import java.util.Random;

/**
 * An egg is the food of our snake that is capable of
 * repositioning itself anywhere in the nest as the
 * snake eats and grows.
 */
public class Egg {
    int x;
    int y;
    private Random r;


    public Egg(){
        r = new Random();
        replace();
    }

    /**
     * Repositions this egg, randomly, within the nest.
     * Call this if an egg is been eaten.
     */
    public void replace(){
        x = r.nextInt(Math.floorDiv(Nest.WIDTH, Nest.UNIT_SIZE)) * Nest.UNIT_SIZE;
        y = r.nextInt(Math.floorDiv(Nest.HEIGHT, Nest.UNIT_SIZE)) * Nest.UNIT_SIZE;
    }

}
