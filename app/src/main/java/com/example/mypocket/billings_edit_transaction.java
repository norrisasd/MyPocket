package com.example.mypocket;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class billings_edit_transaction extends AppCompatActivity {

    EditText bamount,duedate,company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billings_edit_transaction);

        bamount = findViewById(R.id.bills_edit_amount);
        duedate = findViewById(R.id.bills_edit_date);
        company = findViewById(R.id.bills_edit_company);

        Button apply = findViewById(R.id.bills_edit_button);
        int id = getIntent().getIntExtra("position",0);
        String user = getIntent().getStringExtra("user");
        DBHelper db = new DBHelper(this);
        Cursor cursor = db.getBillsInfoByID(user,id);
        bamount.setText(Double.toString(cursor.getDouble(4)));
        duedate.setText(cursor.getString(2));
        company.setText(cursor.getString(3));

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check =db.updateBillsTransaction(id,Double.parseDouble(bamount.getText().toString()),duedate.getText().toString(),company.getText().toString());
                if(check){
                    Toast.makeText(billings_edit_transaction.this, "Updated", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(billings_edit_transaction.this, "Error", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplication(), Billings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtras(getIntent());
                intent.removeExtra("position");
                startActivity(intent);
                finish();
            }
        });

    }
}