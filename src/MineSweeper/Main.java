package MineSweeper;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MineSweeperUI.fxml"));
        primaryStage.setTitle("Mine Sweeper");
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();

        ChoiceBox choiceBox = (ChoiceBox) root.lookup("#DifficultyBox");
        System.out.println(choiceBox);
        ObservableList<String> options = FXCollections.observableArrayList("Easy", "Medium", "Hard");
        choiceBox.setItems(options);
        choiceBox.getSelectionModel().selectFirst();
    }


    public static void main(String[] args) {
        launch(args);
    }
}