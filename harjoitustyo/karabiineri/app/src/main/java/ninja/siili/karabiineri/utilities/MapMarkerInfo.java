package ninja.siili.karabiineri.utilities;


import java.util.ArrayList;


public class MapMarkerInfo {

    private String mTitle;
    private String mDesc;
    private String mDiffStart;
    private String mDiffEnd;
    private ArrayList<String> mTypes;
    private int mImage;


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDiffStart() {
        return mDiffStart;
    }

    public void setDiffStart(String mDiffStart) {
        this.mDiffStart = mDiffStart;
    }

    public String getDiffEnd() {
        return mDiffEnd;
    }

    public void setDiffEnd(String mDiffEnd) {
        this.mDiffEnd = mDiffEnd;
    }

    public ArrayList<String> getTypes() {
        return mTypes;
    }

    public void setTypes(ArrayList<String> mTypes) {
        this.mTypes = mTypes;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }


    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }
}
