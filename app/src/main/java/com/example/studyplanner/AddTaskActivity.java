package com.example.studyplanner;

import static android.view.View.GONE;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlarmManager;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.studyplanner.database.AppDatabase;
import com.example.studyplanner.database.Schedule;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        Button saveB=findViewById(R.id.save_task);
        SwitchCompat st=findViewById(R.id.task_switch);
        SwitchCompat ss=findViewById(R.id.schedule_switch);
        Spinner spinner=findViewById(R.id.schedule_spinner);

        saveB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AppDatabase db=AppDatabase.getDbInstance(AddTaskActivity.this);
                Schedule s=new Schedule();
                s.subject=subET.getText().toString();
                s.note=noteET.getText().toString();

                String pattern="dd/MM/YYYY";
                if (!dateB.getText().toString().equalsIgnoreCase("set date")) {
                    SimpleDateFormat sdf=new SimpleDateFormat(pattern);
                    try {
                        Date date=sdf.parse(dateB.getText().toString());
                        Date curr=new Date();
                        if(curr.after(date)){
                            s.status=true;
                        }else{
                            s.status=false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                s.isSchedule=ss.isChecked();
                if(s.isSchedule){
                    s.day=spinner.getSelectedItemPosition()-1;
                    if(s.day==-1){
                        Snackbar.make(findViewById(R.id.addTask),"Please select day for schedule.", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                }

                if(!dateB.getText().toString().equalsIgnoreCase("set date")|ss.isChecked()){
                    s.date=dateB.getText().toString();
                    if(!s.subject.equals("") | !s.note.equals("")) {

                        db.userDao().insertSchedule(s);

                        Snackbar.make(findViewById(R.id.addTask), R.string.saved_success, Snackbar.LENGTH_LONG)
                                .setAction(R.string.undo, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        db.userDao().deleteSchedule(s.date,s.subject);
                                    }
                                }).show();
                    }else{
                        Snackbar.make(findViewById(R.id.addTask), R.string.valid_args, Snackbar.LENGTH_SHORT).show();
                    }
                }else{
                    Snackbar.make(findViewById(R.id.addTask), R.string.valid_args, Snackbar.LENGTH_SHORT).show();
                }

            }
        });

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

        st.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(st.isChecked()){
                    remDate.setVisibility(View.VISIBLE);
                    remTime.setVisibility(View.VISIBLE);
                    remDateB.setVisibility(View.VISIBLE);
                    remTimeB.setVisibility(View.VISIBLE);
                }else{
                    remDate.setVisibility(GONE);
                    remTime.setVisibility(GONE);
                    remDateB.setVisibility(GONE);
                    remTimeB.setVisibility(GONE);
                }
            }
        });

        ss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(ss.isChecked()){
                    spinner.setVisibility(View.VISIBLE);
                }else{
                    spinner.setVisibility(GONE);
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