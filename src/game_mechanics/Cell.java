package game_mechanics;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static general.Controller.CELL_SIZE;

public class Cell extends Rectangle {
    private Chip chip;
    private CType cellType;

    public Cell(int x, int y, CType cellType) {
        setWidth(CELL_SIZE);
        setHeight(CELL_SIZE);
        this.cellType = cellType;

        relocate(x*CELL_SIZE, y*CELL_SIZE);

        if(cellType.id != 4) setFill(Color.web(cellType.color, 0.25));
        else setFill(Color.web("#f4f4f8"));
        // Color.web("#e6e6ea")
    }

    public boolean hasChip() { return (chip != null); }

    public void setChip(Chip chip) { this.chip = chip; }
    public Chip getChip() { return chip; }

    public CType getCellType() { return cellType; }
}
