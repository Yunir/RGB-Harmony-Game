package game_mechanics;

import general.Controller;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
    private Chip chip;

    public Cell(int x, int y) {
        setWidth(Controller.CELL_SIZE);
        setHeight(Controller.CELL_SIZE);
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
