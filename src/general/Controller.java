package general;

import game_mechanics.Board;
import game_mechanics.Cell;
import game_mechanics.Chip;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sun.font.FontRunIterator;

public class Controller {

    public static final int CELL_SIZE = 100;
    public static final int BOARD_SIZE = 5; // 5 CELL_SIZES

    @FXML private BorderPane vBase;
    //@FXML private GridPane vBoard;
    @FXML private Label vIntro;

    private Pane vBoard;

    private Group cellsGroup;
    private Group chipsGroup;

    private Board board;

    public Controller() {
        vBoard = new Pane();
        cellsGroup = new Group();
        chipsGroup = new Group();

        board = new Board();

    }

    @FXML
    private void initialize() {
        vBoard.setPrefSize(CELL_SIZE*BOARD_SIZE, CELL_SIZE*BOARD_SIZE);

        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                Cell cell = new Cell(x, y, (x+y)%2 == 0);
                cellsGroup.getChildren().add(cell);
            }
        }

        vBase.getChildren().addAll(cellsGroup, chipsGroup);
        vBase.getChildren().add(vBoard);
        addChipsToGroup();
    }

    private void addChipsToGroup() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board.getCells()[i][j] != null) {
                    chipsGroup.getChildren().add(board.getCells()[i][j]);

                }
            }
        }
    }

    private void drawChip(Chip chip) {

    }

}
