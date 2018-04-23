package ninja.siili.karabiineri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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

        Bundle b = getIntent().getExtras();
        if(b != null) {
            String id = b.getString("id");

            mTitleTextView.setText(ParserUtils.getPlaceFieldText(this, id, "title"));
            mRoutesCountTextView.setText(ParserUtils.getPlaceFieldText(this, id, "routes_count"));
            mRoutesDiffTextView.setText((ParserUtils.getPlaceFieldText(this, id, "routes_diff")));
            mPlaceTypeTextView.setText((ParserUtils.getPlaceFieldText(this, id, "place_type")));
            mClimbTypeTextView.setText((ParserUtils.getPlaceFieldText(this, id, "climb_type")));
            mPriceTextView.setText((ParserUtils.getPlaceFieldText(this, id, "price")));
            mOpenTextView.setText((ParserUtils.getPlaceFieldText(this, id, "open")));
            mDescTextView.setText((ParserUtils.getPlaceFieldText(this, id, "desc")));
            mParkingAddrTextView.setText((ParserUtils.getPlaceFieldText(this, id, "parking_address")));
            mParkingPriceTextView.setText((ParserUtils.getPlaceFieldText(this, id, "parking_price")));
            mNotesTextView.setText((ParserUtils.getPlaceFieldText(this, id, "notes")));
            mWebTextView.setText((ParserUtils.getPlaceFieldText(this, id, "web")));
        } else {
            Toast.makeText(this, "no id in tag", Toast.LENGTH_SHORT).show();
            // TODO make actual exception, tea biscuits
        }
    }


}
