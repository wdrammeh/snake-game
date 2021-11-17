package snake;

import javax.swing.*;
import java.awt.*;

public class Theme {
    Color backgroundColor;
    Color borderColor;
    Color snakeHeadColor;
    Color snakeEyesColor;
    Color snakeBodyColor;
    Color eggColor;
    Color labelColor;
    JComponent target;


    public Theme(JComponent target){
        this.target = target;
        setDark();
    }

    void setLight(){
        backgroundColor = Color.WHITE;
        borderColor = Color.BLACK;
        snakeHeadColor = Color.BLACK;
        snakeEyesColor = Color.WHITE;
        snakeBodyColor = Color.GRAY;
        eggColor = Color.BLUE;
        labelColor = Color.BLACK;
        apply();
    }

    void setDark(){
        backgroundColor = Color.BLACK;
        borderColor = Color.WHITE;
        snakeHeadColor = Color.GREEN;
        snakeEyesColor = Color.BLACK;
        snakeBodyColor = Color.YELLOW;
        eggColor = Color.RED;
        labelColor = Color.WHITE;
        apply();
    }

    void setGrassLand(){
        backgroundColor = Color.GREEN;
        borderColor = Color.GREEN;
        snakeHeadColor = Color.BLACK;
        snakeEyesColor = Color.WHITE;
        snakeBodyColor = Color.YELLOW;
        eggColor = Color.RED;
        labelColor = Color.BLACK;
        apply();
    }

    private void apply(){
        target.setBackground(backgroundColor);
        target.setBorder(BorderFactory.createLineBorder(borderColor));
    }

}
