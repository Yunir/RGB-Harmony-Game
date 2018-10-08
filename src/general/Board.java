package general;

public class Board {
    private final int SCALE = 5;
    private Chip[][] cells;

    public Board() {
        cells = new Chip[SCALE][SCALE];
        generateChipPositions();
    }

    private void generateChipPositions() {

    }
}
