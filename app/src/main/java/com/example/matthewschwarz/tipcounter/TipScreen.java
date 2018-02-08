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
import android.app.AlertDialog;
import android.content.DialogInterface;

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
        tTotal.setText(null); fTotal.setText(null); indivTotal.setText(null);

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
                if(people < 1){
                    showErrorAlert((getString(R.string.peopleError)), R.id.num_people);
                }else if(tip < 1){
                    showErrorAlert((getString(R.string.tipError)), R.id.other_tip_input);
                }else if (total < 1){
                    showErrorAlert((getString(R.string.billError)), R.id.bill_total);
                }else{
                    double tipAmount = total * (tip * .01);
                    double totalWithTip = total + tipAmount;
                    double totalEachPays = totalWithTip / people;
                    //set the strings to the tip values
                    String tipTotal = getString(R.string.tipAmnt, String.valueOf(tipAmount));
                    tTotal.setText(tipTotal);
                    String fullTotal = getString(R.string.totalAmnt, String.valueOf(totalWithTip));
                    fTotal.setText(fullTotal);
                    String splitTotal = getString(R.string.splitAmnt, String.valueOf(totalEachPays));
                    indivTotal.setText(splitTotal);
                }
            }
        });

        View.OnKeyListener mKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {

                switch(v.getId()){
                    case R.id.num_people:
                        if(numPeople.getText().length() > 0 && billTotal.getText().length() > 0
                                && radioGroup.getCheckedRadioButtonId() != -1){
                            calculateButton.setEnabled(true);
                        }else{
                            calculateButton.setEnabled(false);
                        }
                        break;
                    case R.id.bill_total:
                        if(numPeople.getText().length() > 0 && billTotal.getText().length() > 0
                                && radioGroup.getCheckedRadioButtonId() != -1){
                            calculateButton.setEnabled(true);
                        }else{
                            calculateButton.setEnabled(false);
                        }
                        break;
                    case R.id.other_tip_input:
                        if(numPeople.getText().length() > 0 && billTotal.getText().length() > 0
                                && otherAmount.getText().length() > 0 && radioGroup.getCheckedRadioButtonId() != -1){
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
                    if(numPeople.getText().length() > 0 && billTotal.getText().length() > 0){
                        calculateButton.setEnabled(true);
                    }
                }else if(rb == radio18){
                    otherAmount.setVisibility(View.INVISIBLE);
                    tipSelected = "18";
                    Toast.makeText(getApplicationContext(), "18% tip", Toast.LENGTH_SHORT).show();
                    if(numPeople.getText().length() > 0 && billTotal.getText().length() > 0){
                        calculateButton.setEnabled(true);
                    }
                }else if(rb == radio20){
                    otherAmount.setVisibility(View.INVISIBLE);
                    tipSelected = "20";
                    Toast.makeText(getApplicationContext(), "20% tip", Toast.LENGTH_SHORT).show();
                    if(numPeople.getText().length() > 0 && billTotal.getText().length() > 0){
                        calculateButton.setEnabled(true);
                    }
                }else{
                    otherAmount.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "enter tip amount", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void showErrorAlert(String errorMessage, final int fieldId) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(errorMessage)
                .setNeutralButton("Close",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                findViewById(fieldId).requestFocus();
                            }
                        }).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(getString(R.string.peopleKey), numPeople.getText().toString());
        savedInstanceState.putString(getString(R.string.tipKey), tipSelected);
        savedInstanceState.putString(getString(R.string.totalKey), billTotal.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        numPeople.setText(savedInstanceState.getString(getString(R.string.peopleKey)));
        billTotal.setText(savedInstanceState.getString(getString(R.string.totalKey)));
        tipSelected = savedInstanceState.getString(getString(R.string.tipKey));
        if(numPeople.getText().length() > 0 && billTotal.getText().length() > 0 && tipSelected.length() > 0){
            calculateButton.setEnabled(true);
        }


    }
}
