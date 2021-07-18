package com.example.mypocket;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Categories extends AppCompatActivity{
//    Expense_dialog.expdiaglistener
    FragmentTransaction fragmentTransaction;
    TextView text;
    EditText ecategory;
    EditText icategory;
    DBHelper db;
    String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        RadioButton expense= (RadioButton) findViewById(R.id.cat_expense_rad);
        RadioButton income= (RadioButton) findViewById(R.id.cat_income_rad);
        FloatingActionButton add = findViewById(R.id.categorry_button);
        db = new DBHelper(getApplicationContext());
        db.DefaultExpenseCategories();

        expense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    try{
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag("incomecatFrag");
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.remove(fragment);
                        fragmentTransaction.commit();
                    }catch(Exception e){

                    }
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    expense_list ex = new expense_list();
                    fragmentTransaction.add(R.id.category_fragment,ex,"expensecatFrag");
                    fragmentTransaction.commit();

                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //openDialog();
                            ExpenseDialog();
                        }
                    });
                }

            }
        });
        income.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    try{
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag("expensecatFrag");
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.remove(fragment);
                        fragmentTransaction.commit();
                    }catch(Exception e){

                    }
                    fragmentTransaction =getSupportFragmentManager().beginTransaction();
                    income_list inc = new income_list();
                    fragmentTransaction .add(R.id.category_fragment,inc,"incomecatFrag");
                    fragmentTransaction.commit();

                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //openIncomeDialog();
                            IncomeDialog();
                        }
                    });
                }

            }
        });
    }

    public void ExpenseDialog(){
        LayoutInflater inflater = Categories.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.expense_dialog,null);
        text = findViewById(R.id.textView30);
        ecategory = view.findViewById(R.id.expense_dialog_text);
        user = getIntent().getStringExtra("user");

        AlertDialog.Builder builder = new AlertDialog.Builder(Categories.this);
        builder.setView(view)
                .setTitle("Add Expense Category")
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("APPLY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //get string from input and pass it to text view
                        try {
                            String expcategory = ecategory.getText().toString();
                            if(expcategory.matches("")){

                                Toast.makeText(getApplicationContext(), "Empty Field", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "GOOOOOD", Toast.LENGTH_SHORT).show();
                                text.setText(expcategory);
                                AddExpenseCategory();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
//database
    public void AddExpenseCategory(){
        db = new DBHelper(getApplicationContext());
        boolean check = db.createECategory(user,ecategory.getText().toString());
        if(check){
            Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
        }
    }

    public void IncomeDialog(){
        LayoutInflater inflater = Categories.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.income_dialog,null);
        text = findViewById(R.id.textView30);
        icategory = view.findViewById(R.id.income_dialog);
        user = getIntent().getStringExtra("user");

        AlertDialog.Builder builder = new AlertDialog.Builder(Categories.this);
        builder.setView(view)
                .setTitle("Add Income Category")
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("APPLY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //get string from input and pass it to text view
                        try {
                            String inccategory = icategory.getText().toString();
                            if(inccategory.matches("")){

                                Toast.makeText(getApplicationContext(), "Empty Field", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                text.setText(inccategory);
                                AddIncomeCategory();
                                dialog.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
//database
    public void AddIncomeCategory(){
        db = new DBHelper(getApplicationContext());
        boolean check = db.createICategory(user,icategory.getText().toString());
        if(check){
            Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
        }
    }
}