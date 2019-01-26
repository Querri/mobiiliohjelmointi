package ninja.siili.karabiineri.activities;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import ninja.siili.karabiineri.Place;
import ninja.siili.karabiineri.R;
import ninja.siili.karabiineri.PlaceViewModel;
import ninja.siili.karabiineri.utilities.PermissionUtils;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    private GoogleMap mMap;

    private DrawerLayout mDrawerLayout;

    private PlaceViewModel mPlaceViewModel;
    public static final int NEW_PLACE_ACTIVITY_REQUEST_CODE = 1;


    /**
     * Creates the action and sets things up.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Set menu button in action bar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_drawer_menu);

        // Find drawer and set it's items clickable
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        int itemId = menuItem.getItemId();

                        // TODO actual navigation
                        switch (itemId) {
                            case R.id.nav_nearest_place:
                                Toast.makeText(MainActivity.this, "nearest", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_add_place:
                                Toast.makeText(MainActivity.this, "add", Toast.LENGTH_SHORT).show();
                                addPlacePicker();
                                break;
                            case R.id.nav_settings:
                                Toast.makeText(MainActivity.this, "settings", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_help:
                                Toast.makeText(MainActivity.this, "help", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_feedback:
                                Toast.makeText(MainActivity.this, "feedback", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Get a new or existing ViewModel from the ViewModelProvider.
        mPlaceViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);
    }


    /**
     * Open and close drawer menu with the menu button in action bar.
     * @param item  The selected item, which is the menu button.
     * @return      Boolean, probably signals success.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /** Update the map markers **/
    private void updatePlaceMarkers() {
        mPlaceViewModel.getAllPlaces().observe(this, new Observer<List<Place>>() {
            @Override
            public void onChanged(@Nullable List<Place> places) {
                if (places != null) {
                    for (Place place : places) {
                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(place.getLocation())
                                .title(place.getName())
                                .snippet(place.getDesc()));
                        marker.setTag("view");
                    }
                }
            }
        });
    }


    /**
     * Called when the map is ready to set up everything in it.
     * @param googleMap The map in question.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(this);

        enableMyLocation();
        updatePlaceMarkers();
    }


    /** Checks permissions and enables user's location. */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted.
            mMap.setMyLocationEnabled(true);

            LocationManager locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);

            // Get current location and move/zoom the camera to it.
            if (locationManager != null) {
                Location location = locationManager.getLastKnownLocation(
                        locationManager.getBestProvider(new Criteria(), false));

                if (location != null) {
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))
                            .zoom(11)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        }
    }


    /** LocationPermission was denied and something happens here. Must investigate further. */
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // TODO Permission was not granted, display error dialog.
            mPermissionDenied = false;
        }
    }


    /** Map marker's infoWindow was clicked, start the placeActivity */
    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker.getTag() != null) {
            if (marker.getTag().equals("edit")) {
                LatLng markerPosition = marker.getPosition();

                Intent intent = new Intent(MainActivity.this, PlaceEditActivity.class);
                intent.putExtra("id", 0);
                intent.putExtra("location", new double[] {markerPosition.latitude, markerPosition.longitude});
                marker.remove();
                startActivityForResult(intent, NEW_PLACE_ACTIVITY_REQUEST_CODE);
            } else if (marker.getTag().equals("view")) {
                Intent intent = new Intent(MainActivity.this, PlaceActivity.class);
                intent.putExtra("id", 0);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "marker tag null", Toast.LENGTH_SHORT).show();
        }
    }


    /** Returning from PlaceEditActivity **/
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_PLACE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            double[] location = data.getDoubleArrayExtra("location");
            if (location != null && location.length == 2) {
                Place place = new Place(
                        data.getStringExtra("name"),
                        data.getStringExtra("desc"),
                        data.getStringExtra("access"),
                        new LatLng(location[0], location[1]));
                mPlaceViewModel.insert(place);
                updatePlaceMarkers();
                return;
            }
        }
        Toast.makeText(
                getApplicationContext(),
                "place not saved",
                Toast.LENGTH_LONG).show();
    }


    /** Add a marker to mark a new Place **/
    public void addPlacePicker() {
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(mMap.getCameraPosition().target)
                .title(getString(R.string.marker_text))
                .draggable(true));
        marker.setTag("edit");
    }
}
