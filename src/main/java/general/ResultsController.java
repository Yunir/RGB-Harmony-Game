package general;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ResultsController {
    @FXML private Label vSummarySteps;
    @FXML private Label vSummaryTime;
    @FXML private Button vNewGameButton;
    @FXML private Button vQuitButton;
    @FXML private ImageView vCongratsImage;


    public static String summarySteps;
    public static String summaryTime;

    @FXML
    public void initialize() {
        initializeCongratsPic();
        vSummarySteps.setText(summarySteps);
        vSummaryTime.setText(summaryTime);
    }

    public void startNewGame(MouseEvent mouseEvent) {
        mainController.NEW_GAME = true;
        closeStage();
    }

    public void quit(MouseEvent mouseEvent) {
        mainController.NEW_GAME = false;
        closeStage();
    }

    private void initializeCongratsPic() {
        vCongratsImage.setImage(new Image("/img/win.png"));
        vCongratsImage.setFitWidth(350);
        vCongratsImage.setFitHeight(170);
        vCongratsImage.setLayoutX(75);
        vCongratsImage.setLayoutY(160);
    }


    private void closeStage() {
        Stage currentStage = (Stage)(vQuitButton.getScene().getWindow());
        currentStage.close();
    }

}
