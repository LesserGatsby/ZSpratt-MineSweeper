package MineSweeper;

import com.sun.javafx.geom.Vec2d;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Collections;

public class MineField {
    boolean generated = false;
    private int freeSpacesRemaining = 0;
    private Controller controller;
    public String[][] mineField;
    private String[][] markField;
    public Button[][] buttonField;

    public String bomb = "B";
    public String space = " ";
    public String flag = "F";

    private int width;
    private int height;

    private View view;

    public MineField(View v, Controller c, int width, int height, int bombCount) {
        //initializes bomb field
        this.width = width;
        this.height = height;
        freeSpacesRemaining = (width * height) - bombCount;

        this.view = v;
        this.controller = c;
    }

    public void generate(int width, int height, int bombCount, int x, int y) {
        mineField = new String[width][height];
        markField = new String[width][height];

        //stores bomb positions in vectors in arraylist
        //Array list is used to select bomb positions in an efficiant way
        ArrayList<Vec2d> fieldArray = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            for (int n = 0; n < height; n++) {
                mineField[i][n] = space;
                fieldArray.add(new Vec2d(i, n));
                markField[i][n] = space;
            }
        }

        //removes fitst click from bomb options
        fieldArray.remove(new Vec2d(x, y));

        Collections.shuffle(fieldArray);

        //a number of locations are selected and bombs are planted
        for (int b = 0; b < bombCount; b++) {
            Vec2d pos = fieldArray.get(b);
            mineField[(int) pos.x][(int) pos.y] = bomb;
        }

        //if a field has a bomb nearby, its text is set to the count, otherwise left as " "
        for (int i = 0; i < width; i++) {
            for (int n = 0; n < height; n++) {
                if (!mineField[i][n].equals(bomb)) {
                    int c = bombCount(i, n);

                    if (c != 0) mineField[i][n] = String.valueOf(c);
                }
            }
        }

        view.clearCellsLeft.setText(String.valueOf(freeSpacesRemaining));
    }

    private int bombCount(int x, int y) {

        //checks all 8 adjacent locations for a bomb, and returns final count
        int count = 0;
        if (x - 1 >= 0)         if (mineField[x - 1][y].equals(bomb)) count += 1;
        if (x + 1 < width)      if (mineField[x + 1][y].equals(bomb)) count += 1;
        if (y - 1 >= 0)         if (mineField[x][y - 1].equals(bomb)) count += 1;
        if (y + 1 < height)     if (mineField[x][y + 1].equals(bomb)) count += 1;
        if (x - 1 >= 0 && y - 1 >= 0)            if (mineField[x - 1][y - 1].equals(bomb)) count += 1;
        if (x - 1 >= 0 && y + 1 < height)        if (mineField[x - 1][y + 1].equals(bomb)) count += 1;
        if (x + 1 < width && y - 1 >= 0)         if (mineField[x + 1][y - 1].equals(bomb)) count += 1;
        if (x + 1 < width && y + 1 < height)     if (mineField[x + 1][y + 1].equals(bomb)) count += 1;
        return count;
    }

    public int expose(int x, int y) {
        if (mineField[x][y].equals(bomb)) {
            //Clicked on a mine, ends game and displays all tiles
            controller.disableAllButtons();
            view.playStatus.setText("Loss");
            return -1;
        } else if (mineField[x][y].equals(space)) {
            //Clicked on space, recursively clicks surrounding buttons
            clickedNotBomb(x, y);
            if (x - 1 >= 0      && !(buttonField[x - 1][y].disabledProperty().getValue()))      expose(x - 1, y);
            if (x + 1 < width   && !(buttonField[x + 1][y].disabledProperty().getValue()))      expose(x + 1, y);
            if (y - 1 >= 0      && !(buttonField[x][y - 1].disabledProperty().getValue()))      expose(x, y - 1);
            if (y + 1 < height  && !(buttonField[x][y + 1].disabledProperty().getValue()))      expose(x, y + 1);
            return 0;
        } else {
            //Clicked a number, standerd click.
            clickedNotBomb(x, y);
            buttonField[x][y].setText(String.valueOf(bombCount(x, y)));
        }

        if (freeSpacesRemaining == 0) {
            view.playStatus.setText("Victory");
        }

        return bombCount(x, y);
    }

    private void clickedNotBomb(int x, int y) {
        //Standard button click
        buttonField[x][y].setDisable(true);
        freeSpacesRemaining -= 1;
        view.clearCellsLeft.setText(String.valueOf(freeSpacesRemaining));
    }

    public boolean mark(int x, int y) {
        if (!isMarked(x, y)) {
            markField[x][y] = flag;
            controller.setButtonGraphic(buttonField[x][y], view.flag);
        } else {
            markField[x][y] = space;
            buttonField[x][y].setGraphic(null);
        }

        return isMarked(x, y);
    }

    public boolean isMarked(int x, int y) {
        return markField[x][y].equals(flag);
    }

    public int unexposedCount() {
        return freeSpacesRemaining;
    }
}
