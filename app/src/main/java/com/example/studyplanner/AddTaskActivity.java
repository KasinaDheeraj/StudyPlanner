package com.example.studyplanner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText subET=findViewById(R.id.task_subET);
        EditText noteET=findViewById(R.id.task_noteET);
        TextView remDate=findViewById(R.id.task_remdate);
        TextView remTime=findViewById(R.id.task_remtime);
        Button dateB=findViewById(R.id.task_dateButton);
        Button remDateB=findViewById(R.id.task_remdateButton);
        Button remTimeB=findViewById(R.id.task_remtimeButton);

        remDateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(remDateB);
            }
        });

        dateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(dateB);
            }
        });

        remTimeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(remTimeB);
            }
        });

        SwitchCompat s=findViewById(R.id.task_switch);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(s.isChecked()){
                    remDate.setVisibility(View.VISIBLE);
                    remTime.setVisibility(View.VISIBLE);
                    remDateB.setVisibility(View.VISIBLE);
                    remTimeB.setVisibility(View.VISIBLE);
                }else{
                    remDate.setVisibility(View.GONE);
                    remTime.setVisibility(View.GONE);
                    remDateB.setVisibility(View.GONE);
                    remTimeB.setVisibility(View.GONE);
                }
            }
        });
    }


    public void setDate(Button b){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialogdate, null);
        dialogBuilder.setView(dialogView);

        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);
        java.util.Calendar calendar = java.util.Calendar.getInstance();

        dialogBuilder.setTitle("Select Date");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(DialogInterface dialog, int whichButton)
            {
                String str="";
                str += datePicker.getDayOfMonth()+"/";
                str += datePicker.getMonth()+"/";
                str += datePicker.getYear();
                b.setText(str);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {

            }
        });
        AlertDialog ad = dialogBuilder.create();
        ad.show();
    }

    public void setTime(Button b){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialogtime, null);
        dialogBuilder.setView(dialogView);

        final TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.timePicker);

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        timePicker.setMinute(min);
        timePicker.setHour(hour);

        dialogBuilder.setTitle("Select Time");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                int hour = timePicker.getHour();
                int min = timePicker.getMinute();
                b.setText(hour+":"+min);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {

            }
        });
        AlertDialog ad = dialogBuilder.create();
        ad.show();
    }
}