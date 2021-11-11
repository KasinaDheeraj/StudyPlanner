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

    private String[] days={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    public List<Schedule> tasks;
    Context context;

    public RVTaskAdapter(Context context){
        tasks= AppDatabase.getDbInstance(context.getApplicationContext()).userDao().getTasks();
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

        sub.setText(tasks.get(position).subject);
        note.setText(tasks.get(position).note);

        SimpleDateFormat sdf1=new SimpleDateFormat("dd/MM/YYYY");
        try {
            Date dt = sdf1.parse(tasks.get(position).date);
            SimpleDateFormat sdf2=new SimpleDateFormat("dd MMM YY");
            String d=sdf2.format(dt);
            date.setText(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(tasks.get(position).isSchedule){
            date.setText(days[tasks.get(position).day]);
        }

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cv) {
            super(cv);
            cardView=cv;
        }
    }

    public void removeItem(int position){

        tasks.remove(position);
        notifyItemRemoved(position);
    }

    public void upcomingTasks(){
        tasks= AppDatabase.getDbInstance(context.getApplicationContext()).userDao().getTasks();
        Date curr=new Date();
        String pattern="dd/MM/YYYY";

        SimpleDateFormat sdf= new SimpleDateFormat(pattern);
        tasks.removeIf(s -> {
            try {
                return !(sdf.parse(s.date).before(curr));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return s.status;
        });

        notifyDataSetChanged();

    }

    public void pastTasks(){
        tasks= AppDatabase.getDbInstance(context.getApplicationContext()).userDao().getTasks();
        Date curr=new Date();
        String pattern="dd/MM/YYYY";

        SimpleDateFormat sdf= new SimpleDateFormat(pattern);
        tasks.removeIf(s -> {
            try {
                return (sdf.parse(s.date).before(curr));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;
        });

        notifyDataSetChanged();
    }
}
