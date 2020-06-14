package eu.mrndesign.matned.animationTest;

import javax.swing.*;
import java.awt.*;

class Smiley {

    private static final int RANDOM_RANGE = 50;
    private static final int RANDOM_MOVE_AMOUNT = 3;
    private static final int MINIMAL_AMOUNT = 1;
    private static final int X_MIN_BORDER = 0;
    private static final int Y_MIN_BORDER = 0;

    private static final Image smiley = new ImageIcon("res/ic_child_care.png").getImage();

    private final JPanel panel;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private final int smileySpeed;
    private final int smileyWidth = smiley.getWidth(null);
    private final int smileyHeight = smiley.getWidth(null);

    Smiley(JPanel panel) {
        this.panel = panel;
        int panelWidth = this.panel.getWidth();
        int panelHeight = this.panel.getHeight();
        generateDelta();
        smileySpeed = ((int) (Math.random() * 7)) + MINIMAL_AMOUNT;
        this.x = (int) (Math.random() * (panelWidth - smileyWidth));
        this.y = (int) (Math.random() * (panelHeight - smileyHeight));
    }

    private void generateDelta() {
        int randomX = ((int) (Math.random() * RANDOM_RANGE)) - RANDOM_RANGE;
        int randomY = ((int) (Math.random() * RANDOM_RANGE)) - RANDOM_RANGE;
        int randomXMove = ((int) (Math.random() * RANDOM_MOVE_AMOUNT)) + MINIMAL_AMOUNT;
        int randomYMove = ((int) (Math.random() * RANDOM_MOVE_AMOUNT)) + MINIMAL_AMOUNT;
        if(randomX >= X_MIN_BORDER) dx = randomXMove;
        else dx = -MINIMAL_AMOUNT * randomXMove;
        if(randomY >= Y_MIN_BORDER) dy = randomYMove;
        else dy = -MINIMAL_AMOUNT * randomYMove;
    }

    public void move() {
        Rectangle panelBounds = this.panel.getBounds();
        x += dx;
        y += dy;
        if (y + smileyHeight >= panelBounds.getMaxY()) {
            dy = -dy;
        }
        if (y <= 0) {
            dy = -dy;
        }
        if (x + smileyWidth >= panelBounds.getMaxX()) {
            dx = -dx;
        }
        if (x <= 0) {
            dx = -dx;
        }
    }

    public static Image getImage() {
        return Smiley.smiley;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSmileySpeed() {
        return smileySpeed;
    }
}
