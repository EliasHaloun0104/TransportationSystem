package stages;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Stage_1_Controller implements Initializable, EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent keyEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML private void switcher(){
        StageManager.getInstance().showStage_2();
    }
}
