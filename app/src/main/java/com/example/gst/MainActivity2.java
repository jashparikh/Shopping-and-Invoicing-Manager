package com.example.gst;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity2 extends AppCompatActivity {
    ListView listView;
    String[] cost;
    Button button1, button2;
    TextView tview1;
    EditText edit1;
    double sum;
    double[] finalvalue;
    Snackbar mSnackbar;
    boolean check;
    AlertDialog.Builder confirm;

    @Override
    public void onBackPressed() {
            mSnackbar.show();
        }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent page2 = getIntent();
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        tview1 = findViewById(R.id.tview1);
        edit1 =  findViewById(R.id.edit1);
        listView =findViewById(R.id.listview);
        final LinearLayout layout = findViewById(R.id.lv1);
        mSnackbar = Snackbar.make(layout, R.string.press_back_again, Snackbar.LENGTH_LONG).setAction("Erase and Go Back", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cost = new String[20];
        cost = page2.getStringArrayExtra("product_cost");
        final ArrayAdapter<Double> display = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cost);
        listView.setAdapter(display);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check) {
                    Toast.makeText(getApplicationContext(), "Nothing to Add. Go Back and Insert New Values", Toast.LENGTH_SHORT).show();
                }
                if (!check) {
                    double temp1;
                    finalvalue = new double[20];
                    sum = 0.0;
                    for (int i = 0; i < display.getCount(); i++) {
                        double temp = Double.parseDouble(cost[i]);
                        finalvalue[i] = temp;
                    }


                    for (double i : finalvalue) {
                        sum = sum + i;
                    }
                    if (sum == 0.0) {
                        Toast.makeText(getApplicationContext(), "No Non-Zero Value To Add", Toast.LENGTH_SHORT).show();
                    } else
                        edit1.setText(String.valueOf((float) sum));
                }

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listView == null) {
                    Toast.makeText(getApplicationContext(), "Already Empty", Toast.LENGTH_SHORT).show();
                } else {

                    listView.setAdapter(null);
                    edit1.setText(null);
                    sum = 0.0;
                    cost = null;
                    check = true;
                    Toast.makeText(getApplicationContext(), "Data Cleared", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}