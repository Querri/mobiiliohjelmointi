package ninja.siili.karabiineri;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PlaceEditActivity extends AppCompatActivity {

    private EditText mEdittextTitle;
    private RadioButton mRadiobtnIndoors;
    private RadioButton mRadiobtnOutdoors;
    private CheckBox mCheckboxBoulder;
    private CheckBox mCheckboxToprope;
    private CheckBox mCheckboxLead;
    private EditText mEdittextRoutediffStart;
    private EditText mEdittextRoutediffEnd;
    private EditText mEdittextDesc;
    private EditText mEdittextWeb;
    private CheckBox mCheckboxFreeParking;
    private EditText mEdittextNotes;
    private Button mButtonSave;
    private Button mButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_edit);

        mEdittextTitle = findViewById(R.id.et_title);
        mRadiobtnIndoors = findViewById(R.id.rb_indoors);
        mRadiobtnOutdoors = findViewById(R.id.rb_outdoors);
        mCheckboxBoulder = findViewById(R.id.cb_boulder);
        mCheckboxToprope = findViewById(R.id.cb_toprope);
        mCheckboxLead = findViewById(R.id.cb_lead);
        mEdittextRoutediffStart = findViewById(R.id.et_route_diff_start);
        mEdittextRoutediffEnd = findViewById(R.id.et_route_diff_end);
        mEdittextDesc = findViewById(R.id.et_desc);
        mEdittextWeb = findViewById(R.id.et_web);
        mCheckboxFreeParking = findViewById(R.id.cb_parking_free);
        mEdittextNotes = findViewById(R.id.et_notes);
        mButtonSave = findViewById(R.id.bt_save);


        mButtonSave.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {

                Map map = new HashMap();
                map.put("id", 4);
                map.put("lat", 2);
                map.put("lng", 2);

                //ParserUtils.writePlace(this, map);

                Intent intent = new Intent(PlaceEditActivity.this, PlaceActivity.class);
                //intent.putExtra("id", (String) marker.getTag());
                intent.putExtra("id", "1");  // TODO switch to tag
                startActivity(intent);
            }
        });
    }




}
