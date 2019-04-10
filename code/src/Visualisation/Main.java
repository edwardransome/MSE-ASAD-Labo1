package Visualisation;

import Computation.AStarShortestPathSolver;
import Interfaces.ShortestPathSolver;
import TerrainManagement.TerrainManager;
import Utils.Terrain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static MainWindowController controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        Parent root = loader.load();
        controller = (MainWindowController) loader.getController();
        primaryStage.setTitle("Shortest Path Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        ShortestPathSolver solver = new AStarShortestPathSolver();
        TerrainManager tm = new TerrainManager(20, 20);
        tm.setSolver(solver);
        controller.setTerrainManager(tm);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
