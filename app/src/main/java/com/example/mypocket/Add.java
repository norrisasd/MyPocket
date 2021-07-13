package com.example.mypocket;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Add extends AppCompatActivity {

    EditText amount;
    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        RadioButton expense= (RadioButton) findViewById(R.id.expense_trans_btn);
        RadioButton income= (RadioButton) findViewById(R.id.income_trans_btn);
        amount = (EditText)findViewById(R.id.et_amount);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        expense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    try{
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag("incomeFrag");
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.remove(fragment);
                        fragmentTransaction.commit();
                    }catch(Exception e){

                    }
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    add_expenses ex = new add_expenses();
                    fragmentTransaction.add(R.id.fragment_view,ex,"expenseFrag");
                    fragmentTransaction.commit();
                }

            }
        });
        income.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    try{
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag("expenseFrag");
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.remove(fragment);
                        fragmentTransaction.commit();
                    }catch(Exception e){

                    }
                    fragmentTransaction =getSupportFragmentManager().beginTransaction();
                    add_income ai = new add_income();
                    fragmentTransaction .add(R.id.fragment_view,ai,"incomeFrag");
                    fragmentTransaction.commit();
                }

            }
        });

    }


}