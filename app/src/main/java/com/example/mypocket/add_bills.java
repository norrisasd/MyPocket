package com.example.mypocket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class add_bills extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bills);

        Button applyb = findViewById(R.id.apply_bills_button);
        EditText company = findViewById(R.id.company_bills);
        EditText amount = findViewById(R.id.bills_add_amount);
        EditText duedate = findViewById(R.id.bills_due_date);
        SimpleDateFormat dateN = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        String date = dateN.format(Calendar.getInstance().getTime());
        duedate.setText(date);


        applyb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(add_bills.this);
                String user = getIntent().getStringExtra("user");
                boolean check = db.createBills(Double.parseDouble(amount.getText().toString()),user,duedate.getText().toString(),company.getText().toString());
                if(check){
                    Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(add_bills.this, Billings.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Intent getInt = getIntent();
                    intent.putExtras(getInt);
                    if(duedate.getText().toString().matches("July 22, 2021")){
                        NotificationCompat.Builder build = new NotificationCompat.Builder(add_bills.this,"My Notification")
                                .setSmallIcon(android.R.drawable.ic_dialog_info)
                                .setContentTitle(company.getText().toString())
                                .setContentText("Your bill is due tomorrow worth "+amount.getText().toString()+" PHP")
                                // .setContentIntent(contentIntent)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setAutoCancel(true);
                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(add_bills.this);
                        managerCompat.notify(1,build.build());
                    }
                    else if (duedate.getText().toString().matches("July 21, 2021")){
                        NotificationCompat.Builder build = new NotificationCompat.Builder(add_bills.this,"My Notification")
                                .setSmallIcon(android.R.drawable.ic_dialog_info)
                                .setContentTitle(company.getText().toString())
                                .setContentText("Your bill is due today worth "+amount.getText().toString()+" PHP")
                                // .setContentIntent(contentIntent)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setAutoCancel(true);
                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(add_bills.this);
                        managerCompat.notify(1,build.build());

                    }

                    else{

                    }
                    startActivity(intent);
                    finish();


                }


            }
        });
    }

}