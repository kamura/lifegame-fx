package lifegame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class LifegameController implements Initializable {
    
    private Lifegame lifegame;
    
    @FXML
    private void handleStartButtonAction(ActionEvent event) {
        lifegame.start();
    }
    
    @FXML
    private void handleStopButtonAction(ActionEvent event) {
        lifegame.stop();
    }

    @FXML
    private void handleResetButtonAction(ActionEvent event) {
        lifegame.reset();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    void setLifegame(Lifegame lifegame) {
        this.lifegame = lifegame;
    }
}