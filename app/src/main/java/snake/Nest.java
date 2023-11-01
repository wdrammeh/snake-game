package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.Duration;

/**
 * The nest is the container wherein is a snake, and an egg.
 * The nest also provides the graphics and actions for operations
 * in a nest; i.e snake eating eggs and growing its body proportionally.
 */
public class Nest extends JPanel {
    private Graphics2D graphics;
    private Egg egg;
    private Serpent serpent;
    private final Timer timer;
    private Direction direction;
    /**
     * Decides whether it's permissible to trigger keyboard
     * input, in order to change the course of the snake.
     * It is noticed that if the user inputs at least twice
     * while the snake is still advancing its body-part, the
     * head may clash with the body.
     * To forestall this clash, user-inputs are ignored
     * within this time-frame.
     * This is an issue, and it's to be referred to.
     */
    private boolean active;
    private boolean failed;
    private boolean isPlaying;
    private JMenuBar menuBar;
    private final Theme theme;
    // Frame Dimension
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    public static final int UNIT_SIZE = 25;
    // Snake Speed
    public static final int SLOW = 1000/5;
    public static final int AVERAGE = 1000/10;
    public static final int FAST = 1000/20;


    public Nest() {
        egg = new Egg();
        serpent = new Serpent();
        direction = new Direction(Direction.RIGHT);
        timer = new Timer(AVERAGE, e-> {
            active = true;
            serpent.run(direction.getPath(), egg);
            failed = collide();
            if (failed) {
                stop();
                UIManager.getLookAndFeel().provideErrorFeedback(getParent());
            }
            repaint();
        });
        theme = new Theme(this);

        setupMenuBar();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                final int code = e.getKeyCode();
                if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
                    if (isPlaying) {
                        stop();
                    } else if (failed) {
                        restart();
                    } else {
                        play();
                    }
                } else if (code == KeyEvent.VK_N) { // press "N", while playing, to reposition the egg
                    egg.replace();
                }
                if (active) {
                    direction.setPath(code);
                    active = false;
                } else { // Suspend the action then for some arbitrary time
                    final Timer aTimer = new Timer(timer.getDelay(), e1 -> {
                        direction.setPath(code);
                    });
                    aTimer.setRepeats(false);
                    aTimer.start();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawSerpent();
        placeEgg();
        writeScore();
        if (failed) {
            writeFailMessage();
        }
    }

    private void placeEgg() {
        graphics.setColor(theme.eggColor);
        graphics.fillOval(egg.x, egg.y, UNIT_SIZE, UNIT_SIZE);
    }

    private void drawSerpent() {
        for (int i = serpent.length() - 1; i >= 0; i--) {
            final Segment seg = serpent.segmentAt(i);
            if (seg == serpent.head()) {
                graphics.setColor(theme.snakeHeadColor);
                graphics.fillRect(seg.x, seg.y, UNIT_SIZE, UNIT_SIZE);
                final int eyeSpace = 5;
                graphics.setColor(theme.snakeEyesColor);
                graphics.fillOval(seg.x + UNIT_SIZE/2 - eyeSpace, seg.y + UNIT_SIZE/5,
                        eyeSpace, eyeSpace);
                graphics.fillOval(seg.x + UNIT_SIZE/2 - eyeSpace,
                        seg.y + UNIT_SIZE - (UNIT_SIZE/5)*2, eyeSpace, eyeSpace);
            } else {
                graphics.setColor(theme.snakeBodyColor);
                graphics.fillRect(seg.x, seg.y, UNIT_SIZE, UNIT_SIZE);
            }
        }
    }

    private void writeScore() {
        graphics.setFont(new Font("Ink Free", Font.BOLD, 20));
        final String scoreText = "Score: "+getScore();
        graphics.setColor(theme.labelColor);
        graphics.drawString(scoreText, 4, HEIGHT - 4);
    }

    private void writeFailMessage() {
        graphics.setFont(new Font("Ink Free", Font.BOLD, 100));
        final FontMetrics metrics = getFontMetrics(graphics.getFont());
        final String overText = "Game Over";
        graphics.setColor(theme.labelColor);
        graphics.drawString(overText, (WIDTH - metrics.stringWidth(overText))/2, HEIGHT/2);
    }

    private boolean collide() {
       //  Get the head
        final Segment head = serpent.head();

       // collision of the bounds
        if (head.x < 0 || head.x >= WIDTH) {
            return true;
        } else if (head.y < 0 || head.y >= HEIGHT) {
            return true;
        }

       // collision of body parts
        for (int i = 1; i < serpent.length(); i++) {
            final Segment seg = serpent.segmentAt(i);
            if (head.x == seg.x && head.y == seg.y) {
                return true;
            }
        }

        return false;
    }

    private int getScore() {
        return (serpent.length() - 3) * 10;
    }

    private void play() {
        isPlaying = true;
        timer.start();
    }

    private void stop() {
        timer.stop();
        isPlaying = false;
    }

    private void restart() {
        egg = new Egg();
        serpent = new Serpent();
        direction = new Direction(Direction.RIGHT);
        failed = false;
        isPlaying = true;
        timer.start();
    }

    private void setupMenuBar() {
        final Font itemFont = new Font("Arial", Font.BOLD, 15);

        final JMenuItem slowItem = new JMenuItem("Slow");
        slowItem.setFont(itemFont);
        slowItem.addActionListener(e-> timer.setDelay(SLOW));

        final JMenuItem avgItem = new JMenuItem("Average");
        avgItem.setFont(itemFont);
        avgItem.addActionListener(e-> timer.setDelay(AVERAGE));

        final JMenuItem fastItem = new JMenuItem("Fast");
        fastItem.setFont(itemFont);
        fastItem.addActionListener(e-> timer.setDelay(FAST));

        final JMenu speedMenu = new JMenu("Speed");
        speedMenu.setMnemonic(KeyEvent.VK_S);
        speedMenu.add(slowItem);
        speedMenu.add(avgItem);
        speedMenu.add(fastItem);

        final JMenuItem whiteItem = new JMenuItem("Light");
        whiteItem.setFont(itemFont);
        whiteItem.addActionListener(e-> theme.setLight());

        final JMenuItem blackItem = new JMenuItem("Dark");
        blackItem.setFont(itemFont);
        blackItem.addActionListener(e-> theme.setDark());

        // final JMenuItem greenItem = new JMenuItem("Grass Land");
        // greenItem.setFont(itemFont);
        // greenItem.addActionListener(e-> theme.setGrassLand());

        final JMenu themeMenu = new JMenu("Theme");
        themeMenu.setMnemonic(KeyEvent.VK_T);
        themeMenu.add(whiteItem);
        themeMenu.add(blackItem);
        // themeMenu.add(greenItem);

        menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        menuBar.add(speedMenu);
        menuBar.add(themeMenu);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

}
