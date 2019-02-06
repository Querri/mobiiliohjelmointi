package ninja.siili.karabiineri;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.view.View;

import com.google.ar.core.HitResult;
import com.google.ar.sceneform.ux.TransformationSystem;

import java.util.ArrayList;

import ninja.siili.karabiineri.utilities.RenderableHelper;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/** Route is an entity that consists of multiple Clips and a RouteInfo. */
@Entity(tableName = "route_table",
        foreignKeys = @ForeignKey(entity = Place.class,
                parentColumns = "mID",
                childColumns = "mPlaceID",
                onDelete = CASCADE))
public class Route {

    @PrimaryKey(autoGenerate = true)
    public int mID;

    public int mPlaceID;

    public String mName;

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

    @Ignore
    private RouteInfo mRouteInfo;


    public Route(int placeID, String name) {
        mPlaceID = placeID;
        mName = name;
    }


    /**
     * Initiation
     * @param context App's context.
     * @param transformationSystem TransformationSystem for TransformableNodes.
     * @param renderableHelper RenderableHelper to provide correct Renderables.
     */
    public void init(Context context, TransformationSystem transformationSystem,
                     RenderableHelper renderableHelper) {
        mContext = context;
        mTransformationSystem = transformationSystem;
        mRenderableHelper = renderableHelper;
        mRouteInfo = new RouteInfo(context, "0");  // FIXME it's static
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
                mRouteInfo.getDifficultyColor(), previousClip));
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
            clip.changeColor(mRouteInfo.getDifficultyColor());
        }
    }


    /**
     * Pass infoView to RouteInfo for updating RouteInfo's values.
     * @param infoView View.
     */
    public void updateRouteInfo(View infoView) {
        if (mRouteInfo.updateAll(infoView)) {
            changeRouteColor();
            updateInfoCard();
        }
    }


    /**
     * Pass infoView to RouteInfo for setup.
     * @param infoView View of the infoView.
     */
    public void setupInfoView(View infoView) {
        mRouteInfo.setupInfoView(infoView);
    }


    /**
     * Pass infoView to RouteInfo for updating..
     * @param infoView View of the infoView.
     */
    public void updateInfoView(View infoView) {
        mRouteInfo.updateInfoView(infoView);
    }


    /**
     * Update the info card.
     */
    public void updateInfoCard() {
        mRouteInfo.updateInfoCardView(mClips.get(0).getInfoCardView());
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
