package com.example.studyplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.studyplanner.database.AppDatabase;
import com.example.studyplanner.database.Subject;
import com.google.android.material.snackbar.Snackbar;

public class AddSubjectActivity extends AppCompatActivity {

    EditText subjectET;
    EditText teacherET;
    EditText noteET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        subjectET=findViewById(R.id.SubeditText);
        teacherET=findViewById(R.id.teachEditText);
        noteET=findViewById(R.id.noteEditText);
    }
    public void saveAndExit(View v){

        AppDatabase db=AppDatabase.getDbInstance(this.getApplicationContext());

        Subject s=new Subject();
        s.subjectName=subjectET.getText().toString();
        s.teacher=teacherET.getText().toString();
        s.note=noteET.getText().toString();

        if(s.subjectName!=null&&!s.subjectName.equals(""))
        db.userDao().insertSubject(s);

        Snackbar.make(findViewById(R.id.addSubject), R.string.saved_success, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.userDao().deleteSubject(s.subjectName,s.teacher);
                    }
                }).show();

        subjectET.setText("");
        noteET.setText("");
        teacherET.setText("");
    }
}