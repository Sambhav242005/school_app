package com.surana.myschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.surana.myschool.item.ItemClassCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class AddClassActivity extends AppCompatActivity {

    private static final String TAG = "My App";
    EditText mClassName;
    FirebaseUser mUser;
    Button btnSubmit,btnBack;
    DatabaseReference mRef;
    ProgressBar progressBar;
    LinearLayout main_layout;
    TextView mError;
    int check = 0;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        mError = findViewById(R.id.error_add_class);
        progressBar = findViewById(R.id.progress_bar_add_class);
        main_layout = findViewById(R.id.main_add_class);
        mClassName = findViewById(R.id.add_class_name);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        btnSubmit = findViewById(R.id.add_class_submit);
        btnBack = findViewById(R.id.add_class_back);
        mRef = FirebaseDatabase.getInstance().getReference();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddClassActivity.this,MainActivity.class));
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mClassName.getText().toString())){
                    progressBar.setVisibility(View.VISIBLE);
                    main_layout.setVisibility(View.GONE);
                    progressBar.setProgress(0);
                    mRef.child("class_name").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange( DataSnapshot snapshot) {
                            check = 0;
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                String className = dataSnapshot.child("class_name").getValue().toString().replaceAll(" ","").toLowerCase();
                                String compareClassName = mClassName.getText().toString().replaceAll(" ","").toLowerCase();
                                if (compareClassName.equals(className)){
                                    check++;
                                }
                                Log.d(TAG,className);
                                Log.d(TAG, String.valueOf(check));
                                Log.d(TAG,compareClassName);
                                progressBar.setProgress(100);
                                progressBar.setVisibility(View.GONE);
                                main_layout.setVisibility(View.VISIBLE);
                                mError.setVisibility(View.GONE);
                            }
                            if (check==0){
                                addClass();
                            }else {
                                mError.setVisibility(View.VISIBLE);
                                mError.setText("Already Exit Class Name");
                            }
                        }

                        @Override
                        public void onCancelled( DatabaseError error) {

                        }
                    });
                }
            }
        });

        mRef.child("users").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                username = snapshot.child("username").getValue().toString();
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });

    }
    protected String getSaltString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    private void addClass() {
        String uid = mUser.getUid();
        String email =mUser.getEmail();
        String className = mClassName.getText().toString();
        String token = getSaltString(20);

        Map<String,String> hashMap = new HashMap<>();

        hashMap.put("class_name",className);
        hashMap.put("uid_create_by",uid);
        hashMap.put("create_by",username);
        hashMap.put("token",token);

        mRef.child("class_name").child(token).setValue(hashMap);
        mRef.child("class_name").child(token).child("list_users").child(mUser.getUid()).setValue("Add");

        startActivity(new Intent(AddClassActivity.this,MainActivity.class));
        finish();
    }
}