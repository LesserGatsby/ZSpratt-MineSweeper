package MineSweeper;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class View {
    ChoiceBox difficultyBox;
    Button startButton;
    Text clearCellsLeft;
    GridPane mineBoard;
    Text playStatus;

    Image bomb;
    Image flag;

    public View() {
        bomb = new Image("/Bomb.png");
        flag = new Image("/Flag.png");
    }
}
