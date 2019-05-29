import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

/**
 * Main entry point for the Shortest Path Solver app.
 */
public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        List<String> params = getParameters().getRaw();

        if(params.size() > 0){
            AddressStore.getInstance().setAddress(params.get(0));
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Shortest Path Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
