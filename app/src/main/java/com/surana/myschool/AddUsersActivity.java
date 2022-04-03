package com.surana.myschool;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.surana.myschool.R;

import java.util.HashMap;
import java.util.Map;

public class AddUsersActivity extends AppCompatActivity {

    private static final String TAG = "Add Users";
    String select = "student";
    Button btn_student,btn_teacher,btn_submit,btn_selectClass,btn_done;
    LinearLayout student_layout,teacher_layout,main_layout,select_layout;
    EditText mEditStudentName,mEditStudentRollNo,mEditStudentPassword;
    EditText mEditTeacherName,mEditTeacherEmail,mEditTeacherPassword;
    TextView mError;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);

        btn_done = findViewById(R.id.done_add_users);
        main_layout = findViewById(R.id.add_users_main);
        select_layout = findViewById(R.id.select_class_layout);
        btn_selectClass = findViewById(R.id.btn_select_class_add_users);
        student_layout = findViewById(R.id.student_layout_add_users);
        teacher_layout = findViewById(R.id.teacher_layout_add_users);
        btn_student = findViewById(R.id.btn_student_add_users);
        btn_teacher = findViewById(R.id.btn_teacher_add_users);
        btn_submit = findViewById(R.id.submit_add_users);
        mError = findViewById(R.id.add_users_errorView);
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference().child("users");
        mUser = mAuth.getCurrentUser();

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
                mError.setVisibility(View.GONE);
            }
        });

        btn_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select = "teacher";
                select_teacher();
                mError.setVisibility(View.GONE);
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

                        addStudentAccount();

                        mError.setVisibility(View.GONE);
                    }else {
                        mError.setVisibility(View.VISIBLE);
                        mError.setText("Enter All Detail");
                    }

                }else  if (select.equals("teacher")){
                    if (!TextUtils.isEmpty(mEditTeacherName.getText().toString())
                            && !TextUtils.isEmpty(mEditTeacherEmail.getText().toString())
                            && !TextUtils.isEmpty(mEditTeacherPassword.getText().toString())){

                            addTeacherAccount();
                            mError.setVisibility(View.GONE);
                    }else{
                        mError.setVisibility(View.VISIBLE);
                        mError.setText("Enter All Detail");
                    }
                }
            }
        });

        btn_selectClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_layout.setVisibility(View.GONE);
                select_layout.setVisibility(View.VISIBLE);
            }
        });
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_layout.setVisibility(View.GONE);
                main_layout.setVisibility(View.VISIBLE);
            }
        });

    }

    private void addStudentAccount() {
        // Add Student
    }

    private void addTeacherAccount() {
        String email = mEditTeacherEmail.getText().toString();
        String password = mEditTeacherPassword.getText().toString();
        String username = mEditTeacherName.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Map<String,String> hashMap = new HashMap<>();

                hashMap.put("email",email);
                hashMap.put("username",username);
                hashMap.put("type",select);
                hashMap.put("img_src","null");
                hashMap.put("create_by", mUser.getUid());
                hashMap.put("token", authResult.getUser().getUid());

                mRef.child(authResult.getUser().getUid()).setValue(hashMap);

                mError.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mError.setVisibility(View.VISIBLE);
                mError.setText(e.getMessage());
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
}