package ninja.siili.karabiineri;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ninja.siili.karabiineri.utilities.Converters;
import ninja.siili.karabiineri.utilities.RouteInfoHelper;

/** Stores information about the route. */
public class RouteInfo {

    private Context mContext;
    private String mID;

    private String mName;
    private int mDiff;
    private int mDiffColor;
    private boolean mIsBoulder;
    private boolean mIsSport;
    private boolean mIsTrad;
    private int mStartHoldCount;
    private boolean mIsSitstart;
    private boolean mIsTopOut;
    private String mNotes;


    /**
     * Constructor for RouteInfo, sets default values.
     * @param context Context of the app
     */
    public RouteInfo(Context context, String id) {
        mContext = context;

        // default values
        mID = "50";
        mName = "";
        mIsBoulder = true;
        mIsSport = false;
        mIsTrad = false;
        mStartHoldCount = 1;
        mIsSitstart = false;
        mIsTopOut = false;
        mNotes = "";
        setDifficulty(10);
    }


    private String getName() {
        if (mName.equals("")) return "Nameless Route";
        else return mName;
    }



    /** Set and get difficulty color based on difficulty */
    private void setDifficulty(int diff) {
        mDiff = diff;
        if (mDiff < 9) {
            mDiffColor = mContext.getColor(R.color.green);
        } else if (mDiff < 18) {
            mDiffColor = mContext.getColor(R.color.yellow);
        } else if (mDiff < 27) {
            mDiffColor = mContext.getColor(R.color.orange);
        } else {
            mDiffColor = mContext.getColor(R.color.red);
        }
    }


    public int getDifficultyColor() {
        return mDiffColor;
    }


    /**
     * Get Route type as a String.
     * @return String value of Route type.
     */
    private String getTypeString() {
        if (mIsBoulder) {
            return mContext.getString(R.string.boulder);
        } else if (mIsSport) {
            return mContext.getString(R.string.sport);
        } else if (mIsTrad) {
            return mContext.getString(R.string.trad);
        } else return "";
    }


    /**
     * Check that values given to the route are fine.
     * @return true if all is fine
     */
    public boolean checkValuesAreAllRight() {
        if (!mIsBoulder && !mIsSport && !mIsTrad) {
            Toast.makeText(mContext, "Choose route type.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mStartHoldCount > 2) {
            Toast.makeText(mContext, "Too many start holds.", Toast.LENGTH_SHORT).show();
        } else if (mName.equals("")) {
            mName = "Nameless Route";
        }
        return true;
    }


    /**
     * Update all values in RouteInfo.
     * @param infoView baabaa
     * @return True if succeeded.
     */
    public boolean updateAll(View infoView) {
        ArrayList<View> views = findAllViews(infoView);

        if (views != null && views.size() == 8) {
            mName = ((TextView) views.get(0)).getText().toString();
            mIsBoulder = ((RadioButton) views.get(2)).isChecked();
            mIsSport = ((RadioButton) views.get(3)).isChecked();
            mIsTrad = ((RadioButton) views.get(4)).isChecked();
            mIsSitstart = ((CheckBox) views.get(5)).isChecked();
            mIsTopOut = ((CheckBox) views.get(6)).isChecked();
            mStartHoldCount = 1;
            mNotes = ((TextView) views.get(7)).getText().toString();

            setDifficulty(((SeekBar) views.get(1)).getProgress());
            return true;
        }
        Toast.makeText(mContext, "Failed to update RouteInfo.", Toast.LENGTH_SHORT).show();
        return false;
    }


    /**
     * Setup listener for difficulty seekbar in info view.
     * @param infoView View of the info view.
     */
    public void setupInfoView(View infoView) {
        SeekBar diffSeekBar = infoView.findViewById(R.id.diff_seekbar);
        TextView diffTextView = infoView.findViewById(R.id.diff_number);

        // listener for difficulty seekbar to change difficulty textview
        diffSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setDifficulty(progress);
                diffTextView.setText(RouteInfoHelper.getDiffString(mDiff));
                diffTextView.setTextColor(getDifficultyColor());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }


    /**
     * Update the info view with RouteInfo's values.
     * @param infoView the infoview.
     */
    public void updateInfoView(View infoView) {
        ArrayList<View> views = findAllViews(infoView);

        if (views != null && views.size() == 8) {
            ((TextView) views.get(0)).setText(mName);
            //readJson(infoView);
            ((SeekBar) views.get(1)).setProgress(mDiff);
            ((RadioButton) views.get(2)).setChecked(mIsBoulder);
            ((RadioButton) views.get(3)).setChecked(mIsSport);
            ((RadioButton) views.get(4)).setChecked(mIsTrad);
            ((CheckBox) views.get(5)).setChecked(mIsSitstart);
            ((CheckBox) views.get(6)).setChecked(mIsTopOut);
            ((TextView) views.get(7)).setText(mNotes);
        }
        Toast.makeText(mContext, "Failed to update info view.", Toast.LENGTH_SHORT).show();
    }


    /**
     * Update the info card with RouteInfo's values.
     * @param infoCardView the info card.
     */
    public void updateInfoCardView(View infoCardView) {
        if (infoCardView != null) {
            TextView nameTV = infoCardView.findViewById(R.id.name);
            TextView diffTV = infoCardView.findViewById(R.id.diff_number);
            TextView typeTV = infoCardView.findViewById(R.id.type);
            TextView sitstartIC = infoCardView.findViewById(R.id.sitstart);
            TextView holdcountIC = infoCardView.findViewById(R.id.start_hold_count);
            TextView topoutIC = infoCardView.findViewById(R.id.topout);
            TextView notesTV = infoCardView.findViewById(R.id.notes);

            if (nameTV == null || diffTV == null || typeTV == null || sitstartIC == null
                    || holdcountIC == null || topoutIC == null || notesTV == null)  {
                Toast.makeText(mContext, "Null field in info card.", Toast.LENGTH_SHORT).show();
                return;
            }

            nameTV.setText(getName());
            diffTV.setText(RouteInfoHelper.getDiffString(mDiff));
            diffTV.setTextColor(mDiffColor);
            typeTV.setText(getTypeString());

            if (mIsSitstart) sitstartIC.setVisibility(View.VISIBLE);
            else sitstartIC.setVisibility(View.INVISIBLE);

            if (mStartHoldCount == 2) holdcountIC.setVisibility(View.VISIBLE);
            if (mStartHoldCount == 1) holdcountIC.setVisibility(View.VISIBLE);
            else holdcountIC.setVisibility(View.INVISIBLE);

            if (mIsTopOut) topoutIC.setVisibility(View.VISIBLE);
            else topoutIC.setVisibility(View.INVISIBLE);

            notesTV.setText(mNotes);
        } else {
            Toast.makeText(mContext, "Null info card.", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Find all Views from the info view.
     * @param infoView the info view.
     * @return ListArray of the views.
     */
    private ArrayList<View> findAllViews(View infoView) {
        ArrayList<View> views = new ArrayList<>();

        // order is meaningful
        views.add(infoView.findViewById(R.id.name));
        views.add(infoView.findViewById(R.id.diff_seekbar));
        views.add(infoView.findViewById(R.id.boulder));
        views.add(infoView.findViewById(R.id.sport));
        views.add(infoView.findViewById(R.id.trad));
        views.add(infoView.findViewById(R.id.sitstart));
        views.add(infoView.findViewById(R.id.topout));
        views.add(infoView.findViewById(R.id.notes));

        for (View view : views) {
            if (view == null) {
                return null;
            }
        }
        return views;
    }
}
