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
        setPosTo1TypeChips(ChipType.BLOCK, COUNT_OF_BLOCKS);
        setPosTo1TypeChips(ChipType.YELLOW, COUNT_OF_ONE_COLOR_CHIPS);
        setPosTo1TypeChips(ChipType.ORANGE, COUNT_OF_ONE_COLOR_CHIPS);
        setPosTo1TypeChips(ChipType.RED, COUNT_OF_ONE_COLOR_CHIPS);
        printBoard();
    }

    private void setPosTo1TypeChips(ChipType type, int countOf) {
        int rand1, rand2;
        for (int i = 0; i < countOf; i++) {
            if (type == ChipType.BLOCK) {
                if(i<2) rand1 = 1;
                else if(i<4) rand1 = 3;
                else {
                    rand1 = getRandomInteger(0, 1);
                    rand1 = (rand1 == 0) ? 1 : 3;
                }
            } else {
                rand1 = getRandomInteger(0, BOARD_SIZE-1);
            }
            rand2 = getRandomInteger(0, BOARD_SIZE-1);
            if (cells[rand1][rand2] == null) cells[rand1][rand2] = new Chip(type, rand1, rand2);
            else --i;
        }
    }

    public Chip[][] getCells() {
        return cells;
    }

    private void printBoard() {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                System.out.print((cells[x][y]==null?null:cells[x][y].getType()) + " \t");
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
