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

        addCellsAndChipsToGroup();
        vBoard.getChildren().addAll(cellsGroup, chipsGroup);
        vBoard.setMaxSize(500, 500);
        vBase.setCenter(vBoard);

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
                    board.getCell(oldX,oldY).setChip(null);
                    board.getCell(newX,newY).setChip(chip);
                    if(chip.getChipType() == board.getCell(oldX, oldY).getCellType() && board.getRGBbyId(chip.getChipType().getId())) {
                        board.changeCellsColor(chip.getChipType(), false);
                    }
                    if(chip.getChipType() == board.getCell(newX, newY).getCellType()) board.checkRowCollected(chip.getChipType());
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

    private void addCellsAndChipsToGroup() {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                cellsGroup.getChildren().add(board.getCell(x,y));
                if (board.hasChip(x,y)) {
                    chipsGroup.getChildren().add(attachActionToChip(board.getCell(x,y).getChip()));
                }
            }
        }
    }
}
