package com.example.studyplanner;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.studyplanner.database.AppDatabase;
import com.example.studyplanner.database.Schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotificationHelper {

    private static List<Schedule> Tasks=new ArrayList<>();
    private static List<Schedule> Repeating=new ArrayList<>();
    private static final long WEEK_IN_MILLIS=604800000;
    private static List<String> days= Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");

    public static void setRepeatingAlarm(Context context,Calendar calendar,String msg) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.MESSAGE,msg);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),WEEK_IN_MILLIS, pendingIntent);
    }

    public static void cancelRepeatingAlarm(Context context){
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        new MySAsyncTask().execute(context);

    }

    public static void setAlarm(Context context,Calendar calendar,String msg) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.MESSAGE,msg);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
    }


    public static void cancelAlarm(Context context){
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        new MyTAsyncTask().execute(context);

    }

    private static class MySAsyncTask extends AsyncTask<Context,Void,Void>{

        @Override
        protected Void doInBackground(Context... contexts) {

            Tasks=AppDatabase.getDbInstance(contexts[0]).userDao().getTasks();
            Repeating=AppDatabase.getDbInstance(contexts[0]).userDao().getSchedule();
            Calendar calendar=Calendar.getInstance();

            SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
            for(Schedule s:Repeating){
                calendar.set(Calendar.DAY_OF_WEEK,days.indexOf(s.date)+1);
                try {
                    Date date=sdf.parse(s.date);
                    calendar.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                setRepeatingAlarm(contexts[0],calendar,"Class Time!!");
            }

            return null;
        }
    }
    private static class MyTAsyncTask extends AsyncTask<Context,Void,Void>{

        @Override
        protected Void doInBackground(Context... contexts) {
            Tasks=AppDatabase.getDbInstance(contexts[0]).userDao().getTasks();
            Repeating=AppDatabase.getDbInstance(contexts[0]).userDao().getSchedule();
            Calendar calendar=Calendar.getInstance();

            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
            for(Schedule s:Tasks){
                calendar.set(Calendar.HOUR_OF_DAY,9);
                try {
                    Date date=sdf.parse(s.date);
                    calendar.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                setAlarm(contexts[0],calendar,"Pending Task! Do before the deadline.");
            }

            return null;
        }
    }
}
