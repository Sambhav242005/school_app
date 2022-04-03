package com.surana.myschool;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddUsersActivity extends AppCompatActivity {

    private static final String TAG = "Add Users";
    String select = "student";
    Button btn_student,btn_teacher,btn_submit;
    LinearLayout student_layout,teacher_layout;
    EditText mEditStudentName,mEditStudentRollNo,mEditStudentPassword;
    EditText mEditTeacherName,mEditTeacherEmail,mEditTeacherPassword;
    TextView mError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);

        student_layout = findViewById(R.id.student_layout_add_users);
        teacher_layout = findViewById(R.id.teacher_layout_add_users);
        btn_student = findViewById(R.id.btn_student_add_users);
        btn_teacher = findViewById(R.id.btn_teacher_add_users);
        btn_submit = findViewById(R.id.submit_add_users);
        mError = findViewById(R.id.add_users_errorView);

        mEditStudentName = findViewById(R.id.student_activity_name);
        mEditStudentRollNo = findViewById(R.id.student_activity_roll_no);
        mEditStudentPassword = findViewById(R.id.student_activity_password);

        mEditTeacherName = findViewById(R.id.teacher_activity_name);
        mEditTeacherEmail = findViewById(R.id.teacher_activity_email);
        mEditTeacherPassword = findViewById(R.id.teacher_activity_password);

        if (select.equals("student")){
            select_student();
        }

        btn_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select = "student";
                select_student();
            }
        });

        btn_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select = "teacher";
                select_teacher();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (select.equals("student")){

                    if (!TextUtils.isEmpty(mEditStudentName.getText().toString())
                            && !TextUtils.isEmpty(mEditStudentRollNo.getText().toString())
                            && !TextUtils.isEmpty(mEditStudentPassword.getText().toString())){

                        Log.d(TAG,"Student");

                        mError.setVisibility(View.GONE);
                    }else {
                        mError.setVisibility(View.VISIBLE);
                        mError.setText("Enter All Detail");
                    }

                }else  if (select.equals("teacher")){
                    if (!TextUtils.isEmpty(mEditTeacherName.getText().toString())
                            && !TextUtils.isEmpty(mEditTeacherEmail.getText().toString())
                            && !TextUtils.isEmpty(mEditTeacherPassword.getText().toString())){
                        if (!isEmailValid(mEditTeacherEmail.getText().toString())){
                            Log.d(TAG, "Teacher");

                            mError.setVisibility(View.GONE);

                        }else {
                            mError.setVisibility(View.VISIBLE);
                            mError.setText("Invalid Email Address");
                        }
                    }else{
                        mError.setVisibility(View.VISIBLE);
                        mError.setText("Enter All Detail");
                    }
                }
            }
        });

    }

    private void select_student() {
        teacher_layout.setVisibility(View.GONE);
        student_layout.setVisibility(View.VISIBLE);

        btn_student.setBackgroundColor(Color.parseColor("#000000"));
        btn_student.setTextColor(Color.parseColor("#ffffff"));

        btn_teacher.setTextColor(Color.parseColor("#000000"));
        btn_teacher.setBackgroundColor(Color.parseColor("#ffffff"));
    }
    private void select_teacher() {
        teacher_layout.setVisibility(View.VISIBLE);
        student_layout.setVisibility(View.GONE);

        btn_teacher.setBackgroundColor(Color.parseColor("#000000"));
        btn_teacher.setTextColor(Color.parseColor("#ffffff"));

        btn_student.setTextColor(Color.parseColor("#000000"));
        btn_student.setBackgroundColor(Color.parseColor("#ffffff"));
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}