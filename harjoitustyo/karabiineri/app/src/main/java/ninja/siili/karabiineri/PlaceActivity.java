package ninja.siili.karabiineri;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class PlaceActivity extends AppCompatActivity {

    //private RecyclerView mValuesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);


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




            /*mValuesList = findViewById(R.id.rv_values);
            mValuesList.setLayoutManager(new LinearLayoutManager(this));
            mValuesList.setHasFixedSize(true);
            mValuesList.setAdapter(new PlaceAdapter(ParserUtils.getMap(this, id), keys));*/

        } else {
            Toast.makeText(this, "no id in tag", Toast.LENGTH_SHORT).show();
            // TODO make actual exception, tea biscuits
        }
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
