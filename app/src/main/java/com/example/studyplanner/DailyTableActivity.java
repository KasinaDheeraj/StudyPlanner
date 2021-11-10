package com.example.studyplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.studyplanner.adapters.ViewpagerAdapter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class DailyTableActivity extends AppCompatActivity {

    private String[] days={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    public static final String DAY="";

    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_table);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        FragmentManager fm=getSupportFragmentManager();
        setupViewPager(fm,viewPager);
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewPagerTab);
        viewPagerTab.setViewPager(viewPager);

        int day=getIntent().getIntExtra(DAY,0);
        day=(day>=0&&day<6)?day:0;
        viewPager.setCurrentItem(day);

    }

    private void setupViewPager(FragmentManager fm, ViewPager viewPager) {
        ViewpagerAdapter adapter = new ViewpagerAdapter(fm,days);

        adapter.addFragment(new DayScheduleFragment(0));
        adapter.addFragment(new DayScheduleFragment(1));
        adapter.addFragment(new DayScheduleFragment(2));
        adapter.addFragment(new DayScheduleFragment(3));
        adapter.addFragment(new DayScheduleFragment(4));
        adapter.addFragment(new DayScheduleFragment(5));

        viewPager.setAdapter(adapter);

    }

}