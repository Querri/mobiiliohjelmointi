package ninja.siili.karabiineri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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

    private String ID = "1";

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

        mTitleTextView.setText(ParserUtils.getPlaceFieldText(this, ID, "title"));
        mRoutesCountTextView.setText(ParserUtils.getPlaceFieldText(this, ID, "routes_count"));
        mRoutesDiffTextView.setText((ParserUtils.getPlaceFieldText(this, ID, "routes_diff")));
        mPlaceTypeTextView.setText((ParserUtils.getPlaceFieldText(this, ID, "place_type")));
        mClimbTypeTextView.setText((ParserUtils.getPlaceFieldText(this, ID, "climb_type")));
        mPriceTextView.setText((ParserUtils.getPlaceFieldText(this, ID, "price")));
        mOpenTextView.setText((ParserUtils.getPlaceFieldText(this, ID, "open")));
        mDescTextView.setText((ParserUtils.getPlaceFieldText(this, ID, "desc")));
        mParkingAddrTextView.setText((ParserUtils.getPlaceFieldText(this, ID, "parking_address")));
        mParkingPriceTextView.setText((ParserUtils.getPlaceFieldText(this, ID, "parking_price")));
        mNotesTextView.setText((ParserUtils.getPlaceFieldText(this, ID, "notes")));
        mWebTextView.setText((ParserUtils.getPlaceFieldText(this, ID, "web")));
    }


}
