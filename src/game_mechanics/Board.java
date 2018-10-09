package game_mechanics;

import static general.Controller.BOARD_SIZE;

public class Board {

    private final int COUNT_OF_ONE_COLOR_CHIPS = BOARD_SIZE; //maximum height of board (where one_color_chips should be placed)
    private final int COUNT_OF_BLOCKS = 6;
    private Chip[][] cells;

    public Board() {
        cells = new Chip[BOARD_SIZE][BOARD_SIZE];
        generateChipPositions();
    }

    private void generateChipPositions() {
        setPositionsToOneColorChips(ChipType.YELLOW, COUNT_OF_ONE_COLOR_CHIPS);
        setPositionsToOneColorChips(ChipType.ORANGE, COUNT_OF_ONE_COLOR_CHIPS);
        setPositionsToOneColorChips(ChipType.RED, COUNT_OF_ONE_COLOR_CHIPS);
        setPositionsToOneColorChips(ChipType.BLOCK, COUNT_OF_BLOCKS);
        printBoard();
    }

    private void setPositionsToOneColorChips(ChipType color, int countOf) {
        int rand1, rand2;
        for (int i = 0; i < countOf; i++) {
            rand1 = getRandomInteger(0, BOARD_SIZE-1);
            rand2 = getRandomInteger(0, BOARD_SIZE-1);
            if (cells[rand1][rand2] == null) cells[rand1][rand2] = new Chip(color, rand1, rand2);
            else --i;
        }
    }

    public Chip[][] getCells() {
        return cells;
    }

    private void printBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print((cells[i][j]==null?null:cells[i][j].getType()) + " \t");
            }
            System.out.println();
        }
    }

    private int getRandomInteger(double min, double max) {
        double x = (Math.random() * ((max - min) + 1)) + min;
        return (int) x;
    }

    public boolean hasChip(int x, int y) {
        return cells[x][y] != null;
    }
}
