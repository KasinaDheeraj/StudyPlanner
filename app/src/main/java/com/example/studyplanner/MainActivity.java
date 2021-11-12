package com.example.studyplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.studyplanner.adapters.ViewpagerAdapter;
import com.majeur.cling.Cling;
import com.majeur.cling.ClingManager;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {

    private static final String FOR_FIRST_TIME = "for first time";
    private static final String MY_PREFS = "my_pref";
    ViewPager viewPager;
    AnimatedBottomBar bottomBar;
    HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        viewPager= findViewById(R.id.viewpager);
        FragmentManager fm=getSupportFragmentManager();
        setupViewPager(fm,viewPager);
        bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setupWithViewPager(viewPager);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.white));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#000000\">" + getString(R.string.app_name) + "</font>",0));
        setStatusBarGradiant(this);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS,MODE_PRIVATE);

        if (prefs.getInt(FOR_FIRST_TIME,0)<2)
        {
            showTutorial();
            prefs.edit().putInt(FOR_FIRST_TIME, prefs.getInt(FOR_FIRST_TIME,0)+1).apply();
        }

    }

    private void showTutorial(){
        ClingManager mClingManager = new ClingManager(this);

        mClingManager.addCling(new Cling.Builder(this)
                .setTitle("\n     Welcome to StudyPlanner!     ")
                .setContent("App to plan for Excellence.\n")
                .setMessageBackground(R.color.black)
                .build());

        mClingManager.start();

        mClingManager.setCallbacks(new ClingManager.Callbacks() {
            @Override
            public boolean onClingClick(int position) {
                // We return false to tell to cling manager that we didn't handle this,
                // so it can perform the default action (ie. showing the next Cling).
                // This is the default value returned by super.onClingClick(position), so
                // in a real project, we would just leave this method unoverriden.
                return super.onClingClick(position);
            }

            @Override
            public void onClingShow(int position) {
            }

            @Override
            public void onClingHide(int position) {
                // Last Cling has been shown, tutorial is ended.
                if (position == 0) {
                    homeFragment.showTutorial();
                }
            }
        });

    }

    private void setupViewPager(FragmentManager fm,ViewPager viewPager) {
        ViewpagerAdapter adapter = new ViewpagerAdapter(fm,new String[]{"Home","Home","Home","About"});
        homeFragment=new HomeFragment();
        adapter.addFragment(homeFragment);
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