package com.example.carloancalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layout;

    private EditText carCostInput;
    private EditText downPaymentInput;
    private EditText aprInput;
    private TextView loanLengthTextview;
    private SeekBar loanLengthSeekbar;
    private TextView paymentOutput;
    private RadioGroup radioGroup;
    private RadioButton loanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initField();
        initListeners();
    }

    private void initField() {
        layout = findViewById(R.id.main_layout);
        carCostInput = findViewById(R.id.car_cost_input);
        downPaymentInput = findViewById(R.id.down_payment_input);
        aprInput = findViewById(R.id.apr_input);
        loanLengthTextview = findViewById(R.id.term_textview);
        loanLengthSeekbar = findViewById(R.id.term_seekbar);
        paymentOutput = findViewById(R.id.payment_output);
        radioGroup = findViewById(R.id.radio_group);
        loanButton = findViewById(R.id.loan_button);
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
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.lease_button)
                {
                    loanLengthSeekbar.setProgress(3);
                    loanLengthSeekbar.setEnabled(false);
                } else {
                    loanLengthSeekbar.setProgress(0);
                    loanLengthSeekbar.setEnabled(true);
                }
            }
        });
    }

    public void calculatePayment(View view) {
        try {
            double monthlyInterestRate = Double.parseDouble(aprInput.getText().toString()) / 100 / 12;
            if(loanButton.isChecked())
            {
                double carCost = Double.parseDouble(carCostInput.getText().toString());
                double downPayment = Double.parseDouble(downPaymentInput.getText().toString());
                double numberOfPayements = (loanLengthSeekbar.getProgress() * 12 == 0) ? 1 : loanLengthSeekbar.getProgress() * 12;
                double loan = carCost - downPayment;
                double monthlyPayment = (monthlyInterestRate * loan) / (1-Math.pow(1+monthlyInterestRate, numberOfPayements*-1));
                paymentOutput.setText(String.format("%.2f", monthlyPayment));
            } else {
                double carCost = Double.parseDouble(carCostInput.getText().toString());
                double downPayment = Double.parseDouble(downPaymentInput.getText().toString());
                double numberOfPayements = (loanLengthSeekbar.getProgress() * 12 == 0) ? 1 : loanLengthSeekbar.getProgress() * 12;
                double loan = carCost / 3 - downPayment;
                double monthlyPayment = (monthlyInterestRate * loan) / (1-Math.pow(1+monthlyInterestRate, numberOfPayements*-1));
                paymentOutput.setText(String.format("%.2f", monthlyPayment));
            }
        } catch (Exception e)
        {
            Toast.makeText(layout.getContext(), "One or more fields are empty", Toast.LENGTH_SHORT).show();
        }
    }
}
