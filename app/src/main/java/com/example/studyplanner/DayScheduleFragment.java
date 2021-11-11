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

import com.example.studyplanner.adapters.RVScheduleAdapter;
import com.example.studyplanner.adapters.RVTaskAdapter;
import com.example.studyplanner.database.AppDatabase;
import com.example.studyplanner.database.Schedule;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class DayScheduleFragment extends Fragment {

    RVScheduleAdapter adapter;

    private int DAY;

    public static String[] days={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    public DayScheduleFragment(int day){
        DAY=day;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View row=inflater.inflate(R.layout.fragment_day_schedule, container, false);

        RecyclerView recyclerView=row.findViewById(R.id.rv_day_schedule);
        adapter=new RVScheduleAdapter(getContext());
        adapter.onlySchedules(DAY);
        LinearLayoutManager lm=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
        setUpSwipeRecycler(recyclerView);

        return row;
    }

    public void setUpSwipeRecycler(RecyclerView recyclerView){
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

            @Override
            public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

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
                    builder.setMessage("Are you sure you want to edit this Schedule?\n *Proceeding to edit will delete this entry.")
                            .setTitle("Edit Schedule")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    AppDatabase db=AppDatabase.getDbInstance(getContext());
                                    int p=viewHolder.getAbsoluteAdapterPosition();
                                    Schedule s=adapter.schedules.get(p);

                                    db.userDao().deleteScheduleD(s.uid);

                                    adapter.removeItem(p);

                                    Intent intent =new Intent(getContext(),AddTaskActivity.class);
                                    Bundle extras = new Bundle();
                                    extras.putBoolean("isSchedule",s.isSchedule);
                                    extras.putString("subject",s.subject);
                                    extras.putString("note",s.note);
                                    extras.putInt("day",s.day);
                                    intent.putExtras(extras);
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
                    builder.setMessage("Are you sure you want to delete this schedule?")
                            .setTitle("Delete Schedule")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AppDatabase db=AppDatabase.getDbInstance(getContext());
                                    int p=viewHolder.getAbsoluteAdapterPosition();
                                    Schedule s=adapter.schedules.get(p);

                                    db.userDao().deleteScheduleD(s.uid);

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