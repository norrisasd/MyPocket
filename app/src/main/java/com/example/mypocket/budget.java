package com.example.mypocket;

import android.content.Context;
import android.content.SharedPreferences;
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

    public String weeklimit ;
    public String Dailylimit;
    public String dlimit = "";
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    FragmentTransaction ft;
    EditText wbudget;

    ProgressBar bar;
    int counter = 0;
    TextView text;
    Handler handler;

    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    SharedPreferences s;
    SharedPreferences.Editor weekedit;
    public budget(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        ImageButton bobutton = (ImageButton) view.findViewById(R.id.budgetok_button);
        wbudget = view.findViewById(R.id.weekbud);
        ImageButton editbutton = view.findViewById(R.id.edit_button);
        text = view.findViewById(R.id.prog_count);
        bar = view.findViewById(R.id.progressBar);

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wbudget.setEnabled(true);
            }
        });

        month();
        week();

        prefs = getActivity().getSharedPreferences("data", 0);
        edit = prefs.edit();
        if(prefs.contains("amount"))
            wbudget.setText(String.valueOf((prefs.getFloat("amount", 0.00f))));
        if(prefs.contains("mweek"))
            weeklimit = prefs.getString("mweek","");
        if(prefs.contains("mdaily"))
            Dailylimit = prefs.getString("mdaily","");
        if(prefs.contains("prog"))
            text.setText((prefs.getString("prog","")));
        if(prefs.contains("pbar"))
            bar.setProgress(prefs.getInt("pbar", 0));
        if (prefs.contains("maxx"))
            bar.setMax(prefs.getInt("maxx",0));



//        if(prefs.contains("text"))
//        {
//            t = prefs.getString("prog", "");
////            if(prefs.contains("text"))
//            text.setText(prefs.getString("prog", ""));
//        }

        s = getActivity().getSharedPreferences("weekdata", 0);
        weekedit = s.edit();
        if(s.contains("week"))
            dlimit =s.getString("week","");



        Spinner period = view.findViewById(R.id.period_spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.selectperiod));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        period.setAdapter(myAdapter);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("FileName",Context.MODE_PRIVATE);
        int spinnerValue = sharedPref.getInt("userChoiceSpinner",-1);
        if(spinnerValue != -1) {
            // set the selected value of the spinner
           period.setSelection(spinnerValue);
        }

        period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int userChoice = period.getSelectedItemPosition();
                SharedPreferences sharedPref = getActivity().getSharedPreferences("FileName",0);
                SharedPreferences.Editor prefEditor = sharedPref.edit();
                prefEditor.putInt("userChoiceSpinner",userChoice);
                prefEditor.commit();

                if(parent.getItemAtPosition(position).equals("Select budget")){

                    ft = getChildFragmentManager().beginTransaction();
                    Fragment f = getChildFragmentManager().findFragmentByTag("monthly");
                    Fragment f1 = getChildFragmentManager().findFragmentByTag("weekly");
                    if(f != null ){
                        edit.remove("m").apply();
                        ft.remove(f);
                        ft.commit();
                    }
                    else if(f1 != null){
                        edit.remove("w").apply();
                        ft.remove(f1);
                        ft.commit();
                    }
                    clear();
                    clearprog();

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
                        progressbar();

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

                                            ft = getChildFragmentManager().beginTransaction();
                                            monthly_budget month = new monthly_budget();
                                            ft.add(R.id.limit_fragment,month,"monthly");
                                            ft.commit();

                                            edit.putBoolean("m",true);
                                            edit.apply();


                                            savedata();

                                            double weeklylimit = bud/4;
                                            double dailylimit = bud/31;
                                            weeklimit = df2.format(weeklylimit);
                                            Dailylimit = df2.format(dailylimit);

                                            edit.putString("mweek",weeklimit);
                                            edit.putString("mdaily",Dailylimit);
                                            edit.apply();
                                            progressbar();
                                            wbudget.setEnabled(false);

                                    }

                                    else{
                                        clearprog();
                                        ft = getChildFragmentManager().beginTransaction();
                                        f = getChildFragmentManager().findFragmentByTag("monthly");
                                        if(f != null){
                                            edit.remove("amount").apply();
                                            edit.remove("m").apply();
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

                        progressbar();

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

                                    if (bud <= bal && bud > 0) {

                                        ft = getChildFragmentManager().beginTransaction();

                                        if(f != null){
                                            ft.remove(f);
                                            ft.commit();
                                        }

                                        ft = getChildFragmentManager().beginTransaction();
                                        weekly_budget week = new weekly_budget();
                                        ft.add(R.id.limit_fragment,week,"weekly");
                                        ft.commit();

                                        edit.putBoolean("w",true);
                                        edit.apply();

                                        savedata();

                                        double dl = bud/7;
                                        dlimit = df2.format(dl);

                                        weekedit.putString("week",dlimit);
                                        weekedit.apply();
                                        progressbar();
                                        wbudget.setEnabled(false);

                                    }

                                    else{
                                        clearprog();
                                        ft = getChildFragmentManager().beginTransaction();
                                        f = getChildFragmentManager().findFragmentByTag("weekly");
                                        if(f != null){
                                            edit.remove("amount").apply();
                                            edit.remove("w").apply();
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
                            edit.remove("m").apply();
                            ft.remove(f);
                            ft.commit();
                        }
                        else if(f1 != null){
                            edit.remove("w").apply();
                            ft.remove(f1);
                            ft.commit();
                        }

                        progressbar();

                        bobutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try{

                                    DBHelper db = new DBHelper(getActivity().getApplicationContext());
                                    double total = db.getTotalExpenses(getActivity().getIntent().getStringExtra("user"));
                                    double totalinc = db.getTotalIncome(getActivity().getIntent().getStringExtra("user"));
                                    double bal = totalinc - total;
                                    double bud = Double.parseDouble(wbudget.getText().toString());

                                    try{
                                        if(bud > bal || bud == 0){
                                            Toast.makeText(getActivity().getApplicationContext(), "INSUFFICIENT BALANCE!", Toast.LENGTH_SHORT).show();
                                            edit.remove("amount").apply();
                                            clearprog();
                                        }
                                        else{
                                            savedata();
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


    public void savedata(){
        edit.putFloat("amount", Float.parseFloat(wbudget.getText().toString()));
        edit.apply();
    }
    public void clear(){
        wbudget.setText("");
    }

    public void month(){
        SharedPreferences prefs = getActivity().getSharedPreferences("data", 0);
        boolean frag = prefs.getBoolean("m", false);
        try{
            if(frag)
            {
                ft = getChildFragmentManager().beginTransaction();
                monthly_budget month = new monthly_budget();
                ft.add(R.id.limit_fragment,month,"monthly");
                ft.commit();
            }
        }
        catch(Exception e){

        }
    }

    public void week(){
        SharedPreferences prefs = getActivity().getSharedPreferences("data", 0);
        boolean frag = prefs.getBoolean("w", false);
        try{
            if(frag)
            {
                ft = getChildFragmentManager().beginTransaction();
                weekly_budget week = new weekly_budget();
                ft.add(R.id.limit_fragment,week,"weekly");
                ft.commit();

            }
        }
        catch(Exception e){

        }
    }

    public void progressbar(){
        try{
            DBHelper db = new DBHelper(getActivity().getApplicationContext());
            double total = db.getTotalExpenses(getActivity().getIntent().getStringExtra("user"));
            double totalinc = db.getTotalIncome(getActivity().getIntent().getStringExtra("user"));
            double bal = totalinc -total;
            //int newbal = (int)bal;
            int newtotal = (int)total;
            double bud = Double.parseDouble(wbudget.getText().toString());

            if(bud <= bal){

                handler = new Handler();
                bar.setMax((int)bud);
                edit.putInt("maxx", (int)bud).apply();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        counter = newtotal;
                        edit.putInt("pbar",counter).apply();
                        //               if(counter != bar.getMax()){
                        //                   counter += newtotal;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                bar.setProgress(counter);
                                text.setText(counter +"/"+ bar.getMax());
                                edit.putString("prog", text.getText().toString()).apply();

                            }
                        });
                        //               }

                    }
                }).start();
            }
            else
                clearprog();

        }catch (Exception e){

        }
    }

    public void clearprog(){
        counter = 0;
        bar.setMax(0);
        bar.setProgress(0);
        text.setText(counter +"/"+ bar.getMax());
        edit.remove("pbar");
        edit.remove("maxx");
        edit.remove("prog");
        edit.apply();
    }

}


