package com.example.carloancalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText carCostInput;
    private EditText downPaymentInput;
    private EditText aprInput;
    private TextView loanLengthTextview;
    private SeekBar loanLengthSeekbar;
    private TextView paymentOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initField();
        initListeners();
    }

    private void initField() {
        carCostInput = findViewById(R.id.car_cost_input);
        downPaymentInput = findViewById(R.id.down_payment_input);
        aprInput = findViewById(R.id.apr_input);
        loanLengthTextview = findViewById(R.id.term_textview);
        loanLengthSeekbar = findViewById(R.id.term_seekbar);
        paymentOutput = findViewById(R.id.payment_output);
    }

    private void initListeners() {
        loanLengthSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                loanLengthTextview.setText("Length of Loan(months): " + progress*12);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void calculatePayment(View view) {
        double monthlyInterestRate = Double.parseDouble(aprInput.getText().toString()) / 12;
        double loan = Double.parseDouble(carCostInput.getText().toString()) - Double.parseDouble(downPaymentInput.getText().toString());
        double numberOfPayements = loanLengthSeekbar.getProgress() * 12;
        double monthlyPayment = (monthlyInterestRate * loan) / (1-Math.pow(1+monthlyInterestRate, numberOfPayements*-1));
        paymentOutput.setText("$"+monthlyPayment);
    }
}
