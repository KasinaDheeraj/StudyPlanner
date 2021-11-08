package com.example.studyplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studyplanner.adapters.RVSubjectAdapter;
import com.example.studyplanner.database.AppDatabase;
import com.example.studyplanner.database.Subject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class SubjectFragment extends Fragment {

    RVSubjectAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View row=inflater.inflate(R.layout.fragment_subject, container, false);

        //Floating actionbar for adding subjects
        FloatingActionButton fab = row.findViewById(R.id.fabsf);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),AddSubjectActivity.class);
                startActivity(intent);
            }
        });

        //RecyclerView for subjects stored
        RecyclerView recyclerView=row.findViewById(R.id.rv_subject);
        adapter=new RVSubjectAdapter(getContext());
        LinearLayoutManager lm=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
        setUpSwipeRecycler(recyclerView);


        return row;
    }

    public void setUpSwipeRecycler(RecyclerView recyclerView){
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

            @Override
            public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,float dX, float dY,int actionState, boolean isCurrentlyActive){

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(),R.color.green))
                        .addSwipeRightActionIcon(R.drawable.ic_edit)
                        .setSwipeRightActionIconTint(ContextCompat.getColor(getContext(),R.color.white))
                        .addSwipeRightLabel("EDIT")
                        .setSwipeRightLabelColor(ContextCompat.getColor(getContext(),R.color.white))
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.red))
                        .addSwipeLeftActionIcon(R.drawable.ic_delete)
                        .setSwipeLeftActionIconTint(ContextCompat.getColor(getContext(), R.color.white))
                        .addSwipeLeftLabel("DELETE")
                        .setSwipeLeftLabelColor(ContextCompat.getColor(getContext(), R.color.white))
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //Left dirction=4 and Right direction=8
                if(direction==8){
                    //Edit by delete and add
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to edit this subject?\n *Proceeding to edit will delete this entry.")
                            .setTitle("Edit Subject")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    AppDatabase db=AppDatabase.getDbInstance(getContext());
                                    int p=viewHolder.getAbsoluteAdapterPosition();
                                    Subject s=adapter.subjects.get(p);
                                    db.userDao().deleteSubject(s.subjectName,s.teacher);

                                    adapter.removeItem(p);

                                    Intent intent =new Intent(getContext(),AddSubjectActivity.class);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.notifyDataSetChanged();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();



                }else if(direction==4){

                    //Delete from database
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to delete this subject?")
                            .setTitle("Delete Subject")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    AppDatabase db=AppDatabase.getDbInstance(getContext());
                                    int p=viewHolder.getAbsoluteAdapterPosition();
                                    Subject s=adapter.subjects.get(p);
                                    db.userDao().deleteSubject(s.subjectName,s.teacher);

                                    adapter.removeItem(p);
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.notifyDataSetChanged();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

}
