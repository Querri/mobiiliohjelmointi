package ninja.siili.karabiineri.utilities;

import android.content.Context;

import ninja.siili.karabiineri.R;

/** A Helper class to aid with various things about Route information. */
public class RouteInfoHelper {


    /** Transform Route difficulty from integer to the conventional difficulty rating */
    public static String getDiffString(int value) {
        String number;
        String letter = "";

        // TODO more grades
        if (value < 9) {
            number = "4";
        } else if (value < 18) {
            number = "5";
        } else if (value < 27) {
            number = "6";
        } else {
            number = "7";
        }

        switch(value) {
            case 0: case 9: case 18: case 27:
                letter = "A-";
                break;
            case 1: case 10: case 19: case 28:
                letter = "A ";
                break;
            case 2: case 11: case 20: case 29:
                letter = "A+";
                break;
            case 3: case 12: case 21: case 30:
                letter = "B-";
                break;
            case 4: case 13: case 22: case 31:
                letter = "B ";
                break;
            case 5: case 14: case 23: case 32:
                letter = "B+";
                break;
            case 6: case 15: case 24: case 33:
                letter = "C-";
                break;
            case 7: case 16: case 25: case 34:
                letter = "C ";
                break;
            case 8: case 17: case 26: case 35:
                letter = "C+";
                break;
        }
        return number + letter;
    }


    /** Get the color associated with the difficulty rating */
    public static int getDiffColor(Context context, int diff) {
        if (diff < 9) {
            return context.getColor(R.color.green);
        } else if (diff < 18) {
            return context.getColor(R.color.yellow);
        } else if (diff < 27) {
            return context.getColor(R.color.orange);
        } else {
            return context.getColor(R.color.red);
        }
    }
}
