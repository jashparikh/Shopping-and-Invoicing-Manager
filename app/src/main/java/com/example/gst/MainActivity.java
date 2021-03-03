package com.example.gst;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tv1, tv2, tv3, tv4, tv5, tv6;
    Button btn1, btn2, btn3, btn4;
    EditText edt1, edt2;
    RadioGroup rg1;
    RadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7;
    Double a1, gst;
    public static final String product_cost = "product_cost";
    Intent page2;
    ArrayList<Double> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rg1 = (findViewById(R.id.rg1));
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv6 = findViewById(R.id.tv6);
        TextView tv_link = findViewById(R.id.tv5);
        tv_link.setMovementMethod(LinkMovementMethod.getInstance());
        tv_link.setLinksClickable(true);
        tv_link.setLinkTextColor(Color.BLUE);
        edt1 = findViewById(R.id.edt1);
        edt2 = findViewById(R.id.edt2);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        rb5 = findViewById(R.id.rb5);
        rb6 = findViewById(R.id.rb6);
        rb7 = findViewById(R.id.rb7);

        page2 = new Intent(this, MainActivity2.class);
        array = new ArrayList<>(20);
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb1:
                        a1 = 5.0;
                        break;
                    case R.id.rb2:
                        a1 = 12.0;
                        break;
                    case R.id.rb3:
                        a1 = 18.0;
                        break;
                    case R.id.rb4:
                        a1 = 28.0;
                        break;
                    case R.id.rb5:
                        a1 = 3.0;
                        break;
                    case R.id.rb6:
                        a1 = 0.25;
                        break;
                    case R.id.rb7:
                        a1 = 0.0;
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((edt1.getText().toString().matches("")) || (rg1.getCheckedRadioButtonId() == -1)) {   //check if user input is missing
                    Toast.makeText(getApplicationContext(), "Please Enter Product MRP and Select the GST Slab as Applicable", Toast.LENGTH_LONG).show();
                } else {
                    Double b = Double.valueOf(edt1.getText().toString());
                    gst = b + (b * (a1 / 100));
                    edt2.setText(String.valueOf(gst));
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt1.setText("");
                edt2.setText("");
                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(false);
                rb5.setChecked(false);
                rb6.setChecked(false);
                a1 = 0.0;
                Toast.makeText(getApplicationContext(), "Cleared", Toast.LENGTH_SHORT).show();
            }
        });


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edt2.getText().toString().isEmpty()) {
                    array.add(gst);
                    edt2.setText(null);
                    Toast.makeText(getApplicationContext(), "ADDED " + gst, Toast.LENGTH_SHORT).show();
                    if (array.size() == 20) {
                        Toast.makeText(getApplicationContext(), "You can only add up to 20 items in the list", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Click CALCULATE to Compute Results Before Adding", Toast.LENGTH_LONG).show();
                }

            }

        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPage2();
            }
        });
    }

    public void openPage2() {
        if (array.isEmpty()) {
            Toast.makeText(getApplicationContext(), "List is Empty. Please Add Values First", Toast.LENGTH_SHORT).show();
        } else {
            String[] arraypass = new String[array.size()];
            for (int i = 0; i < array.size(); i++) {
                arraypass[i] = String.valueOf(array.get(i));
            }
            page2.putExtra("product_cost", arraypass);
            startActivity(page2);
            edt1.setText(null);
            edt2.setText(null);
            rb1.setChecked(false);
            rb2.setChecked(false);
            rb3.setChecked(false);
            rb4.setChecked(false);
            rb5.setChecked(false);
            rb6.setChecked(false);
            array.clear();
        }

    }
}
