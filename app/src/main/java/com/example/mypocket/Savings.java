package com.example.mypocket;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Savings extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        FloatingActionButton savings = findViewById(R.id.addsaving_button);
        Switch sm = findViewById(R.id.saving_switch);


        SharedPreferences sharedPreferences =getSharedPreferences("save1",MODE_PRIVATE);
        sm.setChecked(sharedPreferences.getBoolean("value1",true));


        savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.savings_fragment, new add_savings()).commit();
            }
        });

        sm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                try{
                    if(isChecked) {
                        SharedPreferences.Editor editor = getSharedPreferences("save1",MODE_PRIVATE).edit();
                        editor.putBoolean("value1",true);
                        editor.apply();
                        sm.setChecked(true);


                    }else{
                        SharedPreferences.Editor editor = getSharedPreferences("save1",MODE_PRIVATE).edit();
                        editor.putBoolean("value1",false);
                        editor.apply();
                        sm.setChecked(false);

                    }
                }catch (Exception e){

                }

            }
        });


    }
}