package com.example.studyplanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.studyplanner.adapters.ViewpagerAdapter;

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

    }
    private void setupViewPager(FragmentManager fm,ViewPager viewPager) {
        ViewpagerAdapter adapter = new ViewpagerAdapter(fm,new String[]{"Home","Home","Home","About"});

        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new TaskFragment());
        adapter.addFragment(new SubjectFragment());
        adapter.addFragment(new AboutFragment());

        viewPager.setAdapter(adapter);

    }

}