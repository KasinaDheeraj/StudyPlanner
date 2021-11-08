package com.example.studyplanner.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM Schedule")
    List<Schedule> getFullSchedule();

    @Query("SELECT * FROM Subject")
    List<Subject> getSubjects();

    @Query("SELECT * FROM Schedule WHERE status = :bool")
    List<Schedule> getStatusSchedule(Boolean bool);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSchedule(Schedule... schedules);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSubject(Subject... subjects);

    @Delete
    void deleteSchedule(Schedule schedule);

    @Query("DELETE FROM Subject WHERE subjectName=:subjectName AND teacher=:teacher")
    void deleteSubject(String subjectName,String teacher);

    @Query("DELETE FROM Subject")
    void clearSubjects();

    @Query("DELETE FROM Schedule")
    void clearSchedules();

}
