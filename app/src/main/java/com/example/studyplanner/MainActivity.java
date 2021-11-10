package com.example.studyplanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;

import com.example.studyplanner.adapters.ViewpagerAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    AnimatedBottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        viewPager=(ViewPager) findViewById(R.id.viewpager);
        FragmentManager fm=getSupportFragmentManager();
        setupViewPager(fm,viewPager);
        bottomBar = (AnimatedBottomBar) findViewById(R.id.bottom_bar);
        bottomBar.setupWithViewPager(viewPager);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.white));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#000000\">" + getString(R.string.app_name) + "</font>",0));
        setStatusBarGradiant(this);
        
    }

    private void setupViewPager(FragmentManager fm,ViewPager viewPager) {
        ViewpagerAdapter adapter = new ViewpagerAdapter(fm,new String[]{"Home","Home","Home","About"});

        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new TaskFragment());
        adapter.addFragment(new SubjectFragment());
        adapter.addFragment(new AboutFragment());

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