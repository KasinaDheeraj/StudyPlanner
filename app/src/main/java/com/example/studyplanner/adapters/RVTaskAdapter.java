package com.example.studyplanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyplanner.R;
import com.example.studyplanner.database.AppDatabase;
import com.example.studyplanner.database.Schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RVTaskAdapter extends RecyclerView.Adapter<RVTaskAdapter.ViewHolder> {

    public List<Schedule> schedules;
    Context context;

    public RVTaskAdapter(Context context){
        schedules= AppDatabase.getDbInstance(context.getApplicationContext()).userDao().getTasks();
        this.context=context;
    }

    @NonNull
    @Override
    public RVTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv= (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_task,parent,false);
        return new RVTaskAdapter.ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull RVTaskAdapter.ViewHolder holder, int position) {
        CardView cv= holder.cardView;
        TextView sub=cv.findViewById(R.id.sub_cvtask);
        TextView note=cv.findViewById(R.id.note_cvtask);
        TextView date=cv.findViewById(R.id.time_cvtask);

        sub.setText(schedules.get(position).subject);
        note.setText(schedules.get(position).note);

        SimpleDateFormat sdf1=new SimpleDateFormat("dd/MM/YYYY");
        Date dt= null;
        try {
            dt = sdf1.parse(schedules.get(position).date);
            SimpleDateFormat sdf2=new SimpleDateFormat("dd MMM YY");
            String d=sdf2.format(dt);
            date.setText(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cv) {
            super(cv);
            cardView=cv;
        }
    }

    public void removeItem(int position){

        schedules.remove(position);
        notifyItemRemoved(position);
    }

    public void upcomingTasks(){
        schedules= AppDatabase.getDbInstance(context.getApplicationContext()).userDao().getTasks();
        Date curr=new Date();
        String pattern="dd/MM/YYYY";

        SimpleDateFormat sdf= new SimpleDateFormat(pattern);
        schedules.removeIf(s -> {
            try {
                return !(sdf.parse(s.date).before(curr));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;
        });

        notifyDataSetChanged();

    }

    public void pastTasks(){
        schedules= AppDatabase.getDbInstance(context.getApplicationContext()).userDao().getTasks();
        Date curr=new Date();
        String pattern="dd/MM/YYYY";

        SimpleDateFormat sdf= new SimpleDateFormat(pattern);
        schedules.removeIf(s -> {
            try {
                return (sdf.parse(s.date).before(curr));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;
        });

        notifyDataSetChanged();
    }

    public void onlySchedules(int day){
        schedules= AppDatabase.getDbInstance(context.getApplicationContext()).userDao().getTasks();
        schedules.removeIf(s -> {return !s.isSchedule|!(s.day==day);});
        notifyDataSetChanged();
    }
}
