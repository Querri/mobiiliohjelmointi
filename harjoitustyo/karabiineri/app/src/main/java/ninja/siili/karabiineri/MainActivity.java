package ninja.siili.karabiineri;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mGotoMapButton;
    private Button mGotoNearestButton;
    private Button mGotoSettingsButton;
    private Button mGotoFeedbackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGotoMapButton = findViewById((R.id.bt_goto_map));
        mGotoNearestButton = findViewById(R.id.bt_goto_nearest);
        mGotoSettingsButton = findViewById(R.id.bt_goto_settings);
        mGotoFeedbackButton = findViewById(R.id.bt_goto_feedback);

        mGotoMapButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapActivity.class));
            }
        });

        mGotoSettingsButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        mGotoFeedbackButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
            }
        });
    }
}
