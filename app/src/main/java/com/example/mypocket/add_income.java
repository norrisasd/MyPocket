package com.example.mypocket;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class add_income extends Fragment  {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_income, container, false);

        Button addincome = (Button)view.findViewById(R.id.addincome_button);
        EditText incomeamount = (EditText)view.findViewById(R.id.income_amount);
        EditText inote = view.findViewById(R.id.income_note);

        AutoCompleteTextView datein = (AutoCompleteTextView) view.findViewById(R.id.income_date);
        SimpleDateFormat dateN = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        String indate = dateN.format(Calendar.getInstance().getTime());
        datein.setText(indate);

        Spinner icategory = view.findViewById(R.id.income_category);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.inccategory));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        icategory.setAdapter(myAdapter);

        icategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Select Category")){
                    String selecteditem = "";

                    addincome.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DBHelper db = new DBHelper(getActivity().getApplicationContext());
                            String user = getActivity().getIntent().getStringExtra("user");
                            boolean check = db.createIncome(Double.parseDouble(incomeamount.getText().toString()),user,datein.getText().toString(),selecteditem,inote.getText().toString());
                            if(check){
                                Toast.makeText(getActivity().getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(),Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                Intent getInt = getActivity().getIntent();
                                double totalinc = db.getTotalIncome(user);
                                intent.putExtras(getInt);
                                intent.putExtra("totalinc",totalinc);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        }
                    });
                }
                else{
                    String selecteditem = parent.getItemAtPosition(position).toString();
                    Toast.makeText(getActivity().getBaseContext(), ""+selecteditem, Toast.LENGTH_SHORT).show();

                    addincome.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DBHelper db = new DBHelper(getActivity().getApplicationContext());
                            String user = getActivity().getIntent().getStringExtra("user");
                            boolean check = db.createIncome(Double.parseDouble(incomeamount.getText().toString()),user,datein.getText().toString(),selecteditem,inote.getText().toString());
                            if(check){
                                Toast.makeText(getActivity().getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(),Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                Intent getInt = getActivity().getIntent();
                                double totalinc = db.getTotalIncome(user);
                                intent.putExtras(getInt);
                                intent.putExtra("totalinc",totalinc);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
}

