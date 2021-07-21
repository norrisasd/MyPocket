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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class income_list extends Fragment {

    ArrayList<String> incomelist = new ArrayList<>();
    int refreshcount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //        String[] inccat={"Salary", "Commission", "Gift", "Pension" };
//        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, inccat);

        View view = inflater.inflate(R.layout.fragment_income_list, container, false);
        String user = getActivity().getIntent().getStringExtra("user");
        SwipeRefreshLayout refreshLayout = view.findViewById(R.id.swiperefreshinc);

        ListView list = view.findViewById(R.id.income_list);
        DBHelper db = new DBHelper(getActivity().getApplicationContext());
        incomelist = db.getUserIncomeCategories(user);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,incomelist);
        arrayAdapter.notifyDataSetChanged();
        list.setAdapter(arrayAdapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                incomelist = db.getUserIncomeCategories(user);
                ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,incomelist);
                arrayAdapter.notifyDataSetChanged();
                list.setAdapter(arrayAdapter);
                refreshLayout.setRefreshing(false);

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                String category=incomelist.get(position);
                builder1.setTitle("Income Category");
                builder1.setMessage("\n"+category);

                builder1.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        EditIncomeCategory(category);
                    }
                });

                builder1.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //CALL DELETE DATABASE
                        db.deleteIncomeCategory(user,category);
                        Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder1.create();
                alertDialog.show();


            }
        });

        return view;
    }

    public void EditIncomeCategory(String inccategory){
        LayoutInflater inflater = income_list.this.getLayoutInflater();
        View v = inflater.inflate(R.layout.income_edit_category,null);
        EditText icategory = v.findViewById(R.id.iedit);
        icategory.setText(inccategory);
        DBHelper db = new DBHelper(getActivity());
        String user = getActivity().getIntent().getStringExtra("user");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v)
                .setTitle("Edit Income Category")
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
                        db.updateIncomeCategory(user,icategory.getText().toString(),inccategory);
                        Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}