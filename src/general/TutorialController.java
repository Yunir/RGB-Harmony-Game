package general;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TutorialController {

    @FXML private Button vGotItButton;

    @FXML
    public void initialize() {

    }

    @FXML
    public void closeTutorial(MouseEvent mouseEvent) {
        Stage currentStage = (Stage)(vGotItButton.getScene().getWindow());
        currentStage.close();
    }
}
