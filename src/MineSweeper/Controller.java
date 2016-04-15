package MineSweeper;

import javafx.scene.control.Button;

public class Controller {
    View view;
    MineField field;

    public Controller(View v) {
        view = v;
        field = new MineField(5, 5, 10);

        for (int i = 0; i < 5; i++) {
            for (int n = 0; n < 5; n++) {
                view.mineBoard.add(new Button(field.mineField[i][n]), i + 1, n + 1);
            }
        }
    }
}
