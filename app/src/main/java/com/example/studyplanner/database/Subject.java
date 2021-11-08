package com.example.studyplanner.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Subject {
    @PrimaryKey(autoGenerate = true)
    public int uid=0;

    @ColumnInfo(name="subjectName")
    public String subjectName;

    @ColumnInfo(name="note")
    public String note;

    @ColumnInfo(name="teacher")
    public String teacher;

}

