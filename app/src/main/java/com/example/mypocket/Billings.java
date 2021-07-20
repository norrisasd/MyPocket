package com.example.mypocket;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Billings extends AppCompatActivity {

    ArrayList<String> billslist = new ArrayList<>();
    ArrayList<Integer> arrayID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billings);

        String user = this.getIntent().getStringExtra("user");
        FloatingActionButton addb = findViewById(R.id.add_bills_button);
        SimpleDateFormat dateN = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        String date = dateN.format(Calendar.getInstance().getTime());

        addb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), add_bills.class);
                Intent getInt = getIntent();
                intent.putExtras(getInt);
                startActivity(intent);
            }
        });

        ListView list = findViewById(R.id.bills_listview);
        DBHelper db = new DBHelper(Billings.this);
        billslist = db.getBillsCompany(user);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,billslist);
        arrayAdapter.notifyDataSetChanged();
        list.setAdapter(arrayAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Billings.this);
                arrayID = db.getBillsID(user);
                Cursor cursor = db.getBillsInfoByID(user,arrayID.get(position));
                builder1.setTitle(cursor.getString(3));
                builder1.setMessage("\nAmount\t:\t"+cursor.getDouble(4)+"\nDue Date\t:\t"+cursor.getString(2)+"");

                builder1.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = new Intent(Billings.this, billings_edit_transaction.class);
                        intent.putExtra("position",arrayID.get(position));
                        intent.putExtras(getIntent());
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                });

                builder1.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //CALL DELETE DATABASE
                        db.deleteBills(user,arrayID.get(position));
                        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        arrayAdapter.notifyDataSetChanged();
                        startActivity(getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtras(getIntent()));

                        dialog.dismiss();
                    }
                });
                builder1.setNeutralButton("PAID", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean check = db.createExpense(cursor.getDouble(4),user,date,"Bills","");
                        if(check){
                            Toast.makeText(getApplicationContext(), "Paid", Toast.LENGTH_SHORT).show();
                            startActivity(getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtras(getIntent()));
                            db.deleteBills(user,arrayID.get(position));
                        }


                    }
                });

                AlertDialog alertDialog = builder1.create();
                alertDialog.show();


            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Billings.this, Home.class);
        intent.putExtras(getIntent());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}