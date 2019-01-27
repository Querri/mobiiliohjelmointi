package ninja.siili.karabiineri.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ninja.siili.karabiineri.Place;
import ninja.siili.karabiineri.PlaceViewModel;
import ninja.siili.karabiineri.R;
import ninja.siili.karabiineri.RouteInfo;

public class PlaceActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private PlaceViewModel mPlaceViewModel;

    private View.OnClickListener arButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            new RouteInfo(PlaceActivity.this, "0");
            Intent intent = new Intent(PlaceActivity.this, ArActivity.class);
            startActivity(intent);
        }
    };

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_place);

        Button arButton = findViewById(R.id.bt_ar);
        arButton.setOnClickListener(arButtonOnClickListener);

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
                            case R.id.nav_add_place:
                                Toast.makeText(PlaceActivity.this, "add", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_settings:
                                Toast.makeText(PlaceActivity.this, "settings", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_help:
                                Toast.makeText(PlaceActivity.this, "help", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_feedback:
                                Toast.makeText(PlaceActivity.this, "feedback", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });


        TextView nameTextView = findViewById(R.id.tv_name);
        TextView routeTypeTextView = findViewById(R.id.tv_route_types);
        TextView routeDiffStartTextView = findViewById(R.id.tv_route_diff_start);
        TextView routeDiffEndTextView = findViewById(R.id.tv_route_diff_end);
        TextView descTextView = findViewById(R.id.tv_desc);
        TextView accessTextView = findViewById(R.id.tv_access);

        // get bundle from intent
        Bundle b = getIntent().getExtras();
        if (b != null) {
            int id = b.getInt("id");

            // Get the PlaceViewModel
            mPlaceViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);
            mPlaceViewModel.init(id);

            // Observe PlaceLiveData and get it's info to populate fields.
            mPlaceViewModel.getPlaceLiveData().observe(this, new Observer<Place>() {
                @Override
                public void onChanged(@Nullable final Place place) {
                    if (place != null) {
                        nameTextView.setText(place.getName());
                        descTextView.setText(place.getDesc());
                        accessTextView.setText(place.getAccess());
                    } else {
                        Toast.makeText(PlaceActivity.this, "null Place" + Integer.toString(id), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "no id in tag", Toast.LENGTH_SHORT).show();
        }
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
}
