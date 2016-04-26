package MineSweeper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Controller {
    private View view;
    private MineField field;
    private int width = 20;
    private int height = 20;
    private int bombCount = 20;

    public Controller(View v) {
        //References to exposed UI elements
        view = v;

        //Sets up width, height, and bombs in minefield
        getWIdthAndBombCount();
        field = new MineField(view, this, width, height, bombCount);

        field.buttonField = new Button[width][height];

        //Affixes buttons to gridPane, and adds their respective click actions
        for (int i = 0; i < width; i++) {
            for (int n = 0; n < height; n++) {
                Button b = new Button(" ");
                double size = 30.0;
                b.setPrefSize(size, size);
                b.setMinSize(0.0, 0.0);
                b.setTextAlignment(TextAlignment.CENTER);
                b.setStyle("-fx-background-color: -fx-outer-border, -fx-inner-border, -fx-body-color; \n" +
                        "    -fx-background-insets: 0, 1, 2;\n" +
                        "    -fx-background-radius: 5, 4, 3;");
                view.mineBoard.add(b, i + 1, n + 1);
                int x = i;
                int y = n;

                b.setOnMouseClicked(event -> {
                    //Any Click Generates Field if not generated
                    if (!field.generated) {
                        field.generate(width, height, bombCount, x, y);
                        field.generated = true;
                    }

                    //LeftClick  Digs up spot
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        if (!field.isMarked(x, y)) {
                            field.expose(x, y);
                        }
                    }
                    //RightClick  Plants Flag
                    if (event.getButton().equals(MouseButton.SECONDARY)) {
                        field.mark(x, y);
                    }
                });

                //adds button to buttonField
                field.buttonField[i][n] = b;
            }
        }

        //Sets play status
        view.playStatus.setText("Playing");
    }

    private void getWIdthAndBombCount() {
        //DifficultySettings
        if(view.difficultyBox.getValue().equals("Easy")) {
            width = 8;
            height = 8;
            bombCount = 10;
        } else if(view.difficultyBox.getValue().equals("Medium")) {
            width = 16;
            height = 16;
            bombCount = 40;
        } else if(view.difficultyBox.getValue().equals("Hard")) {
            width = 30;
            height = 16;
            bombCount = 99;
        }
    }

    public void disableAllButtons() {
        for (int i = 0; i < width; i++) {
            for (int n = 0; n < height; n++) {
                field.buttonField[i][n].setText(field.mineField[i][n]);

                if (!field.mineField[i][n].equals(field.bomb)) {
                    field.buttonField[i][n].setDisable(true);
                } else {
                    field.buttonField[i][n].setOnAction(event -> {

                    });
                }
            }
        }
    }
}
