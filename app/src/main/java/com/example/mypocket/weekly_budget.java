package com.example.mypocket;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class weekly_budget extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weekly_budget, container, false);


        //String bisagunsa2 = ((budget)getParentFragment()).dlimit;
        TextView wb = view.findViewById(R.id.weekdl);
        //weekbud.setText(bisagunsa2);

        SharedPreferences s = getActivity().getSharedPreferences("weekdata", 0);
        if(s.contains("week"))
            wb.setText(s.getString("week",""));

        return view;
    }
}