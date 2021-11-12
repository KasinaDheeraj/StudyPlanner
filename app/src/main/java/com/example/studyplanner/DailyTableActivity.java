package com.example.studyplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;

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

        final Drawable upArrow = this.getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.white));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#000000\">" + getString(R.string.app_name) + "</font>",0));
        setStatusBarGradiant(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
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

    public static void setStatusBarGradiant(Activity activity) {
        Window window = activity.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(activity,R.color.black));
    }

}