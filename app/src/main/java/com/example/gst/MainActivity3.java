package com.example.gst;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MainActivity3 extends AppCompatActivity {

    ArrayList<String> shoppingList = null;
    ArrayAdapter<String> adapter = null;
    ListView lv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shoppingList = getValues(getApplicationContext());
        Collections.sort(shoppingList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shoppingList);
        lv = findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = ((TextView) view).getText().toString();
                if (selection.trim().equals(shoppingList.get(i).trim())) {
                    removeElement(selection, i);
                } else {
                    Toast.makeText(getApplicationContext(), "Could Not Delete, Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button btn = findViewById(R.id.gstredirectbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addNew) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity3.this);
            builder.setTitle("Add New Item");
            final EditText input = new EditText(this);
            final EditText quantity = new EditText(this);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity3.this);
                    builder1.setTitle("Add Quantity\n");
                    builder1.setView(quantity);
                    builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            input.append("\t\t\t\t\t\tQuantity: " + quantity.getText().toString());
                            shoppingList.add(preferredCase(input.getText().toString()));
                            Collections.sort(shoppingList);
                            PutValues(shoppingList, getApplicationContext());
                            lv.setAdapter(adapter);
                        }
                    });
                    builder1.setNegativeButton("", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder1.show();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
            return true;
        }
        if (id == R.id.ClearAll) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity3.this);
            builder.setTitle("Attention!").setMessage("Clicking OK will remove all items!");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    shoppingList.clear();
                    lv.setAdapter(adapter);
                    PutValues(shoppingList, getApplicationContext());
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
            return true;
        }
        return true;
    }

    public static String preferredCase(String original) {
        if (original.isEmpty())
            return original;
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public static void PutValues(ArrayList inArrayList, Context context) {
        Set WhatToWrite = new HashSet(inArrayList);
        SharedPreferences Word = context.getSharedPreferences("StoredArrayValues", Activity.MODE_PRIVATE);
        SharedPreferences.Editor pref = Word.edit();
        pref.putStringSet("Shopping List", WhatToWrite);
        pref.commit();
    }

    public static ArrayList getValues(Context context) {
        SharedPreferences Word = context.getSharedPreferences("StoredArrayValues", Activity.MODE_PRIVATE);
        Set temp = new HashSet();
        temp = Word.getStringSet("Shopping List", temp);
        return new ArrayList<>(temp);
    }

    public void removeElement(String selection, final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity3.this);
        builder.setTitle("Remove " + selection + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                shoppingList.remove(pos);
                Collections.sort(shoppingList);
                PutValues(shoppingList, getApplicationContext());
                lv.setAdapter(adapter);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
}