package com.example.carloancalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class ColorActivity extends AppCompatActivity {

    private RadioButton redButton, yellowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        redButton = findViewById(R.id.red_button);
        yellowButton = findViewById(R.id.yellow_button);
    }

    @Override
    public void onBackPressed() {
        int color;
        if(redButton.isChecked())
            color = 0xffff0000;
        else if (yellowButton.isChecked())
            color = 0xffffff00;
        else
            color = 0xff0000ff;
        Intent i = new Intent();
        i.putExtra("COLOR", color);
        setResult(RESULT_OK, i);
        finish();
    }

}
