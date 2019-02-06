package ninja.siili.karabiineri.utilities;

import android.content.Context;

import ninja.siili.karabiineri.R;

/** A Helper class to aid with various things about Route information. */
public class RouteInfoHelper {


    /** Transform Route difficulty from integer to the conventional difficulty rating */
    public void getDiffString(int diff) {
        // Where did I put this function? it's gotta be somewhere...
    }


    /** Get the color associated with the difficulty rating */
    public int getDiffColor(Context context, int diff) {
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
