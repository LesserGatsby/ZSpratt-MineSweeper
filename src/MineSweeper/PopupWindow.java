package MineSweeper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PopupWindow {

    public PopupWindow(View view, Controller controller, Stage  primaryStage) throws IOException {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("PopupDialoge.fxml"));

        TextField width = (TextField) root.lookup("#Width");
        TextField height = (TextField) root.lookup("#Height");
        TextField bombs = (TextField) root.lookup("#Bombs");
        Button accept = (Button) root.lookup("#Accept");

        accept.setOnAction(event -> {
            try {
                controller.width = Integer.valueOf(width.getText());
                controller.height = Integer.valueOf(height.getText());
                controller.bombCount = Integer.valueOf(bombs.getText());

                if (controller.width * controller.height < 2) {
                    controller.width *= 2;
                }

                if (controller.bombCount >= controller.width * controller.height) {
                    controller.bombCount = controller.width * controller.height - 1;
                }

                view.startButton.getOnAction().handle(null);
                dialog.close();
            } catch (Exception e) {

            }

        });

        Scene dialogScene = new Scene(root, 200, 100);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
