import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Controller for the main window. Contains all controller logic and displays
 * information using JavaFX.
 */
public class MainWindowController {
    private final int SIZE = 20;
    private Position start = new Position(0,0);
    private Position end = new Position(SIZE-1,SIZE-1);
    //private TerrainManager terrainManager;

    @FXML
    public GridPane mainGrid;

    @FXML
    public ChoiceBox<String> chooseAlg;

    @FXML
    public void initialize() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                TextField tf = new TextField("0");
                final int x = i;
                final int y = j;
                tf.textProperty().addListener((observable, oldValue, newValue) -> {
                    Position p = new Position(x, y);
                    try{
                        double weight = Double.parseDouble(newValue);
                        Registry registry = LocateRegistry.getRegistry(AddressStore.getInstance().address());
                        AuthManager stub = (AuthManager) registry.lookup("AuthManager");
                        stub.setWeight(TokenStore.getInstance().token(), p, weight);

                    } catch (NumberFormatException e){
                        tf.textProperty().setValue(oldValue);
                    } catch (Exception e) {
                        System.err.println("Client exception: " + e.toString());
                        e.printStackTrace();
                        tf.textProperty().setValue(oldValue);
                        communicationErrorDialog();
                    }

                });
                mainGrid.add(tf, i, j);
            }
        }
        try{
            Registry registry = LocateRegistry.getRegistry(AddressStore.getInstance().address());
            AuthManager stub = (AuthManager) registry.lookup("AuthManager");
            List<String> algs = stub.getAlgorithms(TokenStore.getInstance().token());
            ObservableList<String> obsAlgs = FXCollections.observableList(algs);
            chooseAlg.setItems(obsAlgs);
            chooseAlg.getSelectionModel().selectFirst();

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            communicationErrorDialog();
        }


        refreshView();
    }

    @FXML
    public void selectStart(){
        Dialog<Pair<Integer, Integer>> dialog = getCoordDialog("Start");

        Optional<Pair<Integer, Integer>> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            Position p = new Position(pair.getKey(), pair.getValue());
            try {
                Registry registry = LocateRegistry.getRegistry(AddressStore.getInstance().address());
                AuthManager stub = (AuthManager) registry.lookup("AuthManager");
                stub.setStart(TokenStore.getInstance().token(), p);
                start = p;

            } catch (Exception e) {
                e.printStackTrace();
                communicationErrorDialog();
            }

            refreshView();
        });
    }

    /**
     * Creates a dialog box for two coords.
     * https://stackoverflow.com/questions/31556373/javafx-dialog-with-2-input-fields
     * @return A Dialog box ready to be shown and waited on
     */
    private Dialog<Pair<Integer, Integer>> getCoordDialog(String name) {
        List<Integer> range = IntStream.rangeClosed(0, SIZE - 1)
                .boxed().collect(Collectors.toList());
        Dialog<Pair<Integer, Integer>> dialog = new Dialog<>();
        dialog.setTitle("Choose " + name);
        dialog.setHeaderText("" + name + " choice");
        dialog.setContentText("Choose " + name + " coords:");

        // Set the button types.
        ButtonType confirmButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButton, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField x = new TextField();
        x.setPromptText("0");
        TextField y = new TextField();
        y.setPromptText("0");

        gridPane.add(new Label("X:"), 0, 0);
        gridPane.add(x, 1, 0);
        gridPane.add(new Label("Y:"), 2, 0);
        gridPane.add(y, 3, 0);

        dialog.getDialogPane().setContent(gridPane);

        Platform.runLater(() -> x.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButton) {
                return new Pair<>(Integer.parseInt(x.getText()), Integer.parseInt(y.getText()));
            }
            return null;
        });
        return dialog;
    }

    @FXML
    public void selectEnd(){
        Dialog<Pair<Integer, Integer>> dialog = getCoordDialog("End");

        Optional<Pair<Integer, Integer>> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            Position p = new Position(pair.getKey(), pair.getValue());
            try {
                Registry registry = LocateRegistry.getRegistry(AddressStore.getInstance().address());
                AuthManager stub = (AuthManager) registry.lookup("AuthManager");
                stub.setEnd(TokenStore.getInstance().token(), p);
                end = p;

            } catch (Exception e) {
                e.printStackTrace();
                communicationErrorDialog();
            }

            refreshView();
        });
    }

    @FXML
    public void calculateShortestPath(){
        clearAll();
        try {
            Registry registry = LocateRegistry.getRegistry(AddressStore.getInstance().address());
            AuthManager stub = (AuthManager) registry.lookup("AuthManager");
            Path result = stub.getShortestPath(TokenStore.getInstance().token(), chooseAlg.getSelectionModel().getSelectedItem());
            if(result == null){
                System.out.println("Null path received. Is the computation server down?");
            }
            Iterator<Position> it = result.iterator();
            it.forEachRemaining(pos -> {
                getMainGridNodeAt(new Position(pos.getKey(), pos.getValue())).setStyle("-fx-background-color: yellow;");
            });
            getMainGridNodeAt(start).setStyle("-fx-background-color: green;");
            getMainGridNodeAt(end).setStyle("-fx-background-color: red;");

        } catch (Exception e) {
            e.printStackTrace();
            communicationErrorDialog();
        }
    }

    public void clearAll() {
        for(Node n: mainGrid.getChildren()) {
            n.setStyle("-fx-background-color: white;");
        }
    }

    private Node getMainGridNodeAt(Position p){
        for (Node n: mainGrid.getChildren()){
            if (mainGrid.getRowIndex(n).equals(p.getValue()) && mainGrid.getColumnIndex(n).equals(p.getKey())) {
                return n;
            }
        }
        return null;
    }


    @FXML
    private void refreshView(){
        for(Node n: mainGrid.getChildren()) {
            if (mainGrid.getRowIndex(n) == start.getValue() && mainGrid.getColumnIndex(n) == start.getKey()) {
                n.setStyle("-fx-background-color: green;");
            } else if (mainGrid.getRowIndex(n) == end.getValue() && mainGrid.getColumnIndex(n) == end.getKey()) {
                n.setStyle("-fx-background-color: red;");
            } else {
                n.setStyle("-fx-background-color: white;");
            }
        }
    }

    private void communicationErrorDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("RMI connection error.");
        alert.setContentText("Unable to connect. Please check the server status and try again.");
        alert.showAndWait();
    }


}
