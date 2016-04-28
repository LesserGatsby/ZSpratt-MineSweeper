//  Author: Zachary Spratt
package MineSweeper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {
    View view = new View();
    Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //loads up the fxml and creates scene
        Parent root = FXMLLoader.load(getClass().getResource("MineSweeperUI.fxml"));
        primaryStage.setTitle("Mine Sweeper");
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();

        //Hooks up Exposed UI to view
        view.difficultyBox = (ChoiceBox) root.lookup("#DifficultyBox");
        view.clearCellsLeft = (Text)  root.lookup("#ClearCellsLeft");
        view.mineBoard = (GridPane)  root.lookup("#MineBoard");
        view.startButton = (Button)  root.lookup("#StartButton");

        view.timerSegment = new ImageView[5];
        for (int i = 0; i < 5; i++) {
            view.timerSegment[i] = (ImageView)  root.lookup("#TimerSeg" + i);
            view.timerSegment[i].setEffect(view.red);
        }
        //sets start segment for doubleDot
        view.timerSegment[2].setImage(view.timerDD[0]);

        //Sets options in difficult selector
        ObservableList<String> options = FXCollections.observableArrayList("Easy", "Medium", "Hard", "Custom", "Last Custom Game");
        view.difficultyBox.setItems(options);
        view.difficultyBox.getSelectionModel().selectFirst();

        //Creates and sets off timer
        view.timer = new TimerObject(view);
        view.timer.start();

        //Sets up action for start button, done here for convenience
        view.startButton.setOnAction(event -> {
            view.mineBoard.getChildren().clear();
            view.timer.running = false;
            view.timer = new TimerObject(view);
            view.timer.start();
            controller.RunController(view);
        });

        controller = new Controller(view);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                view.timer.interrupt();

                Platform.exit();
                System.exit(0);
            }
        });

        //Custom Size Option
        //CustomOption

        view.difficultyBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (view.difficultyBox.getValue().equals("Custom")) {
                    try {
                        controller.isCustom = true;
                        PopupWindow pop = new PopupWindow(view, controller, primaryStage);
                        view.difficultyBox.getSelectionModel().selectLast();
                    } catch (IOException e) {

                    }
                } else {
                    controller.isCustom = false;
                }
            }

        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
