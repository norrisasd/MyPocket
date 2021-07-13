package com.example.mypocket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;

public class Home extends AppCompatActivity {

    private ImageButton hbutton;
    private ImageButton sbutton;
    private ImageButton tbutton;
    private ImageButton bbutton;
    private FloatingActionButton addbutton;
    FragmentTransaction ft;
    private static DecimalFormat df4 = new DecimalFormat("#.####");


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DBHelper db = new DBHelper(this.getApplicationContext());
        double total = db.getTotalExpenses(getIntent().getStringExtra("user"));
        TextView expense = findViewById(R.id.expense);
        expense.setText(df4.format(getIntent().getExtras().getDouble("total")));

        double totalinc = db.getTotalIncome(getIntent().getStringExtra("user"));
        TextView income = findViewById(R.id.income);
        income.setText(df4.format(getIntent().getExtras().getDouble("totalinc")));

        double totals = db.getTotalSavings(getIntent().getStringExtra("user"));
        TextView savings = findViewById(R.id.saving);
        savings.setText(df4.format(getIntent().getExtras().getDouble("totals")));

        double bal = totalinc - total;
        TextView balance = findViewById(R.id.bal);
        balance.setText(String.valueOf(df4.format(bal)));
//
//        EditText nameasd = findViewById(R.id.test_name);
//        String name = nameasd.getText().toString();
//        TextView v = findViewById(R.id.test_view);
//
//        v.setText(name);


        addbutton = findViewById(R.id.add);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdd();
            }
        });

        hbutton = findViewById(R.id.home_button);
        hbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openHome();
            }
        });

        sbutton = findViewById(R.id.savings_button);
        sbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openSavings();
            }
        });

        tbutton = findViewById(R.id.transaction_button);
        tbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openTrans();
            }
        });

        bbutton = findViewById(R.id.billings_button);
        bbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openBillings();
            }
        });

        Switch bm = findViewById(R.id.budget_button);
         ft = getSupportFragmentManager().beginTransaction();
        bm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                try{
                    if(isChecked) {
                        ft = getSupportFragmentManager().beginTransaction();
                        budget ex = new budget();
                        ft.add(R.id.budget_fragment,ex,"budgetExpense");
                        ft.commit();
                    }else{
                        ft = getSupportFragmentManager().beginTransaction();
                        Fragment f = getSupportFragmentManager().findFragmentByTag("budgetExpense");
                        ft.remove(f);
                        ft.commit();

                    }
                }catch (Exception e){

                }

            }
        });



    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Home.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    public void openAdd() {
        Intent intent = new Intent(this, Add.class);
        Intent getInt = getIntent();
        intent.putExtras(getInt);
        startActivity(intent);
        finish();

    }
    public void openHome(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        //finish();
    }

    public void openSavings(){
        Intent intent = new Intent(this, Savings.class);
        Intent getInt = getIntent();
        intent.putExtras(getInt);
        startActivity(intent);

    }

    public void openTrans(){
        Intent intent = new Intent(this, Transaction.class);
        Intent getInt = getIntent();
        intent.putExtras(getInt);
        startActivity(intent);
    }

    public void openBillings(){
        Intent intent = new Intent(this, Billings.class);
        startActivity(intent);
    }



}