package ninja.siili.karabiineri;

import android.content.Intent;
import android.net.Uri;
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

import ninja.siili.karabiineri.utilities.ParserUtils;

public class PlaceActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private View.OnClickListener arButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PlaceActivity.this, ArActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        //viewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);
        //viewModel.init(drawer);

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
                            case R.id.nav_nearest_place:
                                Toast.makeText(PlaceActivity.this, "nearest", Toast.LENGTH_SHORT).show();
                                break;
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


        Bundle b = getIntent().getExtras();
        if(b != null) {
            final String id = b.getString("id");

            TextView titleTextView = findViewById(R.id.tv_title);
            Button webButton = findViewById(R.id.bt_web);
            TextView placeTypeTextView = findViewById(R.id.tv_place_type);
            TextView routeTypeTextView = findViewById(R.id.tv_route_types);
            TextView routeDiffStartTextView = findViewById(R.id.tv_route_diff_start);
            TextView routeDiffEndTextView = findViewById(R.id.tv_route_diff_end);
            TextView descTextView = findViewById(R.id.tv_desc);
            TextView notesTextView = findViewById(R.id.tv_notes);
            TextView parkingTextView = findViewById(R.id.tv_parking_paid);


            // set texts for all tTextViews
            TextView[] textViews = {titleTextView, placeTypeTextView, routeTypeTextView,
                    routeDiffStartTextView, routeDiffEndTextView, descTextView, notesTextView, parkingTextView};
            String[] keys = ParserUtils.getKeys();

            int i = 0;
            for (TextView textView : textViews) {
                textView.setText(ParserUtils.getPlaceFieldText(this, id, keys[i]));
                i++;
            }

            webButton.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    openWebPage(id);
                }
            });

        } else {
            Toast.makeText(this, "no id in tag", Toast.LENGTH_SHORT).show();
            // TODO make actual exception, tea biscuits
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


    /**
     * Opens a webpage associated with a place with given id.
     * @param id    Id is used to find the correct JSONObject.
     */
    private void openWebPage(String id) {
        String urlAsString = ParserUtils.getPlaceFieldText(PlaceActivity.this, id, "web");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlAsString));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
