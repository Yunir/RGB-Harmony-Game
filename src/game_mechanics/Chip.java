package game_mechanics;

import general.Controller;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class Chip extends Rectangle {
    private ChipColor color;
    private double mouseX, mouseY;
    private double oldX, oldY;

    public Chip(ChipColor c, int x, int y) {
        color = c;

        move(x, y);

        Ellipse vChip = new Ellipse(25, 25);
        vChip.setFill(Color.AQUA);
        vChip.setStroke(Color.BLACK);
        vChip.setStrokeWidth(1);


    }

    public void move(int x, int y) {
        oldX = x;
        oldY = y;
        relocate(oldX, oldY);
    }

    public void moveBack() {
        relocate(oldX, oldY);
    }

    public ChipColor getColor() { return color; }
    public double getOldX() { return oldX; }
    public double getOldY() { return oldY; }
}
