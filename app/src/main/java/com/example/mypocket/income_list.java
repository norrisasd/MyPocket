package com.example.mypocket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class income_list extends Fragment {

    ArrayList<String> incomelist = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_income_list, container, false);

//        String[] inccat={"Salary", "Commission", "Gift", "Pension" };
//        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, inccat);

        ListView list = view.findViewById(R.id.income_list);
        DBHelper db = new DBHelper(getActivity().getApplicationContext());
        incomelist = db.getIncomeCategory();
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,incomelist);
        list.setAdapter(arrayAdapter);

        return view;
    }
}