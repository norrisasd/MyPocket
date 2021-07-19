package com.example.mypocket;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class income_edit_transaction extends AppCompatActivity {
    EditText amount,date,note;
    Spinner category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_edit_transaction);
        amount = findViewById(R.id.income_edit_amount);
        date = findViewById(R.id.income_edit_date);
        note = findViewById(R.id.income_edit_notes);
        category = findViewById(R.id.income_edit_spinner);

        Button apply = findViewById(R.id.edit_income_button);
        Intent intent = getIntent();
        int id = getIntent().getIntExtra("position",0);
        String user = getIntent().getStringExtra("user");
        DBHelper db = new DBHelper(this);
        Cursor cursor = db.getIncomeInfoByID(user,id);
        amount.setText(Double.toString(cursor.getDouble(5)));
        date.setText(cursor.getString(2));
        note.setText(cursor.getString(4));
        ArrayList<String> spinnerValues = db.getUserCategories(user);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spinnerValues);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(myAdapter);
        category.setSelection(myAdapter.getPosition(cursor.getString(3)));

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check =db.updateIncomeTransaction(id,Double.parseDouble(amount.getText().toString()),category.getSelectedItem().toString(),date.getText().toString(),note.getText().toString());
                if(check){
                    Toast.makeText(income_edit_transaction.this, "Updated", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(income_edit_transaction.this, "Error", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

    }
}