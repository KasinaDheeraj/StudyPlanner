package com.example.studyplanner.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyplanner.R;
import com.example.studyplanner.database.AppDatabase;
import com.example.studyplanner.database.Subject;

import java.util.List;

public class RVSubjectAdapter extends RecyclerView.Adapter<RVSubjectAdapter.ViewHolder> {

    public List<Subject> subjects;

    public RVSubjectAdapter(Context context){

        subjects= AppDatabase.getDbInstance(context.getApplicationContext()).userDao().getSubjects();

    }

    @NonNull
    @Override
    public RVSubjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv= (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_subject,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull RVSubjectAdapter.ViewHolder holder, int position) {
        CardView cv= holder.cardView;
        TextView sub=cv.findViewById(R.id.sub_cvsub);
        TextView teach=cv.findViewById(R.id.teach_cvsub);
        TextView note=cv.findViewById(R.id.note_cvsub);

        sub.setText  ("Subject : "+subjects.get(position).subjectName);
        teach.setText("Teacher : "+subjects.get(position).teacher);
        if(subjects.get(position).note!=null&&!subjects.get(position).note.equals(""))
            note.setText ("Note    : "+subjects.get(position).note);

    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cv) {
            super(cv);
            cardView=cv;
        }
    }

    public void removeItem(int position){

        subjects.remove(position);
        notifyItemRemoved(position);
    }
}
