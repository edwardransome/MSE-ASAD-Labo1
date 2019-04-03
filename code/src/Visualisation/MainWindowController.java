package Visualisation;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class MainWindowController {

    @FXML
    public GridPane mainGrid;

    @FXML
    public void initialize() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                TextField tf = new TextField("0");
                mainGrid.add(tf, i, j);
            }
        }
    }

}
