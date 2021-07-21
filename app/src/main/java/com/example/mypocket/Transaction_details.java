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

        }
        else if(check.equals("savings")) {
            arrayID = db.getSavingsIDByDate(user, date);
            for (Integer id : arrayID) {
                arrayList.add(db.getSavingsAmountById(user, id));
            }
        }
        else{
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
                    builder.setMessage("Amount:"+Double.toString(cursor.getDouble(5))+"\nCategory: "+cursor.getString(3)+"\nDate: "+cursor.getString(2)+"\nNote: "+cursor.getString(4)+"");
//                    builder.setCancelable(false);
                }
                else if(check.equals("savings")){
                    cursor = db.getSavingsInfoByID(user,arrayID.get(position));
                    builder.setTitle("Savings");
                    builder.setMessage("Amount:"+Double.toString(cursor.getDouble(4))+"\nDate: "+cursor.getString(2)+
                            "\nNote: "+cursor.getString(3)+"");
                }
                else {
                    cursor = db.getExpensesInfoByID(user, arrayID.get(position));
                    builder.setTitle("Expenses");
                    builder.setMessage("Amount:"+Double.toString(cursor.getDouble(5))+"\nCategory: "+cursor.getString(3)+"\nDate: "+cursor.getString(2)+"\nNote: "+cursor.getString(4)+"");
                }

                builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(check.equals("income")){
//                            EditIncomeTransaction();
                            Intent intent = new Intent(getApplicationContext(), income_edit_transaction.class);
                            intent.putExtra("position",arrayID.get(position));
                            intent.putExtras(getIntent());
                            //intent.putExtra("incdeets",getIntent());
                            startActivity(intent);
                        }

                        else if(check.equals("savings")){
                            Intent intent = new Intent(getApplicationContext(), savings_edit_transaction.class);
                            intent.putExtra("position",arrayID.get(position));
                            intent.putExtras(getIntent());
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(getApplicationContext(), expense_edit_transaction.class);
                            intent.putExtra("position",arrayID.get(position));
                            intent.putExtras(getIntent());
                            startActivity(intent);
                        }

                    }
                });

                builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(check.equals("income")){
                            //DELETE FOR INCOME
                            db.DeleteIncomeTransaction(user,arrayID.get(position));
                            startActivity(getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtras(getIntent()));
                        }

                         else if(check.equals("savings")){
                            //DELETE FOR SAVINGS
                            db.DeleteSavingsTransaction(user,arrayID.get(position));
                            startActivity(getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtras(getIntent()));
                        }
                         else
                            //DELETE FOR EXPENSES
                            db.DeleteExpenseTransaction(user,arrayID.get(position));
                            startActivity(getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtras(getIntent()));


                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });
    }

    //    public void EditIncomeTransaction(){
//        LayoutInflater inflater = Transaction_details.this.getLayoutInflater();
//        View v = inflater.inflate(R.layout.income_edit_transaction,null);
//        EditText icategory = v.findViewById(R.id.income_transaction);
//        String user = getIntent().getStringExtra("user");
//
//        AlertDialog.Builder builderinc = new AlertDialog.Builder(this);
//        builderinc.setView(v)
//                .setTitle("Edit Income Transaction")
//                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                })
//                .setPositiveButton("APPLY", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //CALL UPDATE DATABASE
//                    }
//                });
//        AlertDialog alertDialog1 = builderinc.create();
//        alertDialog1.show();
//    }
}