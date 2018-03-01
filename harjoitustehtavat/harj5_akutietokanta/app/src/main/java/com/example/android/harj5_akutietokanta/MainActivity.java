package com.example.android.harj5_akutietokanta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextDirectionHeuristic;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mEditTextNumber;
    private EditText mEditTextName;
    private EditText mEditTextEdition;
    private EditText mEditTextDate;
    private Button mButtonAdd;
    private Button mButtonDelete;
    private TextView mTextViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextNumber = findViewById(R.id.et_number);
        mEditTextName = findViewById(R.id.et_name);
        mEditTextEdition = findViewById(R.id.et_edition);
        mEditTextDate = findViewById(R.id.et_date);

        mButtonAdd = findViewById(R.id.b_add_new);
        mButtonDelete = findViewById(R.id.b_delete_first);
        mTextViewList = findViewById(R.id.tv_listitem);
    }

    private void addEntry() {
        String number = mEditTextNumber.getText().toString();
        String name = mEditTextName.getText().toString();
        String edition = mEditTextEdition.getText().toString();
        String date = mEditTextDate.getText().toString();
    }
}
