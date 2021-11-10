package com.example.studyplanner;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {

    Button monday,tuesday,wednesday,thursday,friday,saturday,today;
    FloatingActionButton add,addSub,addTask;
    TextView addtTV,addsTV;
    View row;

    boolean isAllFabVisible=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        row=inflater.inflate(R.layout.fragment_home, container, false);

        add=row.findViewById(R.id.fabth);
        addSub=row.findViewById(R.id.fabth_sub);
        addTask=row.findViewById(R.id.fabth_task);
        addsTV=row.findViewById(R.id.fabth_stv);
        addtTV=row.findViewById(R.id.fabth_ttv);

        addSub.setVisibility(View.GONE);
        addsTV.setVisibility(View.GONE);
        addTask.setVisibility(View.GONE);
        addtTV.setVisibility(View.GONE);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isAllFabVisible){
                    addSub.show();
                    addTask.show();
                    addsTV.setVisibility(View.VISIBLE);
                    addtTV.setVisibility(View.VISIBLE);
                    rotateFab(add,!isAllFabVisible);
                }else{
                    addSub.hide();
                    addTask.hide();
                    addsTV.setVisibility(View.GONE);
                    addtTV.setVisibility(View.GONE);
                    rotateFab(add,!isAllFabVisible);
                }
                isAllFabVisible=!isAllFabVisible;
            }
        });

        addSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),AddSubjectActivity.class);
                startActivity(intent);
            }
        });

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),AddTaskActivity.class);
                startActivity(intent);
            }
        });

        setUpTodayButton();
        setUpDayButtons();

        return row;
    }

    public void setUpTodayButton(){

        today=row.findViewById(R.id.hm_today);

        //For setting string with two diff fonts and colors
        String s= "Today\n ";
        SimpleDateFormat sdf=new SimpleDateFormat("dd MMM YY");
        s+=sdf.format(new Date());
        SpannableString ss1=  new SpannableString(s);
        //UI params for buttons
        int sizeInDP = 24;
        //converting dp to px
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, sizeInDP, getContext().getResources()
                        .getDisplayMetrics());

        ss1.setSpan(new AbsoluteSizeSpan(px), 0,5, 0);
        ss1.setSpan(new AbsoluteSizeSpan((int) (px*0.6)),5,s.length(),0);
        ss1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),R.color.grey_dim)), 5, s.length(), 0);

        today.setText(ss1);

        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getContext(),DailyTableActivity.class);
                intent.putExtra(DailyTableActivity.DAY,new Date().getDay()-1);
                startActivity(intent);
            }
        });
    }

    public void setUpDayButtons(){
        monday=row.findViewById(R.id.mon_home);
        tuesday=row.findViewById(R.id.tues_home);
        wednesday=row.findViewById(R.id.wed_home);
        thursday=row.findViewById(R.id.thur_home);
        friday=row.findViewById(R.id.fri_home);
        saturday=row.findViewById(R.id.sat_home);

        DayButtonClick onclick=new DayButtonClick();
        monday.setOnClickListener(onclick);
        tuesday.setOnClickListener(onclick);
        wednesday.setOnClickListener(onclick);
        thursday.setOnClickListener(onclick);
        friday.setOnClickListener(onclick);
        saturday.setOnClickListener(onclick);
    }

    private void rotateFab(final View v, boolean rotate) {
        v.animate().setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .rotation(rotate ? 135f : 0f);
        return ;
    }

    public class DayButtonClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent =new Intent(getContext(),DailyTableActivity.class);
            if(view==monday){
                intent.putExtra(DailyTableActivity.DAY,0);
            }else if(view==tuesday){
                intent.putExtra(DailyTableActivity.DAY,1);
            }else if(view==wednesday){
                intent.putExtra(DailyTableActivity.DAY,2);
            }else if(view==thursday){
                intent.putExtra(DailyTableActivity.DAY,3);
            }else if(view==friday){
                intent.putExtra(DailyTableActivity.DAY,4);
            }else if(view==saturday){
                intent.putExtra(DailyTableActivity.DAY,5);
            }
            startActivity(intent);
        }
    }
}