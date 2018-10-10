package general;

import game_mechanics.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ResourceBundle;

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
    private Group cellsGroup;
    private Group chipsGroup;

    private Board board;
    private int stepsCounter;
    private GameTimer gameTimer;
    Stage tutorialStage;
    Stage resultsStage;

    public static boolean NEW_GAME;


    public mainController() { }

    @FXML
    private void initialize() {
        vRetryButton.setFill(new ImagePattern(new Image("retry.png")));
        vTutorialButton.setFill(new ImagePattern(new Image("instruction.png")));
        vStepBackButton.setFill(new ImagePattern(new Image("step_back.png")));
        vRetryButton.setVisible(false);
        vTutorialButton.setVisible(false);
        vStepBackButton.setVisible(false);
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
        vBoard.getChildren().clear();
        stepsCounter = 0;
        setSteps2Label();
        gameTimer = new GameTimer(vTimer);
        board = new Board();
        cellsGroup = new Group();
        chipsGroup = new Group();
        addCellsAndChipsToGroup();
        vBoard.getChildren().addAll(cellsGroup, chipsGroup);

        vStepBackButton.setDisable(true);
        vStepBackButton.setOpacity(0.5);
    }


    private void generateBoardAgain() {
        gameTimer.stop();
        vTimer.setText("00:00");
        vBoard.getChildren().clear();
        generateBoard();
    }

    @FXML
    public void generateBoardAgainEvent(MouseEvent mouseEvent) {
        generateBoardAgain();
    }

    @FXML
    public void openTutorial(MouseEvent mouseEvent) throws IOException {
        tutorialStage = new Stage();
        tutorialStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("tutorial.fxml"))));
        tutorialStage.setResizable(false);
        tutorialStage.initStyle(StageStyle.UNDECORATED);
        tutorialStage.initModality(Modality.APPLICATION_MODAL);
        tutorialStage.initOwner(vBoard.getScene().getWindow());
        tutorialStage.showAndWait();
    }

    public void openResults() {
        ResultsController.summarySteps = "You coped with the task in " + stepsCounter + " steps";
        ResultsController.summaryTime = "Elapsed time - " + (gameTimer.getHours()==0?"":gameTimer.getHours() + " hour, ") + (gameTimer.getMinutes()==0?"":gameTimer.getMinutes() + " minutes and ") + gameTimer.getSeconds() + " seconds";
        resultsStage = new Stage();
        try {
            resultsStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("results.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultsStage.setResizable(false);
        resultsStage.initStyle(StageStyle.UNDECORATED);
        resultsStage.initModality(Modality.APPLICATION_MODAL);
        resultsStage.initOwner(vBoard.getScene().getWindow());
        resultsStage.showAndWait();
        if (NEW_GAME) {
            generateBoardAgain();
        } else {
            closeStage();
        }
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


    private int mousePosToCell(double pos) {
        return (int)(pos + CELL_SIZE / 2) / CELL_SIZE;
    }

    private void setSteps2Label() {
        vStepsLabel.setText(Integer.toString(stepsCounter));
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
                    if(!gameTimer.isStarted()) gameTimer.start();
                    ++stepsCounter;
                    setSteps2Label();

                    board.saveLastStep(oldX, oldY, newX, newY);
                    board.moveChipToNewXY();

                    vStepBackButton.setDisable(false);
                    vStepBackButton.setOpacity(1);


                    if(chip.getChipType() == board.getCell(oldX, oldY).getCellType() && board.getRGBbyId(chip.getChipType().getId())) {
                        board.changeCellsColor(chip.getChipType(), false);
                    }
                    if(chip.getChipType() == board.getCell(newX, newY).getCellType()) {
                        if (board.checkRowCollected(chip.getChipType())) {
                            gameTimer.stop();
                            openResults();
                        }
                    }
                    break;
            }
        });

        return chip;
    }

    public void stepBack(MouseEvent mouseEvent) {
        stepsCounter = stepsCounter + (board.isSteppedBack()?1:-1);
        setSteps2Label();

        board.stepBack();
        board.moveChipToNewXY();


    }


    private void closeStage() {
        Stage currentStage = (Stage)(vBase.getScene().getWindow());
        currentStage.close();
    }



    public void startNewGame(MouseEvent mouseEvent) {
        generateBoard();
        vRetryButton.setVisible(true);
        vTutorialButton.setVisible(true);
        vStepBackButton.setVisible(true);
    }

    public void quit(MouseEvent mouseEvent) {
        closeStage();
    }

}
