package com.example.mypocket;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.DecimalFormat;

public class budget extends Fragment {

    public String weeklimit = "";
    public String Dailylimit = "";
    public String dlimit = "";
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    FragmentTransaction ft;
    EditText wbudget;

    ProgressBar bar;
    int counter = 0;
    TextView text;
    Handler handler;

    public budget(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        ImageButton bobutton = (ImageButton) view.findViewById(R.id.budgetok_button);
         wbudget = view.findViewById(R.id.weekbud);
        ImageButton editbutton = view.findViewById(R.id.edit_button);

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wbudget.setEnabled(true);
            }
        });




        Spinner period = view.findViewById(R.id.period_spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.selectperiod));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        period.setAdapter(myAdapter);

        period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Select budget")){

                    ft = getChildFragmentManager().beginTransaction();
                    Fragment f = getChildFragmentManager().findFragmentByTag("monthly");
                    Fragment f1 = getChildFragmentManager().findFragmentByTag("weekly");
                    if(f != null ){
                        ft.remove(f);
                        ft.commit();
                    }
                    else if(f1 != null){
                        ft.remove(f1);
                        ft.commit();
                    }
                    bobutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity().getApplicationContext(), "NO SELECTED BUDGET!", Toast.LENGTH_SHORT).show();
                            wbudget.setEnabled(false);
                        }
                    });
                }

                else{
                    if(parent.getItemAtPosition(position).equals("Monthly budget")){
                        Fragment f1 = getChildFragmentManager().findFragmentByTag("weekly");

                        ft = getChildFragmentManager().beginTransaction();
                        if(f1 != null){
                            ft.remove(f1);
                            ft.commit();
                        }

                        bobutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Fragment f = getChildFragmentManager().findFragmentByTag("monthly");

                                DBHelper db = new DBHelper(getActivity().getApplicationContext());
                                double total = db.getTotalExpenses(getActivity().getIntent().getStringExtra("user"));
                                double totalinc = db.getTotalIncome(getActivity().getIntent().getStringExtra("user"));
                                double bal = totalinc - total;

                                try {

                                    double bud = Double.parseDouble(wbudget.getText().toString());

                                    if (bud > 0 && bud <= bal) {

                                        ft = getChildFragmentManager().beginTransaction();

                                        if(f != null){
                                            ft.remove(f);
                                            ft.commit();
                                        }
                                        else{
                                            ft = getChildFragmentManager().beginTransaction();
                                            monthly_budget month = new monthly_budget();
                                            ft.add(R.id.limit_fragment,month,"monthly");
                                            ft.commit();
                                        }


                                        double weeklylimit = bud/4;
                                        double dailylimit = bud/31;
                                        weeklimit = df2.format(weeklylimit);
                                        Dailylimit = df2.format(dailylimit);
                                        wbudget.setEnabled(false);

                                    }

                                    else{

                                        ft = getChildFragmentManager().beginTransaction();
                                        f = getChildFragmentManager().findFragmentByTag("monthly");
                                        if(f != null){
                                            ft.remove(f);
                                            ft.commit();
                                        }
                                        Toast.makeText(getActivity().getApplicationContext(), "INSUFFICIENT BALANCE!", Toast.LENGTH_SHORT).show();
                                    }

                                } catch(Exception e){
                                    Toast.makeText(getActivity().getApplicationContext(), "Empty Field", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }



                    else if(parent.getItemAtPosition(position).equals("Weekly budget")) {

                        Fragment f1 = getChildFragmentManager().findFragmentByTag("monthly");

                        ft = getChildFragmentManager().beginTransaction();
                        if(f1 != null){
                            ft.remove(f1);
                            ft.commit();
                        }

                        bobutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Fragment f = getChildFragmentManager().findFragmentByTag("weekly");

                                DBHelper db = new DBHelper(getActivity().getApplicationContext());
                                double total = db.getTotalExpenses(getActivity().getIntent().getStringExtra("user"));
                                double totalinc = db.getTotalIncome(getActivity().getIntent().getStringExtra("user"));
                                double bal = totalinc - total;

                                try {

                                    double bud = Double.parseDouble(wbudget.getText().toString());

                                    if (bud <= bal) {

                                        ft = getChildFragmentManager().beginTransaction();

                                        if(f != null){
                                            ft.remove(f);
                                            ft.commit();
                                        }

                                        ft = getChildFragmentManager().beginTransaction();
                                        weekly_budget week = new weekly_budget();
                                        ft.add(R.id.limit_fragment,week,"weekly");
                                        ft.commit();


                                        double dl = bud/7;
                                        dlimit = df2.format(dl);

                                        wbudget.setEnabled(false);

                                    }

                                    else{

                                        ft = getChildFragmentManager().beginTransaction();
                                        f = getChildFragmentManager().findFragmentByTag("weekly");
                                        if(f != null){
                                            ft.remove(f);
                                            ft.commit();
                                        }

                                        Toast.makeText(getActivity().getApplicationContext(), "INSUFFICIENT BALANCE!", Toast.LENGTH_SHORT).show();


                                    }

                                } catch(Exception e){
                                    Toast.makeText(getActivity().getApplicationContext(), "Empty Field", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }



                    else if(parent.getItemAtPosition(position).equals("Daily budget")){
                      //  Toast.makeText(getActivity().getApplicationContext(),"DAILY SHIT", Toast.LENGTH_SHORT).show();
                        ft = getChildFragmentManager().beginTransaction();
                        Fragment f = getChildFragmentManager().findFragmentByTag("monthly");
                        Fragment f1 = getChildFragmentManager().findFragmentByTag("weekly");
                        if(f != null ){
                            ft.remove(f);
                            ft.commit();
                        }
                        else if(f1 != null){
                            ft.remove(f1);
                            ft.commit();
                        }

                        bobutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try{

                                    DBHelper db = new DBHelper(getActivity().getApplicationContext());
                                    double total = db.getTotalExpenses(getActivity().getIntent().getStringExtra("user"));
                                    double totalinc = db.getTotalIncome(getActivity().getIntent().getStringExtra("user"));
                                    double bal = totalinc - total;
                                    double bud = Double.parseDouble(wbudget.getText().toString());
                                   // int x =0;
                                    try{
                                        if(bud > bal){
                                            Toast.makeText(getActivity().getApplicationContext(), "INSUFFICIENT BALANCE!", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            progressbar();
                                            wbudget.setEnabled(false);
                                        }

                                    }catch (Exception e){

                                    }

                                }catch (Exception e){
                                    Toast.makeText(getActivity().getApplicationContext(), "Empty Field", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });

                    }

                }
                ///ENDDDDDDDDD OF ELSE FOR BUDGET DROPDOWN

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //END OF DROPDOWN



        return view;
    }

    public void progressbar(){
        DBHelper db = new DBHelper(getActivity().getApplicationContext());
        double total = db.getTotalExpenses(getActivity().getIntent().getStringExtra("user"));
        int newtotal = (int)total;
        double bud = Double.parseDouble(wbudget.getText().toString());

        bar = getView().findViewById(R.id.progressBar);
        text = getView().findViewById(R.id.prog_count);
        handler = new Handler();
        bar.setMax((int)bud);

        new Thread(new Runnable() {
            @Override
            public void run() {
                  counter = newtotal;

 //               if(counter != bar.getMax()){
 //                   counter += newtotal;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            bar.setProgress(counter);
                            text.setText(counter +"/"+ bar.getMax());
                        }
                    });
 //               }
            }
        }).start();

    }

}

