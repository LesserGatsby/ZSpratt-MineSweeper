package MineSweeper;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class View {
    ChoiceBox difficultyBox;
    Button startButton;
    Text clearCellsLeft;
    GridPane mineBoard;
    ImageView[] timerSegment;

    Image bomb;
    Image flag;

    Image[] timerDigit;
    Image[] timerDD;
    Image[] timerLetters;

    TimerObject timer;

    ColorAdjust red = new ColorAdjust();
    ColorAdjust green = new ColorAdjust();

    public View() {
        bomb = new Image("/Graphic/Bomb.png");
        flag = new Image("/Graphic/Flag.png");

        timerDigit = new Image[10];
        for (int i = 0; i < 10; i++) {
            timerDigit[i] = new Image("/Graphic/Timer" + i + ".png");
        }
        timerDD = new Image[2];
        for (int i = 0; i < 2; i++) {
            timerDD[i] = new Image("/Graphic/TimerDD" + i + ".png");
        }
        timerLetters = new Image[3];
        timerLetters[0] = new Image("/Graphic/TimerD.png");
        timerLetters[1] = new Image("/Graphic/TimerE.png");
        timerLetters[2] = new Image("/Graphic/TimerA.png");

        red.setContrast(0.1);
        red.setHue(0.0);
        red.setSaturation(1.0);

        green.setContrast(0.1);
        green.setHue(0.6);
        green.setSaturation(1.0);
    }
}
