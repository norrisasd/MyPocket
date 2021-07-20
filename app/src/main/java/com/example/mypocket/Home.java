package com.example.mypocket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

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
        try{
            double total = db.getTotalExpenses(getIntent().getStringExtra("user"));
            TextView expense = findViewById(R.id.expense);
            expense.setText(df4.format(total));

            double totalinc = db.getTotalIncome(getIntent().getStringExtra("user"));
            TextView income = findViewById(R.id.income);
            income.setText(df4.format(totalinc));

            double totals = db.getTotalSavings(getIntent().getStringExtra("user"));
            TextView savings = findViewById(R.id.saving);
            savings.setText(df4.format(totals));

            double bal = totalinc - total;
            TextView balance = findViewById(R.id.bal);
            balance.setText(String.valueOf(df4.format(bal)));
        }catch (Exception e){

        }


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


        SharedPreferences sharedPreferences =getSharedPreferences("save",MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
        SharedPreferences prefs = getSharedPreferences("data", 0);
        SharedPreferences.Editor edit = prefs.edit();
        SharedPreferences sharedPref = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sharedPref.edit();

        boolean status = sharedPreferences.getBoolean("value",false);
        boolean ft1 = sharedPreferences.getBoolean("fragment", false);

        bm.setChecked(status);

        try{
            if(ft1)
            {
                ft = getSupportFragmentManager().beginTransaction();
                budget ex = new budget();
                ft.add(R.id.budget_fragment,ex,"budgetExpense");
                ft.commit();
            }
        }
        catch(Exception er){

        }


        bm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                try{
                    if(isChecked) {

                        editor.putBoolean("value",true);
                        editor.putBoolean("fragment",true);
                        editor.apply();
                        bm.setChecked(true);

                        ft = getSupportFragmentManager().beginTransaction();
                        budget ex = new budget();
                        ft.add(R.id.budget_fragment,ex,"budgetExpense");
                        ft.commit();

                    }else{

                        editor.putBoolean("value",false);
                        editor.putBoolean("fragment",false);
                        editor.apply();
                        edit.remove("amount").apply();
                        e.remove("userChoiceSpinner").apply();
                        bm.setChecked(false);


                        ft = getSupportFragmentManager().beginTransaction();
                        Fragment f = getSupportFragmentManager().findFragmentByTag("budgetExpense");
                        ft.remove(f);
                        ft.commit();

                    }
                }catch (Exception e){

                }

            }
        });


        ImageButton nav = findViewById(R.id.menu_button);
        DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
        NavigationView navigationView =findViewById(R.id.navigationview);

        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
                //hamMenu(v);
                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                        int id = item.getItemId();
                        if(id == R.id.signout_menu){

                            Toast.makeText(getApplicationContext(), "Empty Field", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(), Categories.class);
//                            startActivity(intent);
                        }



                        drawerLayout.closeDrawer(Gravity.LEFT);
                        return true;
                    }
                });
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
                        finish();
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
    }
    public void openHome(){
        Intent intent = new Intent(this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
        Intent getInt = getIntent();
        intent.putExtras(getInt);
        startActivity(intent);
    }
    public void hamMenu(View view){
        DrawerLayout drawer = findViewById(R.id.drawerlayout);
        NavigationView navigationView =findViewById(R.id.navigationview);
        if (!drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.openDrawer(Gravity.LEFT);

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                    int id = item.getItemId();
                    if(id == R.id.signout_menu){

                            Toast.makeText(getApplicationContext(), "Empty Field", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(), Categories.class);
//                            startActivity(intent);
                        }



                    drawer.closeDrawer(Gravity.LEFT);
                    return true;
                }
            });
        }
//        else {
//            drawer.closeDrawer(GravityCompat.START);
//        }
    }


}