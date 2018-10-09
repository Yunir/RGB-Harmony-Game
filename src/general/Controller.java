package general;

import game_mechanics.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

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

        vBoard.getChildren().addAll(cellsGroup, chipsGroup);
        vBase.setAlignment(vBoard, Pos.TOP_CENTER);
        vBase.setCenter(vBoard);
        addChipsToGroup();
    }

    private void addChipsToGroup() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board.getCells()[i][j] != null) {
                    chipsGroup.getChildren().add(actionCell(board.getCells()[i][j]));
                }
            }
        }
    }

    private Chip actionCell(Chip chip) {
        chip.setOnMouseReleased(event -> {
            int newX = mousePosToCell(chip.getLayoutX());
            int newY = mousePosToCell(chip.getLayoutY());

            MoveResult result;

            if (newX < 0 || newY < 0 || newX >= BOARD_SIZE || newY >= BOARD_SIZE) {
                result = new MoveResult(MoveType.NONE);
            } else {
                result = tryMove(chip, newX, newY);
            }

            int x0 = mousePosToCell(chip.getOldX());
            int y0 = mousePosToCell(chip.getOldY());

            switch (result.getType()) {
                case NONE:
                    chip.moveBack();
                    break;
                case STANDARD:
                    chip.move(newX, newY);
                    board.getCells()[x0][y0] = null;
                    board.getCells()[newX][newY] =chip;
                    break;

            }
        });

        return chip;
    }

    private MoveResult tryMove(Chip chip, int newX, int newY) {
        if (board.hasChip(newX, newY)) {
            return new MoveResult(MoveType.NONE);
        }

        int x0 = mousePosToCell(chip.getOldX());
        int y0 = mousePosToCell(chip.getOldY());


        return new MoveResult(MoveType.NONE);
    }

    private int mousePosToCell(double pos) {
        return (int)(pos + CELL_SIZE / 2) / CELL_SIZE;
    }
}
