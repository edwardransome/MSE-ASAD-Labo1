package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class MainWindowController {

    @FXML
    public GridPane mainGrid;

    @FXML
    public void initialize() {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                //TODO joli
                mainGrid.add(new TextField("0"), i, j);
            }
        }
    }

}
