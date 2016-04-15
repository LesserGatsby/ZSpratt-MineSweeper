package MineSweeper;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    View view = new View();
    Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MineSweeperUI.fxml"));
        primaryStage.setTitle("Mine Sweeper");
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();

        view.difficultyBox = (ChoiceBox) root.lookup("#DifficultyBox");
        view.clearCellsLeft = (Text)  root.lookup("#ClearCellsLeft");
        view.mineBoard = (GridPane)  root.lookup("#MineBoard");
        view.startButton = (Button)  root.lookup("#StartButton");
        view.playStatus = (Text)  root.lookup("#PlayStatus");

        ObservableList<String> options = FXCollections.observableArrayList("Easy", "Medium", "Hard");
        view.difficultyBox.setItems(options);
        view.difficultyBox.getSelectionModel().selectFirst();

        view.startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.mineBoard.getChildren().clear();
                controller = new Controller(view);
            }
        });

        controller = new Controller(view);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
