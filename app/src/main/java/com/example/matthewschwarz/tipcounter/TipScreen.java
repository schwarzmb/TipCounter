package com.example.matthewschwarz.tipcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TipScreen extends AppCompatActivity {

    private Button resetButton;
    private Button calculateButton;
    private EditText numPeople;
    private EditText billTotal;
    private EditText otherAmount;
    private RadioGroup radioGroup;
    private RadioButton radio15;
    private RadioButton radio18;
    private RadioButton radio20;
    private RadioButton radioOther;
    private TextView tTotal, fTotal, indivTotal;
    //this string is used to hold the tip amount that is selected
    private String tipSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_screen);

        //initialize all the button and text
        resetButton = findViewById(R.id.reset_radio);
        calculateButton = findViewById(R.id.calculate);
        numPeople = findViewById(R.id.num_people);
        billTotal = findViewById(R.id.bill_total);
        radioGroup = findViewById(R.id.tip_group);
        radio15 = findViewById(R.id.fifteen);
        radio18 = findViewById(R.id.eighteen);
        radio20 = findViewById(R.id.twenty);
        radioOther = findViewById(R.id.other_amount);
        otherAmount = findViewById(R.id.other_tip_input);
        tTotal= findViewById(R.id.tipTotal);
        fTotal= findViewById(R.id.fullTotal);
        indivTotal= findViewById(R.id.individualTip);

        //make these not shown or not clickable yet
        otherAmount.setVisibility(View.INVISIBLE);
        calculateButton.setEnabled(false);

        //numPeople.setOnKeyListener(mKeyListener);


        //reset button will clear all text entries when selected
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billTotal.setText(null); numPeople.setText(null); otherAmount.setText(null);
                radioGroup.clearCheck();
                tTotal.setText(null); fTotal.setText(null); indivTotal.setText(null);
                otherAmount.setVisibility(View.INVISIBLE);
                calculateButton.setEnabled(false);
                Toast.makeText(getApplicationContext(), "entries cleared", Toast.LENGTH_SHORT).show();
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double total = Double.parseDouble(billTotal.getText().toString());
                double tip = Double.parseDouble(tipSelected);
                int people = Integer.parseInt(numPeople.getText().toString());
                double tipAmount = total * (tip * .01);
                double totalWithTip = total + tipAmount;
                double totalEachPays = totalWithTip / people;
                tTotal.setText(String.valueOf(tipAmount));
                fTotal.setText(String.valueOf(totalWithTip));
                indivTotal.setText(String.valueOf(totalEachPays));

            }
        });

        View.OnKeyListener mKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {

                switch(v.getId()){
                    case R.id.num_people:
                        if(numPeople.getText().length() > 0 && billTotal.getText().length() > 0){
                            calculateButton.setEnabled(true);
                        }else{
                            calculateButton.setEnabled(false);
                        }
                        break;
                    case R.id.bill_total:
                        if(numPeople.getText().length() > 0 && billTotal.getText().length() > 0){
                            calculateButton.setEnabled(true);
                        }else{
                            calculateButton.setEnabled(false);
                        }
                        break;
                    case R.id.other_tip_input:
                        if(numPeople.getText().length() > 0 && billTotal.getText().length() > 0 && otherAmount.getText().length() > 0){
                            //store the custom tip in tipSelected string to be used when calculating
                            tipSelected = otherAmount.getText().toString();
                            calculateButton.setEnabled(true);
                        }else{
                            calculateButton.setEnabled(false);
                        }
                        break;
                }
                return false;
            }
        };

        //set the key listeners for the inputs
        numPeople.setOnKeyListener(mKeyListener);
        billTotal.setOnKeyListener(mKeyListener);
        otherAmount.setOnKeyListener(mKeyListener);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = radioGroup.findViewById(i);
                if(rb == radio15){
                    otherAmount.setVisibility(View.INVISIBLE);
                    tipSelected = "15";
                    Toast.makeText(getApplicationContext(), "15% tip", Toast.LENGTH_SHORT).show();
                }else if(rb == radio18){
                    otherAmount.setVisibility(View.INVISIBLE);
                    tipSelected = "18";
                    Toast.makeText(getApplicationContext(), "18% tip", Toast.LENGTH_SHORT).show();
                }else if(rb == radio20){
                    otherAmount.setVisibility(View.INVISIBLE);
                    tipSelected = "20";
                    Toast.makeText(getApplicationContext(), "20% tip", Toast.LENGTH_SHORT).show();
                }else{
                    otherAmount.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "enter tip amount", Toast.LENGTH_SHORT).show();

                }
            }
        });







    }
}
