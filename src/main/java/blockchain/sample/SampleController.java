package blockchain.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class SampleController {

    @FXML
    private Button DO;

    @FXML
    private Button AA;

    @FXML
    public void createDO(ActionEvent actionEvent) throws IOException {
        Main.showCreateNodeView();
    }

    @FXML
    public void createAA(ActionEvent actionEvent) throws IOException {
        Main.showCreateAttributeview();
    }
}
