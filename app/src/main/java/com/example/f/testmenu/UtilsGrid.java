package com.example.f.testmenu;

/**
 * Created by f on 28/01/2018.
 */

public class UtilsGrid {

    UtilsGrid(){}

    public int getLastCell(int numCols){

        return (numCols * numCols - 1);

    }

    public int getCellWidth(int numCols){

        switch (numCols) {
            case 3:
                return 120;
            case 4:
                return 100;
            case 5:
                return 72;
            default:
                return 72;
        }

    }

}
