package Visualisation;

import Computation.AStarShortestPathSolver;
import Interfaces.ShortestPathSolver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("Shortest Path Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        ShortestPathSolver solver = new AStarShortestPathSolver();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
