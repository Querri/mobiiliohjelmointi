package com.example.querr.harj2_kayttoliittyma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    int[] values;
    int[] answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        values = new int[]{R.id.editText7, R.id.editText8, R.id.editText, R.id.editText4,
                           R.id.editText2, R.id.editText5, R.id.editText3, R.id.editText6};
        answers = new int[]{R.id.textView, R.id.textView6, R.id.textView7, R.id.textView8};
    }


    // clear all input and answer fields
    public void ClearAll(View view) {
        for (int id : values) {
            EditText t = findViewById(id);
            t.setText("");
        }

        for (int id : answers) {
            TextView t = findViewById(id);
            t.setText("");
        }
    }


    // check if value is empty
    private Float ToFloat(EditText text) {
        if (!TextUtils.isEmpty(text.getText())) {
            return Float.parseFloat(text.getText().toString());
        } else {
            return 0f;
        }
    }


    public void add(View view) {
        EditText text1 =  findViewById(R.id.editText7);
        EditText text2 =  findViewById(R.id.editText8);
        TextView answerField = findViewById(R.id.textView);

        answerField.setText(String.valueOf(ToFloat(text1) + ToFloat(text2)));
    }



    public void subtract(View view) {
        EditText text1 =  findViewById(R.id.editText);
        EditText text2 =  findViewById(R.id.editText4);
        TextView answerField = findViewById(R.id.textView6);

        answerField.setText(String.valueOf(ToFloat(text1) - ToFloat(text2)));
    }



    public void multiply(View view) {
        EditText text1 =  findViewById(R.id.editText2);
        EditText text2 =  findViewById(R.id.editText5);
        TextView answerField = findViewById(R.id.textView7);

        answerField.setText(String.valueOf(ToFloat(text1) * ToFloat(text2)));
    }



    public void divide(View view) {
        EditText text1 =  findViewById(R.id.editText3);
        EditText text2 =  findViewById(R.id.editText6);
        TextView answerField = findViewById(R.id.textView8);

        if (ToFloat(text2) != 0) {
            answerField.setText(String.valueOf(ToFloat(text1) / ToFloat(text2)));
        } else {
            answerField.setText("no");
        }

    }
}
