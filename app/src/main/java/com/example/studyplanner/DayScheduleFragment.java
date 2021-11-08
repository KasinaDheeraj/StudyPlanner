package com.example.studyplanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DayScheduleFragment extends Fragment {

    private int DAY;

    private String[] days={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    //sunday-0 TO Saturday-6
    public DayScheduleFragment(int day){
        DAY=day;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View row=inflater.inflate(R.layout.fragment_day_schedule, container, false);


        return row;
    }

}