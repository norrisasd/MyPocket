package com.example.mypocket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;


public class expense_list extends Fragment {

    ArrayList<String> expenselist = new ArrayList<>();
    int ms = 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        String[] excat={"Food", "Electricity", "Bills", "Education" };
//        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, excat);
//        list.setAdapter(arrayAdapter);

        String user = getActivity().getIntent().getStringExtra("user");
        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);
        SwipeRefreshLayout refresh = view.findViewById(R.id.swiperefreshex);

        ListView list = view.findViewById(R.id.expense_category);
        DBHelper db = new DBHelper(getActivity().getApplicationContext());
        expenselist = db.getExpenseCategory(user);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,expenselist);
        arrayAdapter.notifyDataSetChanged();
        list.setAdapter(arrayAdapter);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                expenselist = db.getExpenseCategory(user);
                ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,expenselist);
                arrayAdapter.notifyDataSetChanged();
                list.setAdapter(arrayAdapter);
                refresh.setRefreshing(false);

            }
        });

        return view;
    }

}