import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LoginController {
    @FXML
    TextField loginField;

    @FXML
    PasswordField passwordField;

    @FXML
    public void initialize(){
        Platform.runLater(()->loginField.requestFocus());
    }

    @FXML
    public void tryLogin(){
        String host = "localhost";
        if(loginField.getText().isEmpty() || passwordField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty fields");
            alert.setContentText("Please enter a username and password before attempting login");
            alert.showAndWait();
            return;
        }
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            AuthManager stub = (AuthManager) registry.lookup("AuthManager");
            String token = stub.login(loginField.getText(), passwordField.getText());
            if (token != null){
                //OK! set token and change scene
                TokenStore.getInstance().setToken(token);
                Parent pane = FXMLLoader.load(
                        getClass().getResource("mainWindow.fxml"));
                Stage primaryStage = (Stage) loginField.getScene().getWindow();
                primaryStage.getScene().setRoot(pane);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid credentials");
                alert.setContentText("Please try again using different credentials.");
                alert.showAndWait();

            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Connection error");
            alert.setContentText("A connection error occurred. Is the server online?");

            alert.showAndWait();
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
        return;

    }

}
