package ninja.siili.karabiineri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PlaceActivity extends AppCompatActivity {

    private TextView mTitleTextView;
    private TextView mRoutesDiffTextView;
    private TextView mRoutesCountTextView;
    private TextView mPlaceTypeTextView;
    private TextView mClimbTypeTextView;
    private TextView mPriceTextView;
    private TextView mOpenTextView;
    private TextView mDescTextView;
    private TextView mParkingAddrTextView;
    private TextView mParkingPriceTextView;
    private TextView mNotesTextView;
    private TextView mWebTextView;

    private int ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        mTitleTextView = findViewById(R.id.tv_title);
        mRoutesCountTextView = findViewById(R.id.tv_routes_count);
        mRoutesDiffTextView = findViewById(R.id.tv_routes_diff);
        mPlaceTypeTextView = findViewById(R.id.tv_place_type);
        mClimbTypeTextView = findViewById(R.id.tv_climb_type);
        mPriceTextView = findViewById(R.id.tv_price);
        mOpenTextView = findViewById(R.id.tv_open);
        mDescTextView = findViewById(R.id.tv_desc);
        mParkingAddrTextView = findViewById(R.id.tv_parking_address);
        mParkingPriceTextView = findViewById(R.id.tv_parking_price);
        mNotesTextView = findViewById(R.id.tv_notes);
        mWebTextView = findViewById(R.id.tv_web);

        mTitleTextView.setText(getFieldText(ID, "title"));
        mRoutesCountTextView.setText(getFieldText(ID, "routes_count"));
        mRoutesDiffTextView.setText((getFieldText(ID, "routes_diff")));
        mPlaceTypeTextView.setText((getFieldText(ID, "place_type")));
        mClimbTypeTextView.setText((getFieldText(ID, "climb_type")));
        mPriceTextView.setText((getFieldText(ID, "price")));
        mOpenTextView.setText((getFieldText(ID, "open")));
        mDescTextView.setText((getFieldText(ID, "desc")));
        mParkingAddrTextView.setText((getFieldText(ID, "parking_address")));
        mParkingPriceTextView.setText((getFieldText(ID, "parking_price")));
        mNotesTextView.setText((getFieldText(ID, "notes")));
        mWebTextView.setText((getFieldText(ID, "web")));
    }




    private String getFieldText(int id, String fieldName) {
        JSONParser parser = new JSONParser();

        try {
            InputStream stream = getAssets().open("test.json");
            InputStreamReader streamReader = new InputStreamReader((stream));

            JSONObject jsonObject = (JSONObject) parser.parse(streamReader);
            return (String) jsonObject.get(fieldName);


        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "IO exeption", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (ParseException e) {
            Toast.makeText(this, "parse exeption", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return ""; // TODO don't leave it like this
    }
}
