package ninja.siili.karabiineri.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

import ninja.siili.karabiineri.R;

public class PlaceEditActivity extends AppCompatActivity {

    private EditText mEdittextName;
    private EditText mEdittextDesc;
    private EditText mEdittextAccess;
    private Button mButtonSave;
    private Button mButtonBack;

    private double[] mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_edit);

        mEdittextName = findViewById(R.id.et_name);
        mEdittextDesc = findViewById(R.id.et_desc);
        mEdittextAccess = findViewById(R.id.et_access);
        mButtonSave = findViewById(R.id.bt_save);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            mLocation = b.getDoubleArray("location");
        }

        mButtonSave.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                replyIntent.putExtra("name", mEdittextName.getText().toString());
                replyIntent.putExtra("desc", mEdittextDesc.getText().toString());
                replyIntent.putExtra("access", mEdittextAccess.getText().toString());
                replyIntent.putExtra("location", mLocation);

                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }




}
