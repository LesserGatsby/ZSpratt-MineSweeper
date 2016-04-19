package MineSweeper;

import com.sun.javafx.geom.Vec2d;

import java.util.ArrayList;
import java.util.Collections;

public class MineField {
    boolean generated = false;
    public int freeSpacesRemaining = 0;
    public String[][] mineField;
    public String bomb = "B";
    public String space = " ";
    private int width;
    private int height;

    public MineField(int width, int height, int bombCount) {
        //initializes bomb field
        this.width = width;
        this.height = height;
        freeSpacesRemaining = (width * height) - bombCount;
    }

    public void generate(int width, int height, int bombCount, int x, int y) {
        mineField = new String[width][height];

        //stores bomb positions in vectors in arraylist
        //Array list is used to select bomb positions in an efficiant way
        ArrayList<Vec2d> fieldArray = new ArrayList<Vec2d>();

        for (int i = 0; i < width; i++) {
            for (int n = 0; n < height; n++) {
                mineField[i][n] = space;
                fieldArray.add(new Vec2d(i, n));
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


        //if a field has a bomb near bye, its text is set to the count, otherwise left as " "
        for (int i = 0; i < width; i++) {
            for (int n = 0; n < height; n++) {
                if (!mineField[i][n].equals(bomb)) {
                    int c = bombCount(i, n);

                    if (c != 0) mineField[i][n] = String.valueOf(c);
                }
            }
        }
    }

    public int bombCount(int x, int y) {

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
}
