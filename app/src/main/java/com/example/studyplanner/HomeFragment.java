package com.example.studyplanner;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

    FloatingActionButton add,addSub,addTask;
    TextView addtTV,addsTV;
    boolean isAllFabVisible=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View row=inflater.inflate(R.layout.fragment_home, container, false);

        add=row.findViewById(R.id.fabth);
        addSub=row.findViewById(R.id.fabth_task);
        addTask=row.findViewById(R.id.fabth_sub);
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

        return row;
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
}