package com.noman.donateit.data;

/**
 * Created by noman on 4/26/17.
 */

public class UtilityFuncs {

    public static int switchCatStr(String category) {
        if (category.equals("Books")) {
            return 0;
        }
        else if (category.equals("Food")) {
            return 1;
        }
        else if (category.equals("Electronics")) {
            return 2;
        }
        else if(category.equals("Cars")) {
            return 3;
        }
        else if(category.equals("Blood")) {
            return 4;
        }
        else if (category.equals("Clothes")) {
            return 5;
        }
        else if (category.equals("Stationary")) {
            return 6;
        }
        else {
            return 7;
        }
    }

    public static String switchCatInt(int category) {

        if (category == 0) {
            return "Books";
        }
        else if (category == 1) {
            return "Food";
        }
        else if (category == 2) {
            return "Electronics";
        }
        else if (category == 3) {
            return "Cars";
        }
        else if (category == 4) {
            return "Blood";
        }
        else if (category == 5) {
            return "Clothes";
        }
        else if (category == 6) {
            return "Stationary";
        }
        else {
            return "Shoes";
        }
    }

}
