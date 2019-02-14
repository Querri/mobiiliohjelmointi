package ninja.siili.karabiineri.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Point;
import com.google.ar.core.Trackable;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import ninja.siili.karabiineri.Place;
import ninja.siili.karabiineri.PlaceViewModel;
import ninja.siili.karabiineri.R;
import ninja.siili.karabiineri.RouteViewModel;
import ninja.siili.karabiineri.utilities.RenderableHelper;
import ninja.siili.karabiineri.Route;
import ninja.siili.karabiineri.utilities.RouteInfoHelper;

public class ArActivity extends AppCompatActivity {

    private static final String TAG = ArActivity.class.getSimpleName();

    private ArFragment arFragment;
    private Scene mScene;

    private RenderableHelper mRenderableHelper;
    private GestureDetector gestureDetector;

    private Route mActiveRoute;
    private boolean editMode = false;
    private TextView modeTextView;

    private View mInfoView;
    private View mToggleInfoViewFAB;
    private FloatingActionButton mChangeModeFAB;

    private String placeID;
    private RouteViewModel mRouteViewModel;

    private boolean hasFinishedLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        mInfoView = findViewById(R.id.include);
        mInfoView.setVisibility(View.GONE);
        mToggleInfoViewFAB = findViewById(R.id.fab_info_view);
        mToggleInfoViewFAB.setVisibility(View.INVISIBLE);
        mChangeModeFAB = findViewById(R.id.fab_change_mode);
        modeTextView = findViewById(R.id.tv_mode);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            placeID = b.getString("placeID");
            PlaceViewModel placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);
            placeViewModel.init(placeID);

            // change the view title to place name
            placeViewModel.getPlaceLiveData().observe(this, new Observer<Place>() {
                @Override
                public void onChanged(@Nullable final Place place) {
                    if (place != null) {
                        ArActivity.this.setTitle(place.getName());
                    }
                }
            });

            mRouteViewModel = ViewModelProviders.of(this).get(RouteViewModel.class);
            mRouteViewModel.init(placeID);
        }


        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        arFragment.getPlaneDiscoveryController().hide();
        arFragment.getPlaneDiscoveryController().setInstructionView(null);
        mScene = arFragment.getArSceneView().getScene();

        setupRouteInfoEditView();

        // Build all the models.
        CompletableFuture<ModelRenderable> clipStageGreen =
                ModelRenderable.builder().setSource(this, Uri.parse("sphere_green.sfb")).build();
        CompletableFuture<ModelRenderable> clipStageYellow =
                ModelRenderable.builder().setSource(this, Uri.parse("sphere_yellow.sfb")).build();
        CompletableFuture<ModelRenderable> clipStageOrange =
                ModelRenderable.builder().setSource(this, Uri.parse("sphere_orange.sfb")).build();
        CompletableFuture<ModelRenderable> clipStageRed =
                ModelRenderable.builder().setSource(this, Uri.parse("sphere_red.sfb")).build();
        CompletableFuture<ModelRenderable> lineStageGreen =
                ModelRenderable.builder().setSource(this, Uri.parse("cylinder_green.sfb")).build();
        CompletableFuture<ModelRenderable> lineStageYellow =
                ModelRenderable.builder().setSource(this, Uri.parse("cylinder_yellow.sfb")).build();
        CompletableFuture<ModelRenderable> lineStageOrange =
                ModelRenderable.builder().setSource(this, Uri.parse("cylinder_orange.sfb")).build();
        CompletableFuture<ModelRenderable> lineStageRed =
                ModelRenderable.builder().setSource(this, Uri.parse("cylinder_red.sfb")).build();

        CompletableFuture.allOf(
                clipStageGreen,
                clipStageYellow,
                clipStageOrange,
                clipStageRed,
                lineStageGreen,
                lineStageYellow,
                lineStageOrange,
                lineStageRed)
                .handle((notUsed, throwable) -> {

                    if (throwable != null) {
                        Toast.makeText(this, "Failed to load renderables", Toast.LENGTH_LONG).show();
                        return null;
                    }

                    try {
                        // Helper handles renderables from now on.
                        mRenderableHelper = new RenderableHelper(this, mScene,
                                clipStageGreen.get(), clipStageYellow.get(), clipStageOrange.get(), clipStageRed.get(),
                                lineStageGreen.get(), lineStageYellow.get(), lineStageOrange.get(), lineStageRed.get());
                        hasFinishedLoading = true;

                    } catch (InterruptedException | ExecutionException ex) {
                        Toast.makeText(this, "Failed to load renderables", Toast.LENGTH_LONG).show();
                    }

                    return null;
                });
        

        // Update listener for moving lines.
        arFragment.getArSceneView().getScene().setOnUpdateListener(
                frameTime -> {

                    Frame frame = arFragment.getArSceneView().getArFrame();
                    if (frame == null) {
                        return;
                    }

                    if (frame.getCamera().getTrackingState() != TrackingState.TRACKING) {
                        return;
                    }

                    if (mActiveRoute != null && editMode) {
                        // TODO do this only when there is touch event?
                        mActiveRoute.moveLinesIfNeeded();
                    }

                    arFragment.onUpdate(frameTime);
                });


        // Gesture detector.
        gestureDetector = new GestureDetector
                (this, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        onSingleTap(e);
                        return true;
                    }

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }
                });

        
        // Touch listener
        arFragment.getArSceneView().getScene().setOnTouchListener(
                (HitTestResult hitTestResult, MotionEvent event) -> {
                    return gestureDetector.onTouchEvent(event);
                }
        );
    }


    /**
     * User has tapped the screen.
     * @param tap MotionEvent for the tap.
     */
    private void onSingleTap(MotionEvent tap) {
        Frame frame = arFragment.getArSceneView().getArFrame();
        if (frame != null) {
            if (mActiveRoute == null && editMode) {
                if (tryPlaceNewRoute(tap, frame)) {
                    editingRoute(editMode);
                } else {
                    Toast.makeText(this, "nope", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (editMode) {
                    if (!tryPlaceClip(tap, frame)) {
                        Toast.makeText(this, "nope", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    selectRoute(null);
                }
            }
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO disable fullscreen
    }


    /**
     * Try to place a new Route.
     * @param tap MotionEvent fot the tap.
     * @param frame Current frame.
     */
    private boolean tryPlaceNewRoute(MotionEvent tap, Frame frame) {
        if (tap != null && frame.getCamera().getTrackingState() == TrackingState.TRACKING) {
            for (HitResult hit : frame.hitTest(tap)) {
                Trackable trackable = hit.getTrackable();
                if (trackable instanceof Point) {
                    Route newRoute = new Route(placeID);
                    newRoute.init(this, arFragment.getTransformationSystem(), mRenderableHelper);
                    newRoute.addClip(hit);
                    mRouteViewModel.insertRoute(newRoute);
                    selectRoute(newRoute);
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Try to place a new clip to active Route.
     * @param tap MotionEvent fot the tap.
     * @param frame Current frame.
     */
    private boolean tryPlaceClip(MotionEvent tap, Frame frame) {
        if (tap != null && frame.getCamera().getTrackingState() == TrackingState.TRACKING) {
            for (HitResult hit : frame.hitTest(tap)) {
                Trackable trackable = hit.getTrackable();
                if (trackable instanceof Point) {
                    mActiveRoute.addClip(hit);
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Select a new active route.
     * @param route Selected route.
     */
    private void selectRoute(Route route) {
        mActiveRoute = route;
        updateModeText();
    }


    /**
     * Switch mode to adding route or exit it.
     * @param startAdd True if starting adding, false if calcelling or done.
     */
    private void addingRoute(boolean startAdd) {
        editMode = startAdd;
        updateModeText();
    }


    /**
     * Switch mode to editing route or exit it.
     * @param startEdit True if starting edit, false if cancelling or done.
     */
    private void editingRoute(boolean startEdit) {
        mActiveRoute.enableClipTransforming(startEdit);
        editMode = startEdit;
        updateModeText();

        // Show FAB for info view only when a route is in editing mode.
        if (startEdit) {
            mToggleInfoViewFAB.setVisibility(View.VISIBLE);
            setRouteInfoEditViewFields();
        } else {
            mToggleInfoViewFAB.setVisibility(View.INVISIBLE);
        }
    }


    /** Update the text informing what mode is on. */
    private void updateModeText() {
        if (mActiveRoute == null) {
            if (editMode) {
                modeTextView.setText(this.getString(R.string.add_route));
                mChangeModeFAB.setImageResource(R.drawable.ic_add);  // FIXME clear
            } else {
                modeTextView.setText("");
                mChangeModeFAB.setImageResource(R.drawable.ic_add);  // FIXME add
            }
        } else {
            if (editMode) {
                modeTextView.setText(this.getString(R.string.edit_route));
                mChangeModeFAB.setImageResource(R.drawable.ic_save);
            } else {
                modeTextView.setText(this.getString(R.string.route_active));
                mChangeModeFAB.setImageResource(R.drawable.ic_settings);  // FIXME edit
            }
        }
    }


    /**
     * FAB button for changing mode.
     * @param button 2. FAB button
     */
    public void onClickChangeMode(View button) {
        if (mActiveRoute == null) {
            addingRoute(!editMode);
        } else {
            editingRoute(!editMode);
        }
    }


    /**
     * FAB button for toggling Info View's visibility.
     * @param button 1. FAB button
     */
    public void onClickToggleInfoView(View button) {
        if (mActiveRoute != null) {
            if (mInfoView.getVisibility() == View.GONE) {
                mInfoView.setVisibility(View.VISIBLE);
            } else {
                mInfoView.setVisibility(View.GONE);
            }
        }
    }


    /** Setup RouteInfoEditView's listeners */
    private void setupRouteInfoEditView() {
        SeekBar diffSeekBar = mInfoView.findViewById(R.id.diff_seekbar);
        TextView diffTextView = mInfoView.findViewById(R.id.diff_number);
        Button saveButton = mInfoView.findViewById(R.id.bt_save);

        EditText nameEditText = mInfoView.findViewById(R.id.name);
        RadioButton boulderRadioButton = mInfoView.findViewById(R.id.boulder);
        RadioButton sportRadioButton = mInfoView.findViewById(R.id.sport);
        RadioButton tradRadioButton = mInfoView.findViewById(R.id.trad);
        CheckBox sitstartCheckBox = mInfoView.findViewById(R.id.sitstart);
        CheckBox topoutCheckBox = mInfoView.findViewById(R.id.topout);
        EditText notesEditText = mInfoView.findViewById(R.id.notes);

        // listener for difficulty seekbar to change difficulty textview
        diffSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                diffTextView.setText(RouteInfoHelper.getDiffString(progress));
                diffTextView.setTextColor(RouteInfoHelper.getDiffColor(ArActivity.this, progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        saveButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {

                String type = "";
                if (boulderRadioButton.isChecked()) type = "boulder";
                else if (sportRadioButton.isChecked()) type = "sport";
                else if (tradRadioButton.isChecked()) type = "trad";

                mRouteViewModel.updateRoute(mActiveRoute.mID,
                        nameEditText.getText().toString(),
                        diffSeekBar.getProgress(),
                        type,
                        sitstartCheckBox.isChecked(),
                        topoutCheckBox.isChecked(),
                        notesEditText.getText().toString()
                );
            }
        });
    }


    /** Set the fields in the RouteInfoEditView to match the current properties of the active route */
    private void setRouteInfoEditViewFields() {
        EditText nameEditText = mInfoView.findViewById(R.id.name);
        SeekBar diffSeekBar = mInfoView.findViewById(R.id.diff_seekbar);
        TextView diffTextView = mInfoView.findViewById(R.id.diff_number);
        RadioButton boulderRadioButton = mInfoView.findViewById(R.id.boulder);
        RadioButton sportRadioButton = mInfoView.findViewById(R.id.sport);
        RadioButton tradRadioButton = mInfoView.findViewById(R.id.trad);
        CheckBox sitstartCheckBox = mInfoView.findViewById(R.id.sitstart);
        CheckBox topoutCheckBox = mInfoView.findViewById(R.id.topout);
        EditText notesEditText = mInfoView.findViewById(R.id.notes);

        if (mActiveRoute != null) { 
            mRouteViewModel.init(placeID, mActiveRoute.mID);
        } else {
            Toast.makeText(this, "activeroute null", Toast.LENGTH_SHORT).show();
        }

        if (mRouteViewModel != null) {
            if (mRouteViewModel.getRouteLiveData() != null) {
                mRouteViewModel.getRouteLiveData().observe(this, new Observer<Route>() {
                    @Override
                    public void onChanged(@Nullable Route route) {
                        if (route != null) {
                            nameEditText.setText(route.mName);
                            diffSeekBar.setProgress(route.mDiff);
                            diffTextView.setText(RouteInfoHelper.getDiffString(route.mDiff));
                            sitstartCheckBox.setChecked(route.mIsSitStart);
                            topoutCheckBox.setChecked(route.mIsTopOut);
                            notesEditText.setText(route.mNotes);

                            String type = route.mType;
                            switch(type) {
                                case "boulder": boulderRadioButton.setChecked(true);
                                                break;
                                case "sport":   sportRadioButton.setChecked(true);
                                                break;
                                case "trad":    tradRadioButton.setChecked(true);
                                                break;
                            }
                        }
                    }
                });
            } else {
                Toast.makeText(this, "null routeLiveData", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "null routeViewModel", Toast.LENGTH_SHORT).show();
        }
    }
}

