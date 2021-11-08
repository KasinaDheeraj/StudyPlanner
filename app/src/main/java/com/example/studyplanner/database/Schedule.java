package com.example.studyplanner.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Schedule {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name="date")
    public String date;

    @ColumnInfo(name="subject")
    public String subject;

    @ColumnInfo(name="note")
    public String note;

    @ColumnInfo(name="status")
    public boolean status;

}
