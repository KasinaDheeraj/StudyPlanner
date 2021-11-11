package com.example.studyplanner.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM Schedule WHERE isSchedule= 1")
    List<Schedule> getSchedule();

    @Query("SELECT * FROM Schedule")
    List<Schedule> getTasks();

    @Query("SELECT * FROM Subject")
    List<Subject> getSubjects();


    @Query("SELECT * FROM Schedule WHERE status = :bool")
    List<Schedule> getStatusSchedule(Boolean bool);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSchedule(Schedule... schedules);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSubject(Subject... subjects);

    @Query("DELETE FROM Schedule WHERE uid=:id")
    void deleteSchedule(int id);

    @Query("DELETE FROM Schedule WHERE date=:date AND subject=:subject")
    void deleteScheduleArgs(String date,String subject);

    @Query("DELETE FROM Schedule WHERE uid=:id AND isSchedule= 1")
    void deleteScheduleD(int id);

    @Query("DELETE FROM Subject WHERE uid=:id")
    void deleteSubject(int id);

    @Query("DELETE FROM Subject WHERE subjectName=:subjectName AND teacher=:teacher")
    void deleteSubjectArgs(String subjectName,String teacher);

    @Query("DELETE FROM Subject")
    void clearSubjects();

    @Query("DELETE FROM Schedule")
    void clearSchedules();

}
