package game_mechanics;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static general.Controller.CELL_SIZE;

public class Chip extends StackPane {
    private ChipType type;
    private double mouseX, mouseY;
    private double oldX, oldY;

    public Chip(ChipType type, int x, int y) {
        this.type = type;

        relocate(x * CELL_SIZE, y * CELL_SIZE);

        //*****
        Ellipse vBottomOfChip = new Ellipse(CELL_SIZE*0.3125, CELL_SIZE*0.26);
        vBottomOfChip.setFill(Color.BLACK);

        vBottomOfChip.setStroke(Color.BLACK);
        vBottomOfChip.setStrokeWidth(CELL_SIZE*0.03);


        vBottomOfChip.setTranslateX((CELL_SIZE - CELL_SIZE * 0.3125 * 2) / 2);
        vBottomOfChip.setTranslateY((CELL_SIZE - CELL_SIZE * 0.26 * 2) / 2 + CELL_SIZE * 0.07);
        //*****
        Ellipse vChip = new Ellipse(CELL_SIZE*0.3125, CELL_SIZE*0.26);
        vChip.setFill(this.type.color);

        vChip.setStroke(Color.BLACK);
        vChip.setStrokeWidth(CELL_SIZE*0.03);


        vChip.setTranslateX((CELL_SIZE - CELL_SIZE * 0.3125 * 2) / 2);
        vChip.setTranslateY((CELL_SIZE - CELL_SIZE * 0.26 * 2) / 2);

        getChildren().addAll(vBottomOfChip, vChip);

        //*****


        //move(x, y);

    }

    /*public void move(int x, int y) {
        oldX = x;
        oldY = y;
        relocate(oldX, oldY);
    }*/

    public void moveBack() {
        relocate(oldX, oldY);
    }

    public ChipType getType() { return type; }
    public double getOldX() { return oldX; }
    public double getOldY() { return oldY; }
}
