package MineSweeper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class Controller {
    View view;
    MineField field;
    Button[][] buttonField;
    int width = 10;
    int height = 10;
    int bombCount = 10;

    public Controller(View v) {
        view = v;
        field = new MineField(width, height, bombCount);
        buttonField = new Button[width][height];
        view.clearCellsLeft.setText(String.valueOf(field.freeSpacesRemaining));

        view.playStatus.setText("Playing");
        for (int i = 0; i < width; i++) {
            for (int n = 0; n < height; n++) {
                Button b = new Button(" ");
                double size = 30.0;
                b.setMinSize(size, size);
                b.setMaxSize(size, size);
                b.setStyle("-fx-background-color: -fx-outer-border, -fx-inner-border, -fx-body-color; \n" +
                        "    -fx-background-insets: 0, 1, 2;\n" +
                        "    -fx-background-radius: 5, 4, 3;");
                view.mineBoard.add(b, i + 1, n + 1);
                int x = i;
                int y = n;

                b.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        cliokOn(x, y);
                    }
                });

                buttonField[i][n] = b;
            }
        }
    }

    public void cliokOn(int x, int y) {
        if (field.mineField[x][y].equals(field.bomb)) {
            disableAllButtons();
            view.playStatus.setText("Loss");
        } else if (field.mineField[x][y].equals(field.space)) {
            buttonField[x][y].setDisable(true);
            field.freeSpacesRemaining -= 1;
            view.clearCellsLeft.setText(String.valueOf(field.freeSpacesRemaining));
            if (x - 1 >= 0      && !(buttonField[x - 1][y].disabledProperty().getValue()))      cliokOn(x - 1, y);
            if (x + 1 < width   && !(buttonField[x + 1][y].disabledProperty().getValue()))      cliokOn(x + 1, y);
            if (y - 1 >= 0      && !(buttonField[x][y - 1].disabledProperty().getValue()))      cliokOn(x, y - 1);
            if (y + 1 < height  && !(buttonField[x][y + 1].disabledProperty().getValue()))      cliokOn(x, y + 1);
        } else {
            buttonField[x][y].setDisable(true);
            field.freeSpacesRemaining -= 1;
            view.clearCellsLeft.setText(String.valueOf(field.freeSpacesRemaining));
            buttonField[x][y].setText(String.valueOf(field.bombCount(x, y)));
        }

        view.startButton.setDisable(false);
        if (field.freeSpacesRemaining == 0) {
            view.playStatus.setText("Victory");
        }
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
