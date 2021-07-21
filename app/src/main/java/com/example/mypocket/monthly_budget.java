package com.example.mypocket;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class monthly_budget extends Fragment {
    String bisagunsa;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monthly_budget, container, false);


       // bisagunsa = ((budget)getParentFragment()).weeklimit;
        TextView wlimit = view.findViewById(R.id.weekly_limit);
       // wlimit.setText(bisagunsa);

        //String bisagunsa1 = ((budget)getParentFragment()).Dailylimit;
        TextView dlimit = view.findViewById(R.id.daily_limit);
       // dlimit.setText(bisagunsa1);

        SharedPreferences shared = getActivity().getSharedPreferences("data", 0);
        if(shared.contains("mweek"))
            wlimit.setText(shared.getString("mweek",""));
        if(shared.contains("mdaily"))
            dlimit.setText(shared.getString("mdaily",""));

        return view;
    }

}