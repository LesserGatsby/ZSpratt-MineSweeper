package MineSweeper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Controller {
    View view;
    MineField field;
    Button[][] buttonField;
    int width = 20;
    int height = 20;
    int bombCount = 20;

    public Controller(View v) {
        //References to exposed UI elements
        view = v;

        //Sets up width, height, and bombs in minefield
        getWIdthAndBombCount();
        field = new MineField(width, height, bombCount);
        buttonField = new Button[width][height];
        view.clearCellsLeft.setText(String.valueOf(field.freeSpacesRemaining));

        //Sets play status
        view.playStatus.setText("Playing");

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

                b.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        //LeftClick  Digs up spot
                        if (event.getButton().equals(MouseButton.PRIMARY)) {
                            if (!buttonField[x][y].getText().equals("F")) {
                                if (!field.generated) {
                                    field.generate(width, height, bombCount, x, y);
                                    field.generated = true;
                                }
                                cliokOn(x, y);
                            }
                        }
                        //RightClick  Plants Flag
                        if (event.getButton().equals(MouseButton.SECONDARY)) {
                            if (!buttonField[x][y].getText().equals("F")) {
                                buttonField[x][y].setText("F");
                            } else {
                                buttonField[x][y].setText(" ");
                            }
                        }
                    }
                });

                //adds button to buttonField
                buttonField[i][n] = b;
            }
        }
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

    public void cliokOn(int x, int y) {
        if (field.mineField[x][y].equals(field.bomb)) {
            //Clicked on a mine, ends game and displays all tiles
            disableAllButtons();
            view.playStatus.setText("Loss");
        } else if (field.mineField[x][y].equals(field.space)) {
            //Clicked on space, recursively clicks surrounding buttons
            clickedNotBomb(x, y);
            buttonField[x][y].setText(" ");
            if (x - 1 >= 0      && !(buttonField[x - 1][y].disabledProperty().getValue()))      cliokOn(x - 1, y);
            if (x + 1 < width   && !(buttonField[x + 1][y].disabledProperty().getValue()))      cliokOn(x + 1, y);
            if (y - 1 >= 0      && !(buttonField[x][y - 1].disabledProperty().getValue()))      cliokOn(x, y - 1);
            if (y + 1 < height  && !(buttonField[x][y + 1].disabledProperty().getValue()))      cliokOn(x, y + 1);
        } else {
            //Clicked a number, standerd click.
            clickedNotBomb(x, y);
            buttonField[x][y].setText(String.valueOf(field.bombCount(x, y)));
        }

        view.startButton.setDisable(false);
        if (field.freeSpacesRemaining == 0) {
            view.playStatus.setText("Victory");
        }
    }

    public void clickedNotBomb(int x, int y) {
        //Standard button click
        buttonField[x][y].setDisable(true);
        field.freeSpacesRemaining -= 1;
        view.clearCellsLeft.setText(String.valueOf(field.freeSpacesRemaining));
    }

    public void disableAllButtons() {
        for (int i = 0; i < width; i++) {
            for (int n = 0; n < height; n++) {
                buttonField[i][n].setText(field.mineField[i][n]);

                if (!field.mineField[i][n].equals(field.bomb)) {
                    buttonField[i][n].setDisable(true);
                } else {
                    buttonField[i][n].setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {

                        }
                    });
                }
            }
        }
    }
}
