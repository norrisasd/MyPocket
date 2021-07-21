package com.example.mypocket;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class savings_edit_transaction extends AppCompatActivity {
    EditText samount,sdate,snote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings_edit_transaction);
        samount = findViewById(R.id.savings_edit_amount);
        sdate = findViewById(R.id.savings_edit_date);
        snote = findViewById(R.id.savings_edit_note);
        Button applys = findViewById(R.id.savings_edit_button);

        int id = getIntent().getIntExtra("position",0);
        String user = getIntent().getStringExtra("user");
        DBHelper db = new DBHelper(this);
        Cursor cursor = db.getSavingsInfoByID(user,id);
        samount.setText(Double.toString(cursor.getDouble(4)));
        sdate.setText(cursor.getString(2));
        snote.setText(cursor.getString(3));



        applys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check =db.updateSavingsTransaction(id,Double.parseDouble(samount.getText().toString()),sdate.getText().toString(),snote.getText().toString());
                if(check){
                    Toast.makeText(savings_edit_transaction.this, "Updated", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(savings_edit_transaction.this, "Error", Toast.LENGTH_SHORT).show();
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