package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText display;
    TextView tvSymbol;

    Button clearBtn;
    ImageButton backBtn;
    Button equalsBtn;
    Button periodBtn;
    Button plusBtn;
    Button minusBtn;
    Button multiBtn;
    Button divBtn;
    Button percentBtn;
    Button zeroBtn;
    Button duoZeroBtn;
    Button oneBtn;
    Button twoBtn;
    Button threeBtn;
    Button fourBtn;
    Button fiveBtn;
    Button sixBtn;
    Button sevenBtn;
    Button eightBtn;
    Button nineBtn;

    double first_value;
    //boolean first_flag;
    double sec_value;
    boolean sec_flag;

    String str_value;
    int str_len;
    int cursor_pos;

    boolean plus;
    boolean minus;
    boolean multi;
    boolean div;
    boolean percent;

    //work like pass statement of python.
    boolean pass_state; // not required for any logic just for suppressing the warning.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.et_display);
        display.setShowSoftInputOnFocus(false);
        tvSymbol = findViewById(R.id.tv_symbol);
        tvSymbol.bringToFront();

        clearBtn = findViewById(R.id.clearBtn);
        backBtn = findViewById(R.id.backBtn);
        equalsBtn = findViewById(R.id.equalsBtn);
        periodBtn = findViewById(R.id.periodBtn);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        multiBtn = findViewById(R.id.multiBtn);
        divBtn = findViewById(R.id.divBtn);
        percentBtn = findViewById(R.id.percentBtn);
        zeroBtn = findViewById(R.id.zeroBtn);
        duoZeroBtn = findViewById(R.id.douZeroBtn);
        oneBtn = findViewById(R.id.oneBtn);
        twoBtn = findViewById(R.id.twoBtn);
        threeBtn = findViewById(R.id.threeBtn);
        fourBtn = findViewById(R.id.fourBtn);
        fiveBtn = findViewById(R.id.fiveBtn);
        sixBtn = findViewById(R.id.sixBtn);
        sevenBtn = findViewById(R.id.sevenBtn);
        eightBtn = findViewById(R.id.eightBtn);
        nineBtn = findViewById(R.id.nineBtn);


        //Making cursor available or visible.
        //display.setPressed(true);
        display.requestFocus();

        //Clear Button action.
        clearBtn.setOnClickListener(view -> {
            first_value = 0;
            sec_value = 0;
            sec_flag = false;
            str_value = "";
            display.setText("");
            tvSymbol.setText("");
        });

        //Delete one value from end.
        backBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();
            cursor_pos = display.getSelectionEnd();
            if(cursor_pos > 0) {
                str_value = str_value.substring(0, cursor_pos - 1) + str_value.substring(cursor_pos);
                display.setText(str_value);
                display.setSelection(cursor_pos - 1);
            }
        });

        //Get equals for any value
        equalsBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();
            str_len = str_value.length();
            if(sec_flag) {
                if(str_len > 0 && !(str_len == 1 && str_value.startsWith("-") )) {
                    //This if will not be triggered if str len is 0 of 1 in case of -(symbol) used.
                    sec_value = getValue(str_value);
                    calculate();
                }
                plus = false;
                minus = false;
                multi = false;
                div = false;
                percent = false;
                str_value = String.valueOf(first_value);
                display.setText(str_value);
                //Log.v("New_TAG",str_value);
                setCursor_pos(str_value.length());
                tvSymbol.setText("");
                //Setting sec_flag false make calculator like first opened one.
                sec_flag = false;
            }
        });


        // Add given values.
        plusBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();
            cursor_pos = display.getSelectionEnd();
            str_len = str_value.length();
            if(sec_flag) {
                if(str_len > 0) {
                    if(cursor_pos <= 1 && str_value.startsWith("-")) {
                        display.setText(str_value.substring(1));
                    }
                    else {
                        sec_value = getValue(str_value);

                        calculate();

                        minus = false;
                        multi = false;
                        div = false;
                        percent = false;

                        plus = true;
                        display.setText("");
                        tvSymbol.setText(R.string.plus);
                    }
                }
                else {// str_len 0.
                    plus = true;
                    minus = false;
                    multi = false;
                    div = false;
                    percent = false;

                    tvSymbol.setText(R.string.plus);
                }
            }
            else { //sec_flag = false
                if(str_len > 0) {
                    if(!(cursor_pos <= 1 && str_value.startsWith("-"))) {
                        //This if will be executed if cur pos not equals to 1 and value (-) simultaneously.
                        first_value = getValue(str_value);
                        plus = true;
                        sec_flag = true;
                        tvSymbol.setText(R.string.plus);
                        display.setText("");
                    }
                    else {
                        //This if will be executed if cur pos equals to 1 and value (-) simultaneously.
                        display.setText(str_value.substring(1));
                    }
                }
                // do nothing when str_len = 0
            }
        });


        //Subtract given value.
        minusBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();
            cursor_pos = display.getSelectionEnd();
            str_len = str_value.length();

            if(sec_flag) {
                if(cursor_pos == 0 && !str_value.startsWith("-")) {
                    //Execute when cur pos = 0.
                    btnPressed("-");
                }
                else if(str_len == 1 && str_value.startsWith("-")) {//removed && cursor_pos <= 1
                    //change pre existing symbol.
                    minus = true;

                    plus = false;
                    multi = false;
                    div = false;
                    percent = false;

                    tvSymbol.setText(R.string.minus);
                }
                else {
                    //This if will be executed when above condition not satisfy.
                    sec_value = getValue(str_value);

                    calculate();

                    plus = false;
                    multi = false;
                    div = false;
                    percent = false;

                    minus = true;
                    display.setText("");
                    tvSymbol.setText(R.string.minus);
                }
            }
            else { //sec_flag false.
                if(cursor_pos == 0 && !str_value.startsWith("-")) {
                    //Execute when cur pos = 0.
                    btnPressed("-");
                }
                else if(str_len == 1 && str_value.startsWith("-")) {//removed  && cursor_pos <= 1
                    //do nothing
                    pass_state = true;//dummy variable
                }
                else {
                    //This if will be executed when above condition not satisfy.
                    first_value = getValue(str_value);
                    plus = false;
                    multi = false;
                    div = false;
                    percent = false;

                    minus = true;
                    sec_flag = true;
                    tvSymbol.setText(R.string.minus);
                    display.setText("");
                }
            }
        });

        //Multiplication of given values.
        multiBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();
            cursor_pos = display.getSelectionEnd();
            str_len = str_value.length();

            if(sec_flag) {
                if(str_len == 0 || (str_len == 1 && str_value.startsWith("-")) ) {// removed  && cursor_pos <= 1
                    //change pre existing symbol.
                    multi = true;

                    plus = false;
                    minus = false;
                    div = false;
                    percent = false;

                }
                else {
                    //This if will be executed when above condition not satisfy.
                    sec_value = getValue(str_value);

                    calculate();
                    plus = false;
                    minus = false;
                    div = false;
                    percent = false;

                    multi = true;
                    display.setText("");
                }
                tvSymbol.setText(R.string.multi);
            }

            else { //sec_flag false.
                if(str_len == 0 || (str_len == 1 && str_value.startsWith("-")) ) {// removed  && cursor_pos <= 1
                    // do nothing.
                    pass_state = true;//dummy variable
                }
                else {
                    //This if will be executed when above condition not satisfy.
                    first_value = getValue(str_value);
                    plus = false;
                    minus = false;
                    div = false;
                    percent = false;

                    multi = true;
                    sec_flag = true;
                    tvSymbol.setText(R.string.multi);
                    display.setText("");
                }
            }
        });

        //Division of values.
        divBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();
            cursor_pos = display.getSelectionEnd();
            str_len = str_value.length();

            if(sec_flag) {
                if(str_len == 0 || (str_len == 1 && str_value.startsWith("-")) ) {// removed  && cursor_pos <= 1
                    //change pre existing symbol.
                    div = true;

                    plus = false;
                    minus = false;
                    multi = false;
                    percent = false;

                }
                else {
                    //This if will be executed when above condition not satisfy.
                    sec_value = getValue(str_value);
                    calculate();
                    plus = false;
                    minus = false;
                    multi = false;
                    percent = false;

                    div = true;
                    display.setText("");
                }
                tvSymbol.setText(R.string.div);
            }

            else { //sec_flag false.
                if(str_len == 0 || (str_len == 1 && str_value.startsWith("-")) ) {// removed  && cursor_pos <= 1
                    // do nothing.
                    pass_state = true;//dummy variable
                }
                else {
                    //This if will be executed when above condition not satisfy.
                    first_value = getValue(str_value);
                    plus = false;
                    minus = false;
                    multi = false;
                    percent = false;

                    div = true;
                    sec_flag = true;
                    tvSymbol.setText(R.string.div);
                    display.setText("");
                }
            }
        });

        //Percentage of values.
        percentBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();
            cursor_pos = display.getSelectionEnd();
            str_len = str_value.length();

            if(sec_flag) {
                if(str_len == 0 || (str_len == 1 && str_value.startsWith("-")) ) {// removed  && cursor_pos <= 1
                    //change pre existing symbol.
                    plus = false;
                    minus = false;
                    multi = false;
                    div = false;

                    percent = true;
                }
                else {
                    //This if will be executed when above condition not satisfy.
                    sec_value = getValue(str_value);
                    calculate();
                    plus = false;
                    minus = false;
                    multi = false;
                    div = false;

                    percent = true;
                    display.setText("");
                }
                tvSymbol.setText(R.string.percent);
            }
            else { //sec_flag false.
                if(str_len == 0 || (str_len == 1 && str_value.startsWith("-")) ) {// removed  && cursor_pos <= 1
                    // do nothing.
                    pass_state = true;//dummy variable
                }
                else {
                    //This if will be executed when above condition not satisfy.
                    first_value = getValue(str_value);
                    plus = false;
                    minus = false;
                    multi = false;
                    div = false;

                    percent = true;
                    sec_flag = true;
                    tvSymbol.setText(R.string.percent);
                    display.setText("");
                }
            }
        });


        //Add period in value
        periodBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();

            if(!str_value.contains("."))
                btnPressed(".");
        });

        //Add 0 in value
        zeroBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();

            if(display.getSelectionEnd() > 0)
                btnPressed("0");
        });

        //Add 00 in value
        duoZeroBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();

            if(display.getSelectionEnd() > 0)
                btnPressed("00");
        });

        //Add 1 in value
        oneBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();
            btnPressed("1");
        });

        //Add 2 in value
        twoBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();
            btnPressed("2");
        });

        //Add 3 in value
        threeBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();
            btnPressed("3");
        });

        //Add 4 in value
        fourBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();
            btnPressed("4");
        });

        //Add 5 in value
        fiveBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();
            btnPressed("5");
        });

        //Add 6 in value
        sixBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();
            btnPressed("6");
        });

        //Add 7 in value
        sevenBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();
            btnPressed("7");
        });

        //Add 8 in value
        eightBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();
            btnPressed("8");
        });

        //Add 9 in value
        nineBtn.setOnClickListener(view -> {
            str_value = display.getText().toString();
            btnPressed("9");
        });

    }

    //convert string value to double.
    private double getValue(String str) {
        double value;
        try {
            value = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            value = 0;
        }
        return value;
    }

    //calculate different operators on values.
    private void calculate() {

        if(plus) {
            first_value += sec_value;

        }
        else if(minus) {
            first_value -= sec_value;

        }
        else if(multi) {
            first_value *= sec_value;
        }
        else if(div) {

            try {
                first_value /= sec_value;
            }
            catch(Exception e) {
                tvSymbol.setText(R.string.Error);
            }
        }
        else if(percent) {
            first_value = (first_value * sec_value) / 100;
        }
        sec_value = 0;
    }

    //add values of buttons in EditText on action.
    private void btnPressed(String s) {
        cursor_pos = display.getSelectionEnd();
        if(!(str_value.startsWith("-") && cursor_pos == 0)) {
            str_value = str_value.substring(0, cursor_pos) + s + str_value.substring(cursor_pos);
            display.setText(str_value);
            setCursor_pos(cursor_pos + s.length());
        }
    }

    //manage cursor position in EditText.
    private void setCursor_pos(int pos) {
        //Max length of EditText = 21
        str_value = display.getText().toString();

        int str_len = str_value.length();
        if(pos<21) {
            if(str_len >= pos) {
                display.setSelection(pos);
                cursor_pos = pos;
            }
            else {
                display.setSelection(str_len);
                cursor_pos = str_len;
            }
        }
        else {
            display.setSelection(21);
            cursor_pos = 21;
        }
    }

}