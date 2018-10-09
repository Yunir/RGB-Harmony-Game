package general;

import game_mechanics.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class Controller {

    public static final int CELL_SIZE = 100;
    public static final int BOARD_SIZE = 5; // 5 CELL_SIZES

    @FXML private BorderPane vBase;
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
                Cell cell = new Cell(x, y, x == 1 || x == 3);
                cellsGroup.getChildren().add(cell);
            }
        }

        vBoard.getChildren().addAll(cellsGroup, chipsGroup);
        vBoard.setMaxSize(500, 500);
        vBase.setCenter(vBoard);

        addChipsToGroup();
    }

    private void addChipsToGroup() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board.getCells()[i][j] != null) {
                    chipsGroup.getChildren().add(attachActionToChip(board.getCells()[i][j]));
                }
            }
        }
    }

    private Chip attachActionToChip(Chip chip) {
        chip.setOnMouseReleased(event -> {

            MoveResult result;

            int newX = mousePosToCell(chip.getLayoutX());
            int newY = mousePosToCell(chip.getLayoutY());
            if (newX < 0 || newY < 0 || newX >= BOARD_SIZE || newY >= BOARD_SIZE) {
                result = new MoveResult(MoveType.UNABLE);
            } else {
                result = tryMove(chip, newX, newY);
            }

            int oldX = mousePosToCell(chip.getPastMouseX());
            int oldY = mousePosToCell(chip.getPastMouseY());

            switch (result.getType()) {
                case UNABLE:
                    chip.moveBack();
                    break;
                case ABLE:
                    chip.move(newX, newY);
                    board.getCells()[oldX][oldY] = null;
                    board.getCells()[newX][newY] =chip;
                    break;
            }
        });

        return chip;
    }

    private MoveResult tryMove(Chip chip, int newX, int newY) {
        if (board.hasChip(newX, newY)) {
            return new MoveResult(MoveType.UNABLE);
        }

        int oldX = mousePosToCell(chip.getPastMouseX());
        int oldY = mousePosToCell(chip.getPastMouseY());

        boolean moveByX = (Math.abs(newX - oldX) == 1);
        boolean moveByY = (Math.abs(newY - oldY) == 1);
        if ((moveByX && !moveByY) || (!moveByX && moveByY)) {
            return new MoveResult(MoveType.ABLE);
        }
        return new MoveResult(MoveType.UNABLE);
    }

    private int mousePosToCell(double pos) {
        return (int)(pos + CELL_SIZE / 2) / CELL_SIZE;
    }
}
