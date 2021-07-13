package com.example.mypocket;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Transaction_details extends AppCompatActivity {
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<Integer> arrayID = new ArrayList<>();
 //   String[] amount={"200", "300", "400", "600" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        ListView dlist = findViewById(R.id.details_list);
        Intent i = getIntent();
        String user =i.getStringExtra("user");
        String date =i.getStringExtra("date");
        String check = i.getStringExtra("check");
        DBHelper db = new DBHelper(this);
        if(check.equals("expense")){
            arrayID = db.getExpensesIDByDate(user,date);
            for(Integer id : arrayID){
                arrayList.add(db.getExpensesAmountById(user,id));
            }

        }else{
            arrayID = db.getIncomeIDByDate(user,date);
            for(Integer id : arrayID){
                arrayList.add(db.getIncomeAmountById(user,id));
            }
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        dlist.setAdapter(arrayAdapter);

        dlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor;
                AlertDialog.Builder builder = new AlertDialog.Builder(Transaction_details.this);
                if(check.equals("income")){
                    cursor = db.getIncomeInfoByID(user,arrayID.get(position));
                    builder.setTitle("Income");
                }else {
                    cursor = db.getExpensesInfoByID(user, arrayID.get(position));
                    builder.setTitle("Expenses");
                }

                builder.setMessage("Amount:"+Double.toString(cursor.getDouble(5))+"\nCategory: "+cursor.getString(3)+"\nDate: "+cursor.getString(2)+"\nNote: "+cursor.getString(4)+"");
                builder.setCancelable(false);
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // When the user click yes button
                        // then app will close
                        finish();
                    }
                });

                // Set the Negative button with No name
                // OnClickListener method is use
                // of DialogInterface interface.
                builder.setNegativeButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        // If user click no
                        // then dialog box is canceled.
                        dialog.cancel();
                    }
                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();

                // Show the Alert Dialog box
                alertDialog.show();
            }
        });
    }
}