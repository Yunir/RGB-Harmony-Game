package general;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TutorialController {

    @FXML private Button vGotItButton;

    @FXML
    public void initialize() {

    }

    @FXML
    public void checkKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.ESCAPE) closeStage();
    }

    @FXML
    public void closeTutorial(MouseEvent mouseEvent) {
        closeStage();
    }

    private void closeStage() {
        Stage currentStage = (Stage)(vGotItButton.getScene().getWindow());
        currentStage.close();
    }
}
