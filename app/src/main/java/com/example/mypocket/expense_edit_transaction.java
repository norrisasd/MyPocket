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


public class expense_edit_transaction extends AppCompatActivity {
    EditText examount,exdate,exnote;
    Spinner excategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_edit_transaction);
        examount = findViewById(R.id.expense_edit_amount);
        exdate = findViewById(R.id.expense_edit_date);
        exnote = findViewById(R.id.expense_edit_notes);
        excategory = findViewById(R.id.expense_edit_spinner);
        Button applyex = findViewById(R.id.expense_edit_button);

        int id = getIntent().getIntExtra("position",0);
        String user = getIntent().getStringExtra("user");
        DBHelper db = new DBHelper(this);
        Cursor cursor = db.getExpensesInfoByID(user,id);
        examount.setText(Double.toString(cursor.getDouble(5)));
        exdate.setText(cursor.getString(2));
        exnote.setText(cursor.getString(4));
        ArrayList<String> spinnerValues = db.getUserCategories(user);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spinnerValues);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        excategory.setAdapter(myAdapter);
        excategory.setSelection(myAdapter.getPosition(cursor.getString(3)));


        applyex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check =db.updateExpenseTransaction(id,Double.parseDouble(examount.getText().toString()),excategory.getSelectedItem().toString(),exdate.getText().toString(),exnote.getText().toString());
                if(check){
                    Toast.makeText(expense_edit_transaction.this, "Updated", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(expense_edit_transaction.this, "Error", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplication(), Transaction_details.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtras(getIntent());
                intent.removeExtra("position");
                startActivity(intent);
                finish();
            }
        });
    }
}