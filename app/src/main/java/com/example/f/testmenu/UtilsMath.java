package com.example.f.testmenu;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by f on 28/01/2018.
 */

public class UtilsMath {


    UtilsMath(){}

    public int getXFromPosition(int p, int numCol){

        return p % numCol;

    }
    public int getYFromPosition(int p, int numCol){

        return (p/numCol);

    }

    public String getTimeFromInt(int t){

        int secs = (int) (t / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        int milliseconds = (int) (t % 1000);

        return (String.format("%02d", mins) + ":"
                + String.format("%02d", secs) + ":"
                + String.format("%03d", milliseconds));


    }

    public void sortArray(){

        System.out.println("------sortArray------");

        int[] array = new int[10];
        Random rand = new Random();
        for (int i = 0; i < array.length; i++)
            array[i] = rand.nextInt(100) + 1;
        Arrays.sort(array);
        System.out.println(Arrays.toString(array));
        // in reverse order
        for (int i = array.length - 1; i >= 0; i--)
            System.out.print(array[i] + " ");
        System.out.println();

        System.out.println("///------sortArray------////");

    }


}
