package ninja.siili.karabiineri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class PlaceActivity extends AppCompatActivity {

    private RecyclerView mValuesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);


        Bundle b = getIntent().getExtras();
        if(b != null) {
            String id = b.getString("id");

            String[] keys = {"id", "lat", "lng", "title", "place_type", "routes_diff",
                            "routes_type", "desc", "parking_price", "notes", "web"};


            mValuesList = findViewById(R.id.rv_values);

            mValuesList.setLayoutManager(new LinearLayoutManager(this));
            mValuesList.setHasFixedSize(true);
            mValuesList.setAdapter(new PlaceAdapter(ParserUtils.getMap(this, id), keys));

        } else {
            Toast.makeText(this, "no id in tag", Toast.LENGTH_SHORT).show();
            // TODO make actual exception, tea biscuits
        }
    }


}
