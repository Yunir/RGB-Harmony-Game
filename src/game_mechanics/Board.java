package game_mechanics;

import javafx.scene.paint.Color;

import static general.mainController.BOARD_SIZE;

public class Board {

    private boolean r, g, b;

    private final int COUNT_OF_ONE_COLOR_CHIPS = BOARD_SIZE; //maximum height of board (where one_color_chips should be placed)
    private final int COUNT_OF_BLOCKS = 6;
    private Cell[][] cells;

    public Board() {
        cells = new Cell[BOARD_SIZE][BOARD_SIZE];
        generateCells();
        generateChips();
    }

    private void generateCells() {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                Cell cell = new Cell(x, y, CType.getCellTypeByPosX(x));
                cells[x][y] = cell;
            }
        }
    }

    private void generateChips() {
        setPosTo1TypeChips(CType.BLOCK, COUNT_OF_BLOCKS);
        setPosTo1TypeChips(CType.RED, COUNT_OF_ONE_COLOR_CHIPS);
        setPosTo1TypeChips(CType.GREEN, COUNT_OF_ONE_COLOR_CHIPS);
        setPosTo1TypeChips(CType.BLUE, COUNT_OF_ONE_COLOR_CHIPS);
        //printBoard();
    }

    public boolean checkRowCollected(CType chipType) {
        for (int y = 0; y < BOARD_SIZE; y++) {
            if ((!cells[chipType.defPosX][y].hasChip()) || (cells[chipType.defPosX][y].getChip().getChipType() != cells[chipType.defPosX][y].getCellType())) return false;
        }
        changeCellsColor(chipType, true);
        return (r && g && b);

    }

    public void changeCellsColor (CType chipType, boolean isCollected) {
        switch (chipType.id) {
            case 1:
                r = isCollected;
                break;
            case 2:
                g = isCollected;
                break;
            case 3:
                b = isCollected;
                break;
            default:
                break;
        }
        for (int y = 0; y < BOARD_SIZE; y++) {
            cells[chipType.defPosX][y].setFill(Color.web(chipType.color, isCollected?0.5:0.25));
        }
    }

    public boolean getRGBbyId (int id) {
        switch (id) {
            case 1: return r;
            case 2: return g;
            case 3: return b;
            default: return false;
        }
    }

    private void setPosTo1TypeChips(CType type, int countOf) {
        int rand1, rand2;
        for (int i = 0; i < countOf; i++) {
            if (type == CType.BLOCK) {
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
            if (!cells[rand1][rand2].hasChip()) cells[rand1][rand2].setChip(new Chip(type, rand1, rand2));
            else --i;
        }
    }

    private void printBoard() {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                System.out.print((!hasChip(x,y)?null:cells[x][y].getChip().getChipType()) + " \t");
            }
            System.out.println();
        }
    }

    private int getRandomInteger(double min, double max) {
        double x = (Math.random() * ((max - min) + 1)) + min;
        return (int) x;
    }


    public boolean hasChip(int x, int y) {
        return cells[x][y].getChip() != null;
    }
    public void setCell(Cell cell, int x, int y) {
        cells[x][y] = cell;
    }
    public Cell getCell(int x, int y) {
        return cells[x][y];
    }
}
