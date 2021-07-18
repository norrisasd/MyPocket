package com.example.mypocket;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
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


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setTitle("Expense Category");
                builder1.setMessage("\nCategory diri");

                builder1.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        EditExpenseCategory();
                    }
                });

                builder1.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //CALL DELETE DATABASE
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder1.create();
                alertDialog.show();


            }
        });


        return view;
    }
    public void EditExpenseCategory(){
        LayoutInflater inflater = expense_list.this.getLayoutInflater();
        View v = inflater.inflate(R.layout.expense_edit_category,null);
        EditText ecategory = v.findViewById(R.id.eedit);
        String user = getActivity().getIntent().getStringExtra("user");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v)
                .setTitle("Edit Expense Category")
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("APPLY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //CALL UPDATE DATABASE
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}