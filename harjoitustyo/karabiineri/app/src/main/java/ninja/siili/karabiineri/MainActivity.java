package ninja.siili.karabiineri;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ninja.siili.karabiineri.utilities.ParserUtils;
import ninja.siili.karabiineri.utilities.PermissionUtils;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    private GoogleMap mMap;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mContainer;


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
                                addPicker();
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


    /**
     * Called when the map is ready to set up everything in it.
     * @param googleMap The map in question.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // loop through all place IDs and place markers
        for (int i = 0; i < ParserUtils.getPlacesCount(this); i++) {

            LatLng location = ParserUtils.getPlaceFieldLatLng(this, Integer.toString(i));
            if (location != null) {

                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(ParserUtils.getPlaceFieldText(this, Integer.toString(i), "title"))
                        .snippet(ParserUtils.getPlaceFieldText(this, Integer.toString(i), "desc")));
                marker.setTag(ParserUtils.getPlaceFieldText(this, Integer.toString(i), "id"));
            } else {
                Toast.makeText(this, "null location", Toast.LENGTH_SHORT).show();
                // TODO actual exception
            }
        }

        //Set infowindowclicklistener
        mMap.setOnInfoWindowClickListener(this);

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        // Enable location and move/zoom camera
        enableMyLocation();

        // Get last known location and focus the camera
        // FIXME
        // TODO check permission or redo with LocationListener
        /*Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(11)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/
    }


    /**
     * Checks permissions and enables user's location.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }


    /**
     * Locationpermission was denied and something happens here. Must investigate further.
     */
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // TODO Permission was not granted, display error dialog.
            mPermissionDenied = false;
        }
    }


    /**
     * Mapmarker's infowindow was clicked and the correct placeActivity must be started.
     * @param marker
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(MainActivity.this, PlaceActivity.class);
        intent.putExtra("id", (String) marker.getTag());
        startActivity(intent);
    }


    /**
     * Adds a drop pin (kinda) to select a location for a new place
     * and a button (kinda) to confirm selection.
     */
    public void addPicker() {

        ImageView dropPinView = new ImageView(this);
        dropPinView.setImageResource(R.drawable.ic_add);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        dropPinView.getAdjustViewBounds();

        float density = getResources().getDisplayMetrics().density;
        params.bottomMargin = (int) (12 * density);

        dropPinView.setLayoutParams(params);
        mDrawerLayout.addView(dropPinView);



        Button addPlaceButton = new Button(this);
        addPlaceButton.setText(R.string.add_place);

        //float buttonDensity = getResources().getDisplayMetrics().density;
        //buttonParams.bottomMargin = (int) (12 * buttonDensity);

        addPlaceButton.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM));
        mDrawerLayout.addView(addPlaceButton);


        addPlaceButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {

                LatLng pinLocation = mMap.getCameraPosition().target;
                double lat = pinLocation.latitude;
                double lng = pinLocation.longitude;

                Intent intent = new Intent(MainActivity.this, PlaceEditActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                startActivity(intent);
            }
        });
    }
}
