package com.example.studyplanner;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.studyplanner.database.AppDatabase;
import com.example.studyplanner.database.Schedule;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    EditText subET,noteET;
    TextView remDate,remTime,taskDate;
    Button dateB,remDateB,remTimeB;
    SwitchCompat rem,ss;
    Spinner spinner;

    private static List<String> days= Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        final Drawable upArrow = this.getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.white));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#000000\">" + getString(R.string.app_name) + "</font>",0));
        setStatusBarGradiant(this);

        subET=findViewById(R.id.task_subET);
        noteET=findViewById(R.id.task_noteET);
        remDate=findViewById(R.id.task_remdate);
        remTime=findViewById(R.id.task_remtime);
        taskDate=findViewById(R.id.task_date);
        dateB=findViewById(R.id.task_dateButton);
        remDateB=findViewById(R.id.task_remdateButton);
        remTimeB=findViewById(R.id.task_remtimeButton);
        //saveB=findViewById(R.id.save_task);
        rem=findViewById(R.id.rem_switch);
        ss=findViewById(R.id.schedule_switch);
        spinner=findViewById(R.id.schedule_spinner);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            subET.setText(extras.getString("subject",""));
            noteET.setText(extras.getString("note",""));
            ss.setChecked(extras.getBoolean("isSchedule",false));
            if(ss.isChecked()){spinner.setVisibility(View.VISIBLE);}
            spinner.setSelection(extras.getInt("day",-1)+1);
        }


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

        rem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(rem.isChecked()){
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
                    taskDate.setText("Time :");
                    dateB.setText("Select Time");
                    dateB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setTime(dateB);
                        }
                    });
                }else{
                    spinner.setVisibility(GONE);
                    taskDate.setText("Date :");
                    dateB.setText("SET DATE");
                    dateB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setDate(dateB);
                        }
                    });
                }
            }
        });
    }

    public void save(){
        AppDatabase db=AppDatabase.getDbInstance(AddTaskActivity.this);
        Schedule s=new Schedule();
        s.subject=subET.getText().toString();
        s.note=noteET.getText().toString();

        String pattern="dd/MM/yyyy";
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
        s.status=ss.isChecked();
        if(s.isSchedule){
            s.day=spinner.getSelectedItemPosition()-1;
            if(s.day==-1){
                Snackbar.make(findViewById(R.id.addTask),"Please select day for schedule.", Snackbar.LENGTH_SHORT).show();
                return;
            }
        }

        if(!dateB.getText().toString().equalsIgnoreCase("set date")|ss.isChecked()&&!dateB.getText().toString().equalsIgnoreCase("select time")){
            s.date=dateB.getText().toString();
            if(!s.subject.equals("") | !s.note.equals("")) {

                db.userDao().insertSchedule(s);

                Snackbar.make(findViewById(R.id.addTask), R.string.saved_success, Snackbar.LENGTH_LONG)
                        .setAction(R.string.undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                db.userDao().deleteScheduleArgs(s.date,s.subject);
                                if (s.isSchedule)
                                    NotificationHelper.cancelAlarm(AddTaskActivity.this);
                                else
                                    NotificationHelper.cancelAlarm(AddTaskActivity.this);
                            }
                        }).show();
                if(rem.isChecked()){setRemainder(s);}
            }else{
                Snackbar.make(findViewById(R.id.addTask), R.string.valid_args, Snackbar.LENGTH_SHORT).show();
            }
        }else{
            Snackbar.make(findViewById(R.id.addTask), R.string.valid_args, Snackbar.LENGTH_SHORT).show();
        }

    }

    private void setRemainder(Schedule s) {
        if(s.isSchedule){
            Calendar calendar=Calendar.getInstance();
            SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
            calendar.set(Calendar.DAY_OF_WEEK,days.indexOf(s.date)+1);
            try {
                Date date=sdf.parse(remTimeB.getText().toString());
                calendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            NotificationHelper.setRepeatingAlarm(this,calendar,"Class Time!!");

        }else{
            Calendar calendar=Calendar.getInstance();

            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
            calendar.set(Calendar.HOUR_OF_DAY,9);
            try {
                Date date=sdf.parse(remDateB.getText().toString());
                calendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            NotificationHelper.setAlarm(this,calendar,"Pending Task! Do before the deadline.");
        }
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
                str += datePicker.getMonth()+1+"/";
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add,menu);
        menu.findItem(R.id.save_menu).setTitle(Html.fromHtml("<font color='#000000'>SAVE</font>",0));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.save_menu){
            save();
        }
        return super.onOptionsItemSelected(item);
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