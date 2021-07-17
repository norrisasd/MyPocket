package com.example.mypocket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button Regbutton;
    private Button Logbutton;
    EditText user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        AutoCompleteTextView autotest = (AutoCompleteTextView)findViewById(R.id.test);
//        SimpleDateFormat dateF = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
//        String date = dateF.format(Calendar.getInstance().getTime());
//        autotest.setText(date);
        user = (EditText)findViewById(R.id.username_log);
        pass = (EditText)findViewById(R.id.pass_log);
        Regbutton = (Button) findViewById(R.id.Register_button);
        Regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });

        Logbutton = (Button) findViewById(R.id.Login_button);
        Logbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHome();
            }
        });
    }
    public void openRegister(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
    public void openHome(){
        String username = user.getText().toString();
        String password = pass.getText().toString();
        if(username.matches("") || password.matches("")){
            Toast.makeText(MainActivity.this, "There is an empty field!", Toast.LENGTH_SHORT).show();
            return;
        }
        DBHelper db = new DBHelper(this.getApplicationContext());
        boolean check = db.setLogin(username,password);
        if (check) {

            Intent intent = new Intent(this, Home.class);
            double total = db.getTotalExpenses(username);

            intent.putExtra("total",total);

            double totalinc = db.getTotalIncome(username);
            intent.putExtra("totalinc",totalinc);

            double totals = db.getTotalSavings(username);
            intent.putExtra("totals",totals);


            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        }

    }

}