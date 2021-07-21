package com.example.mypocket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class Transaction extends AppCompatActivity {
    ArrayList<String> explist= new ArrayList<>();
    ArrayList<String> inclist= new ArrayList<>();
    ArrayList<String> savingslist= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        String user = this.getIntent().getStringExtra("user");
        Intent prevInt=this.getIntent();
        ListView list = findViewById(R.id.trans_list);
        DBHelper db = new DBHelper(this);


        RadioButton expense= (RadioButton) findViewById(R.id.rad_expense_trans);
        RadioButton income= (RadioButton) findViewById(R.id.Income_rad_trans);
        RadioButton savings = findViewById(R.id.rad_savings_trans);
        SwipeRefreshLayout refreshLayout = findViewById(R.id.swiperefresh_transactionhistory);

        expense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    try{
                        explist = db.getAllExpenseDates(user);
                        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,explist);
                        list.setAdapter(arrayAdapter);
                    }catch(Exception e){

                    }
                    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            explist = db.getAllExpenseDates(user);
                            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,explist);
                            list.setAdapter(arrayAdapter);
                            refreshLayout.setRefreshing(false);
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
                        inclist = db.getAllIncomeDates(user);
                        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,inclist);
                        list.setAdapter(arrayAdapter);
                    }catch(Exception e){

                    }
                    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            inclist = db.getAllIncomeDates(user);
                            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,inclist);
                            list.setAdapter(arrayAdapter);
                            refreshLayout.setRefreshing(false);
                        }
                    });

                }

            }
        });
        savings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    try{
                        savingslist = db.getAllSavingsDates(user);
                        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,savingslist);
                        list.setAdapter(arrayAdapter);
                    }catch(Exception e){

                    }
                    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            savingslist = db.getAllSavingsDates(user);
                            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,savingslist);
                            list.setAdapter(arrayAdapter);
                            refreshLayout.setRefreshing(false);

                        }
                    });


                }

            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(Transaction.this, "YOU ARE UP TO DATE", Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String date = "";
                String check ="";
                if(income.isChecked()){
                    date = inclist.get(position);
                    check="income";
                }
                if(expense.isChecked()){
                    date = explist.get(position);
                    check="expense";
                }
                if(savings.isChecked()){
                    date = savingslist.get(position);
                    check="savings";
                }
                Intent intent = new Intent(getApplicationContext(), Transaction_details.class);
                intent.putExtra("date", date);
                intent.putExtra("check",check);
                intent.putExtras(prevInt);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Transaction.this, Home.class);
        intent.putExtras(getIntent());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}