package ninja.siili.karabiineri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class PlaceActivity extends AppCompatActivity {

    //private RecyclerView mValuesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);


        Bundle b = getIntent().getExtras();
        if(b != null) {
            String id = b.getString("id");

            TextView titleTextView = findViewById(R.id.tv_title);
            TextView placeTypTextView = findViewById(R.id.tv_place_type);
            TextView routeTypeTextView = findViewById(R.id.tv_route_types);
            TextView routeDiffStartTextView = findViewById(R.id.tv_route_diff_start);
            TextView routeDiffEndTextView = findViewById(R.id.tv_route_diff_end);
            TextView descTextView = findViewById(R.id.tv_desc);
            TextView webTextView = findViewById(R.id.tv_web);
            TextView notesTextView = findViewById(R.id.tv_notes);

            TextView[] textViews = {titleTextView, placeTypTextView, routeTypeTextView,
                    routeDiffStartTextView, routeDiffEndTextView, descTextView, webTextView, notesTextView};
            String[] keys = {"title", "place_type", "routes_type", "routes_diff_start",
                    "routes_diff_end", "desc", "web", "notes"};

            // set texts for all tTextViews
            int i = 0;
            for (TextView textView : textViews) {
                textView.setText(ParserUtils.getPlaceFieldText(this, id, keys[i]));
                i++;
            }

            /*mValuesList = findViewById(R.id.rv_values);
            mValuesList.setLayoutManager(new LinearLayoutManager(this));
            mValuesList.setHasFixedSize(true);
            mValuesList.setAdapter(new PlaceAdapter(ParserUtils.getMap(this, id), keys));*/

        } else {
            Toast.makeText(this, "no id in tag", Toast.LENGTH_SHORT).show();
            // TODO make actual exception, tea biscuits
        }
    }


}
