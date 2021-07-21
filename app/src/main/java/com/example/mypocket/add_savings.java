package com.example.mypocket;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class add_savings extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_savings,container,false);

        Button sbtn = view.findViewById(R.id.addsavings_button);
        EditText samount = (EditText)view.findViewById(R.id.savings_amount);
        EditText snote = view.findViewById((R.id.savings_note));

        AutoCompleteTextView sdate= (AutoCompleteTextView)view.findViewById(R.id.savings_date);
        SimpleDateFormat dateN = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        String date = dateN.format(Calendar.getInstance().getTime());
        sdate.setText(date);

        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(getActivity().getApplicationContext());
                String user = getActivity().getIntent().getStringExtra("user");
                boolean check = db.createSavings(Double.parseDouble(samount.getText().toString()),user,sdate.getText().toString(),snote.getText().toString());
                if(check){
                    Toast.makeText(getActivity().getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(),Home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Intent getInt = getActivity().getIntent();
                    double totals = db.getTotalSavings(user);
                    intent.putExtras(getInt);
                    intent.putExtra("totals",totals);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        return view;
    }
}