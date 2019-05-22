import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point for the Shortest Path Solver app.
 */
public class Main extends Application {

    static MainWindowController controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        //controller = (LoginController) loader.getController();
        primaryStage.setTitle("Shortest Path Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        //ShortestPathSolver solver = new AStarShortestPathSolver();
        //TerrainManager tm = new TerrainManager(20, 20);
        //tm.setSolver(solver);
        //controller.setTerrainManager(tm);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
