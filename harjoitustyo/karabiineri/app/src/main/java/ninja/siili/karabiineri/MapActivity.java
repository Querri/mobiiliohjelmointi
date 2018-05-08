package ninja.siili.karabiineri;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ninja.siili.karabiineri.utilities.ParserUtils;
import ninja.siili.karabiineri.utilities.PermissionUtils;

public class MapActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


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

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(TEKIILA));

        // Set infowindowclicklistener and enable location
        mMap.setOnInfoWindowClickListener(this);
        enableMyLocation();
    }


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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // TODO Permission was not granted, display error dialog.
            mPermissionDenied = false;
        }
    }


    @Override
    public void onInfoWindowClick(Marker marker) {

        Intent intent = new Intent(MapActivity.this, PlaceActivity.class);
        intent.putExtra("id", (String) marker.getTag());
        startActivity(intent);
    }
}
