package com.example.studyplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.white));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#000000\">" + getString(R.string.app_name) + "</font>",0));
        setStatusBarGradiant(this);

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

    public static void setStatusBarGradiant(Activity activity) {
        Window window = activity.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(activity,R.color.black));
    }
}