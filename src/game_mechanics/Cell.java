package game_mechanics;

import general.Controller;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static general.Controller.CELL_SIZE;

public class Cell extends Rectangle {
    private Chip chip;

    public Cell(int x, int y, boolean isBlack) {
        setWidth(CELL_SIZE);
        setHeight(CELL_SIZE);

        relocate(x*CELL_SIZE, y*CELL_SIZE);
        if (isBlack) setFill(Color.GREEN);
        else         setFill(Color.BLUE);

    }

    public void setChip(Chip chip) {
        this.chip = chip;
    }

    public Chip getChip() {
        return chip;
    }

    public boolean hasChip() {
        return chip != null;
    }
}
