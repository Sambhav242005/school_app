package com.surana.myschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class StartActivity extends AppCompatActivity {

    FirebaseUser mUser;
    Button btn_studentActivity;
    Button btn_teacherActivity;
    Button btn_submit;
    EditText mEmail,mPassword;
    String select ="student";
    FirebaseAuth mAuth;
    TextView mError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mAuth = FirebaseAuth.getInstance();
        btn_submit = findViewById(R.id.login_submit);
        mUser = mAuth.getCurrentUser();
        mEmail = findViewById(R.id.email_login);
        mPassword = findViewById(R.id.password_login);
        btn_studentActivity = findViewById(R.id.student_activity);
        btn_teacherActivity = findViewById(R.id.teacher_activity);
        mError = findViewById(R.id.error_login);

        if (mUser != null){
            startActivity(new Intent(StartActivity.this,MainActivity.class));
            finish();
        }

        if (select.equals("student")){
            btn_studentActivity.setBackgroundColor(Color.parseColor("#000000"));
            btn_studentActivity.setTextColor(Color.parseColor("#ffffff"));
            btn_teacherActivity.setBackgroundColor(Color.parseColor("#ffffff"));
            btn_teacherActivity.setTextColor(Color.parseColor("#000000"));
            mEmail.setHint("Roll No");
        }

        btn_teacherActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select ="teacher";
                btn_teacherActivity.setBackgroundColor(Color.parseColor("#000000"));
                btn_teacherActivity.setTextColor(Color.parseColor("#ffffff"));
                btn_studentActivity.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_studentActivity.setTextColor(Color.parseColor("#000000"));
                mEmail.setHint("Id");
            }
        });
        btn_studentActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select ="student";
                btn_studentActivity.setBackgroundColor(Color.parseColor("#000000"));
                btn_studentActivity.setTextColor(Color.parseColor("#ffffff"));
                btn_teacherActivity.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_teacherActivity.setTextColor(Color.parseColor("#000000"));
                mEmail.setHint("Roll No");
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        mError.setVisibility(View.GONE);

    }

    private void submit() {
        String email = mEmail.getText().toString()+"@gmail.com";
        String password = mPassword.getText().toString();

        if (!TextUtils.isEmpty(mEmail.getText().toString()) && !TextUtils.isEmpty(mPassword.getText().toString())){
            mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    mUser = authResult.getUser();

                    startActivity(new Intent(StartActivity.this,MainActivity.class));
                    finish();

                    mError.setVisibility(View.GONE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {

                    mError.setVisibility(View.VISIBLE);
                    mError.setText(e.getMessage());
                }
            });
        }

    }
}