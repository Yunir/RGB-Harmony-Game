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

        move(x, y);

        //*****
        Ellipse vBottomOfChip = new Ellipse(CELL_SIZE*0.3125, CELL_SIZE*0.26);
        vBottomOfChip.setFill(Color.BLACK);

        vBottomOfChip.setStroke(Color.BLACK);
        vBottomOfChip.setStrokeWidth(CELL_SIZE*0.03);


        vBottomOfChip.setTranslateX((CELL_SIZE - CELL_SIZE * 0.3125 * 2) / 2);
        vBottomOfChip.setTranslateY((CELL_SIZE - CELL_SIZE * 0.26 * 2) / 2 + CELL_SIZE * 0.07);
        //*****
        Ellipse vChip = new Ellipse(CELL_SIZE*0.3125, CELL_SIZE*0.26);
        vChip.setFill(Color.web(this.type.color));

        vChip.setStroke(Color.BLACK);
        vChip.setStrokeWidth(CELL_SIZE*0.03);


        vChip.setTranslateX((CELL_SIZE - CELL_SIZE * 0.3125 * 2) / 2);
        vChip.setTranslateY((CELL_SIZE - CELL_SIZE * 0.26 * 2) / 2);

        getChildren().addAll(vBottomOfChip, vChip);

        //*****

        setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });

        setOnMouseDragged(event -> {
            relocate(event.getSceneX() - mouseX + oldX, event.getSceneY() - mouseY + oldY);
        });
        //move(x, y);

    }

    public void move(int x, int y) {
        oldX = x * CELL_SIZE;
        oldY = y * CELL_SIZE;
        relocate(oldX, oldY);
    }

    public void moveBack() {
        relocate(oldX, oldY);
    }

    public ChipType getType() { return type; }
    public double getOldX() { return oldX; }
    public double getOldY() { return oldY; }
}
