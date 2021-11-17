package snake;


import javax.swing.*;

/**
 * Play the Snake Game
 *
 * @author Muhammed W. Drammeh <wakadrammeh@gmail.com>
 *
 * Enjoy!!!
 */
public class Player {

    public static void main(String[] args) {
        final Nest nest = new Nest();
        final JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setContentPane(nest);
        frame.setJMenuBar(nest.getMenuBar());
        frame.pack();
        frame.setLocationRelativeTo(null);
        SwingUtilities.invokeLater(()-> frame.setVisible(true));
    }

}
