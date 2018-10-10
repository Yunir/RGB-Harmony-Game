package general;

import game_mechanics.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class mainController {

    public static final int CELL_SIZE = 100;
    public static final int BOARD_SIZE = 5; // 5 CELL_SIZES

    @FXML private BorderPane vBase;
    @FXML private Label vIntro;
    @FXML private Pane vBoard;
    @FXML private Circle vRetryButton;
    @FXML private Circle vTutorialButton;
    @FXML private Circle vStepBackButton;
    @FXML private Label vStepsLabel;
    @FXML private Label vTimer;
    private ImageView vWinPic;
    private Group cellsGroup;
    private Group chipsGroup;

    private Board board;
    private int stepsCounter;
    private GameTimer gameTimer;
    Stage tutorialStage;

    public mainController() { }

    @FXML
    private void initialize() {
        initializeCongratsPic(vWinPic);
        gameTimer = new GameTimer(vTimer);

        vRetryButton.setFill(new ImagePattern(new Image("retry.png")));
        vTutorialButton.setFill(new ImagePattern(new Image("instruction.png")));
        vStepBackButton.setFill(new ImagePattern(new Image("step_back.png")));
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

    private void generateBoard() {
        stepsCounter = 0;
        vStepsLabel.setText("0");
        board = new Board();
        cellsGroup = new Group();
        chipsGroup = new Group();
        addCellsAndChipsToGroup();
        vBoard.getChildren().addAll(cellsGroup, chipsGroup, vWinPic);
    }

    @FXML
    public void generateBoardAgain(MouseEvent mouseEvent) {
        vBoard.getChildren().clear();
        vWinPic.setVisible(false);
        generateBoard();
    }

    @FXML
    public void openTutorial(MouseEvent mouseEvent) throws IOException {
        tutorialStage = new Stage();
        tutorialStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("tutorial.fxml"))));
        tutorialStage.setResizable(false);
        tutorialStage.setTitle("My modal window");
        tutorialStage.initModality(Modality.APPLICATION_MODAL);
        tutorialStage.initOwner(vBoard.getScene().getWindow());
        tutorialStage.showAndWait();
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

    private void initializeCongratsPic(ImageView vPic) {
        vWinPic = new ImageView("win.png");
        vWinPic.setVisible(false);
        vWinPic.setFitWidth(350);
        vWinPic.setFitHeight(170);
        vWinPic.setLayoutX(75);
        vWinPic.setLayoutY(160);
    }

    private int mousePosToCell(double pos) {
        return (int)(pos + CELL_SIZE / 2) / CELL_SIZE;
    }
}
