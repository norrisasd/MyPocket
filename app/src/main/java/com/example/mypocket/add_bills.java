package com.example.mypocket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class add_bills extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bills);

        Button applyb = findViewById(R.id.apply_bills_button);
        EditText company = findViewById(R.id.company_bills);
        EditText amount = findViewById(R.id.bills_add_amount);
        EditText duedate = findViewById(R.id.bills_due_date);
        SimpleDateFormat dateN = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        String date = dateN.format(Calendar.getInstance().getTime());
        duedate.setText(date);


        applyb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(add_bills.this);
                String user = getIntent().getStringExtra("user");
                boolean check = db.createBills(Double.parseDouble(amount.getText().toString()),user,duedate.getText().toString(),company.getText().toString());
                if(check){
                    Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(add_bills.this, Billings.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Intent getInt = getIntent();
                    intent.putExtras(getInt);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}