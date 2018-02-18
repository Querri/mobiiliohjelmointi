package com.example.querr.harj2_kayttoliittyma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void add(View view) {
        EditText text1 =  findViewById(R.id.editText7);
        Integer number1 = Integer.parseInt(text1.getText().toString());

        EditText text2 =  findViewById(R.id.editText8);
        Integer number2 = Integer.parseInt(text2.getText().toString());

        Integer answer = number1 + number2;

        // set answer
        TextView answerField = findViewById(R.id.textView);
        answerField.setText(String.valueOf(answer));
    }
}
