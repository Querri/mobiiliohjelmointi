package ninja.siili.karabiineri;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.ar.core.HitResult;
import com.google.ar.sceneform.ux.TransformationSystem;

import java.util.ArrayList;
import java.util.UUID;

import ninja.siili.karabiineri.utilities.RenderableHelper;
import ninja.siili.karabiineri.utilities.RouteInfoHelper;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/** Route is an entity that consists of multiple Clips and a RouteInfo. */
@Entity(tableName = "route_table",
        foreignKeys = @ForeignKey(entity = Place.class,
                parentColumns = "mID",
                childColumns = "mPlaceID",
                onDelete = CASCADE))
public class Route {

    @NonNull
    @PrimaryKey
    public String mID;

    // FIXME
    // mPlaceID column references a foreign key but it is not part of an index.
    // This may trigger full table scans whenever parent table is modified so
    // you are highly advised to create an index that covers this column.
    public String mPlaceID;

    public String mName;
    public int mDiff;
    public String mType;
    public int mStartHoldCount;
    public boolean mIsSitStart;
    public boolean mIsTopOut;
    public String mNotes;

    @Ignore
    private Context mContext;

    @Ignore
    private TransformationSystem mTransformationSystem;

    @Ignore
    private RenderableHelper mRenderableHelper;

    @Ignore
    private ArrayList<Clip> mClips = new ArrayList<>();

    @Ignore
    private int mSelectedClipPosition = 0;

    public Route() {}

    @Ignore
    public Route(String placeID, String name, int diff, String type, int startHoldCount,
                 boolean isSitStart, boolean isTopOut, String notes) {

        mID = UUID.randomUUID().toString();
        mPlaceID = placeID;
        mName = name;
        mDiff = diff;
        mType = type;
        mStartHoldCount = startHoldCount;
        mIsSitStart = isSitStart;
        mIsTopOut = isTopOut;
        mNotes = notes;
    }


    /**
     * Initiation of the things that don't get stored to database.
     * @param context App's context.
     * @param transformationSystem TransformationSystem for TransformableNodes.
     * @param renderableHelper RenderableHelper to provide correct Renderables.
     */
    public void init(Context context, TransformationSystem transformationSystem,
                     RenderableHelper renderableHelper) {
        mContext = context;
        mTransformationSystem = transformationSystem;
        mRenderableHelper = renderableHelper;
    }


    /**
     * Add new Clip to the route.
     * @param hit HitResult for the spot the user tapped.
     */
    public void addClip(HitResult hit) {

        // If no previous clip, pass null.
        Clip previousClip = null;
        if (mClips.size() > 0) {
            previousClip = mClips.get(mClips.size()-1);
        }

        mClips.add(new Clip(mTransformationSystem, mRenderableHelper, hit,
                RouteInfoHelper.getDiffColor(mContext, mDiff), previousClip));
    }


    /**
     * Find selected Clip and move the lines adjacent to it.
     * Recursion <3
     */
    public void moveLinesIfNeeded() {
        if (mClips.get(mSelectedClipPosition).isClipSelected()) {
            // Selected Clip hasn't changed, move lines adjacent to it if the Clip is transforming.
            if (mClips.get(mSelectedClipPosition).isClipTransforming()) {

                // Move the line below Clip.
                if (mSelectedClipPosition != 0) {
                    mClips.get(mSelectedClipPosition).moveLine(mClips.get(mSelectedClipPosition - 1));
                }
                // Move the line above Clip.
                if (mSelectedClipPosition != mClips.size()-1) {
                    mClips.get(mSelectedClipPosition + 1).moveLine(mClips.get(mSelectedClipPosition));
                }
            }
        } else {
            // Selected Clip has changed, find the new one and do recursion.
            for (int i = 0; i < mClips.size(); i++) {
                if (mClips.get(i).isClipSelected()) {
                    mSelectedClipPosition = i;
                    moveLinesIfNeeded();
                    break;
                }
            }
        }
    }


    /**
     * Change every clip's color.
     */
    public void changeRouteColor() {
        for (Clip clip : mClips) {
            clip.changeColor(RouteInfoHelper.getDiffColor(mContext, mDiff));
        }
    }



    /**
     * Go through all clips and enable/disable transforming. Used when toggling editmode.
     * @param enable True if enable transforming, false if disable.
     */
    public void enableClipTransforming(boolean enable) {
        for (Clip clip : mClips) {
            clip.enableTransforming(enable);
        }
    }
}
