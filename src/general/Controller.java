package general;

import game_mechanics.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Controller {

    public static final int CELL_SIZE = 100;
    public static final int BOARD_SIZE = 5; // 5 CELL_SIZES

    @FXML private BorderPane vBase;
    @FXML private Label vIntro;
    @FXML private Pane vBoard;
    @FXML private Circle vRetryButton;
    @FXML private Circle vInstructionButton;
    @FXML private Label vStepsLabel;
    @FXML private Label vTimer;
    private ImageView vWinPic;
    private Group cellsGroup;
    private Group chipsGroup;

    private Board board;

    private int stepsCounter;
    private GameTimer gameTimer;

    public Controller() { }

    @FXML
    private void initialize() {
        stepsCounter = 0;
        gameTimer = new GameTimer(vTimer);
        vWinPic = new ImageView("win.png");
        vWinPic.setVisible(false);
        vWinPic.setFitWidth(350);
        vWinPic.setFitHeight(170);
        vWinPic.setLayoutX(75);
        vWinPic.setLayoutY(160);
        vRetryButton.setFill(new ImagePattern(new Image("retry.png")));
        vInstructionButton.setFill(new ImagePattern(new Image("instruction.png")));

        generateBoard();
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
                    if(!gameTimer.isStarted())gameTimer.start();
                    ++stepsCounter;
                    vStepsLabel.setText(Integer.toString(stepsCounter));
                    chip.move(newX, newY);
                    board.getCell(oldX,oldY).setChip(null);
                    board.getCell(newX,newY).setChip(chip);
                    if(chip.getChipType() == board.getCell(oldX, oldY).getCellType() && board.getRGBbyId(chip.getChipType().getId())) {
                        board.changeCellsColor(chip.getChipType(), false);
                        //vWinPic.setVisible(false);
                    }
                    if(chip.getChipType() == board.getCell(newX, newY).getCellType()) {
                        if (board.checkRowCollected(chip.getChipType())) {
                            vWinPic.setVisible(true);
                            gameTimer.stop();
                        }
                    }
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

    private void generateBoard() {
        board = new Board();

        cellsGroup = new Group();
        chipsGroup = new Group();
        addCellsAndChipsToGroup();
        vBoard.getChildren().addAll(cellsGroup, chipsGroup, vWinPic);

    }

    public void generateBoardAgain(MouseEvent mouseEvent) {
        vBoard.getChildren().clear();
        generateBoard();

    }
}
