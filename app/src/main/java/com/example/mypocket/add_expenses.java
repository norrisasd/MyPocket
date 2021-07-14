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

public class add_expenses extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_add_expenses,container,false);
       Button btn = (Button)view.findViewById(R.id.addexpense_button);
       EditText amount = (EditText)view.findViewById(R.id.et_amount);
        EditText enote = (EditText)view.findViewById(R.id.expense_note);

       AutoCompleteTextView actv= (AutoCompleteTextView)view.findViewById(R.id.date_text);
        SimpleDateFormat dateN = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        String date = dateN.format(Calendar.getInstance().getTime());
        actv.setText(date);

        Spinner ecategory = view.findViewById(R.id.expense_category);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.expcategory));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ecategory.setAdapter(myAdapter);

        ecategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Select Category")){
                    String selecteditem = "";
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            DBHelper db = new DBHelper(getActivity().getApplicationContext());
                            String user = getActivity().getIntent().getStringExtra("user");
                            boolean check = db.createExpense(Double.parseDouble(amount.getText().toString()),user,actv.getText().toString(),selecteditem,enote.getText().toString());
                            if(check){
                                Toast.makeText(getActivity().getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(),Home.class);
                                Intent getInt = getActivity().getIntent();
                                double total = db.getTotalExpenses(user);
                                intent.putExtras(getInt);
                                intent.putExtra("total",total);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        }
                    });
                }
                else{
                    String selecteditem = parent.getItemAtPosition(position).toString();
                    Toast.makeText(getActivity().getBaseContext(), ""+selecteditem, Toast.LENGTH_SHORT).show();

                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            DBHelper db = new DBHelper(getActivity().getApplicationContext());
                            String user = getActivity().getIntent().getStringExtra("user");
                            boolean check = db.createExpense(Double.parseDouble(amount.getText().toString()),user,actv.getText().toString(),selecteditem,enote.getText().toString());
                            if(check){
                                Toast.makeText(getActivity().getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(),Home.class);
                                Intent getInt = getActivity().getIntent();
                                double total = db.getTotalExpenses(user);
                                intent.putExtras(getInt);
                                intent.putExtra("total",total);
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
