package general;

public class Board {
    private final int SCALE = 5;
    private final int COUNT_OF_ONE_COLOR_CHIPS = SCALE; //maximum height of board (where one_color_chips should be placed)
    private final int COUNT_OF_BLOCKS = 6;
    private Chip[][] cells;

    public Board() {
        cells = new Chip[SCALE][SCALE];
        generateChipPositions();
    }

    private void generateChipPositions() {
        int rand1, rand2;
        for (int i = 0; i < SCALE; i++) {
            rand1 = getRandomInteger(0, SCALE-1);
            rand2 = getRandomInteger(0, SCALE-1);
            if (cells[rand1][rand2] == null) cells[rand1][rand2] = new Chip(ChipColor.YELLOW);
            else --i;
        }
        printBoard();
    }

    private void printBoard() {
        for (int i = 0; i < SCALE; i++) {
            for (int j = 0; j < SCALE; j++) {
                System.out.print((cells[i][j]==null?null:cells[i][j].getColor()) + " \t");
            }
            System.out.println();
        }
    }

    private int getRandomInteger(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return (int)x;
    }
}
