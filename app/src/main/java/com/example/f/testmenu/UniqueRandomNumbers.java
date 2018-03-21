package com.example.f.testmenu; /**
 * Created by f on 27/01/2018.
 */

import java.util.ArrayList;
import java.util.Collections;

public class UniqueRandomNumbers {

    UniqueRandomNumbers(){}

    public int[] getRandomNumbers(int howMany, int from, int to) {

        int[] intArray = new int[howMany+1];

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=from; i<to; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i=1; i<=howMany; i++) {
            intArray[i] = list.get(i);
        }

        return intArray;

    }



}
