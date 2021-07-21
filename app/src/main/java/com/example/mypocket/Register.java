package com.example.mypocket;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
EditText fname,lname,user,pass,cpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fname = (EditText)findViewById(R.id.first_name);
        lname=(EditText)findViewById(R.id.last_name);
        user=(EditText)findViewById(R.id.reg_user);
        pass=(EditText)findViewById(R.id.reg_pass);
        cpass=(EditText)findViewById(R.id.re_pass);
    }

    public void setRegister(View v){
        DBHelper db = new DBHelper(this.getApplicationContext());
        String firstname = fname.getText().toString();
        String lastname = lname.getText().toString();
        String username = user.getText().toString();
        String password = pass.getText().toString();
        String cpassword = cpass.getText().toString();
        if(firstname.matches("") || lastname.matches("") || username.matches("") || password.matches("")){
            Toast.makeText(Register.this, "There is an empty field!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.matches(cpassword)){
            Toast.makeText(Register.this, "Password doesnt match", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean check = db.createUser(firstname,lastname,username,password);
        if(check){
            Toast.makeText(Register.this, "Registered", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(Register.this, "Username is taken", Toast.LENGTH_SHORT).show();
        }
        getIntent().putExtra("fullname", firstname);
    }
}