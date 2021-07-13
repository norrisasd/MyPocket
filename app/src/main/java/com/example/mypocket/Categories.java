package com.example.mypocket;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Categories extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        RadioButton expense= (RadioButton) findViewById(R.id.cat_expense_rad);
        RadioButton income= (RadioButton) findViewById(R.id.cat_income_rad);

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
                }

            }
        });
    }
}