package com.surana.myschool;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.surana.myschool.adpter.AdapterUsers;
import com.surana.myschool.item.ItemUsers;

import java.util.ArrayList;
import java.util.Random;

public class AddUsersInClassActivity extends AppCompatActivity {


    private static final String TAG = "My App";
    ImageButton btn_back;
    RecyclerView recyclerView_users;
    TextView topTextView;
    String class_name,token,name;
    DatabaseReference mRef;
    ArrayList<String> alreadyAdd;
    ArrayList<ItemUsers> usersArrayList;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users_in_class);

        btn_back = findViewById(R.id.add_users_in_class_back_btn);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView_users = findViewById(R.id.add_users_in_class_users_recycle);

        topTextView = findViewById(R.id.add_users_in_class_textView);

        class_name = getIntent().getStringExtra("class_name");
        token = getIntent().getStringExtra("token");
        name = getIntent().getStringExtra("name");

        mRef = FirebaseDatabase.getInstance().getReference();

        getInitAdd();
        getInitUsers();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i =0;i<usersArrayList.size();i++){
                    if (usersArrayList.get(i).getAdd()){
                        mRef.child("class").child(token).child("list_users")
                                .child(usersArrayList.get(i).getToken()).child("add_by").setValue(mUser.getUid());
                    }else {
                        int finalI = i;
                        mRef.child("class").child(token).child("list_users").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange( DataSnapshot snapshot) {
                                if (snapshot.child(usersArrayList.get(finalI).getToken()).exists()){
                                    mRef.child("class").child(token).child("list_users").child(usersArrayList.get(finalI).getToken()).removeValue();
                                }
                            }

                            @Override
                            public void onCancelled( DatabaseError error) {

                            }
                        });
                    }
                }

                {
                    Intent intent = new Intent(AddUsersInClassActivity.this, ClassActivity.class);
                    intent.putExtra("name", class_name);
                    intent.putExtra("token", token);
                    startActivity(intent);
                    finish();
                }
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

    private void getInitUsers() {
        mRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                usersArrayList = new ArrayList<>();

                AdapterUsers adapterUsers = new AdapterUsers(usersArrayList,AddUsersInClassActivity.this);
                recyclerView_users.setAdapter(adapterUsers);
                LinearLayoutManager linearLayout = new LinearLayoutManager(AddUsersInClassActivity.this);
                linearLayout.setOrientation(VERTICAL);
                recyclerView_users.setLayoutManager(linearLayout);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String type = dataSnapshot.child("type").getValue().toString();
                    String username = dataSnapshot.child("username").getValue().toString();
                    String uid_add_by = dataSnapshot.child("create_by").getValue().toString();
                    String add_by = snapshot.child(uid_add_by).child("username").getValue().toString();
                    String roll_no= "null";
                    Boolean add = false;

                    if (type.equals("student")){
                        roll_no = dataSnapshot.child("roll_no").getValue().toString();
                    }

                    for (int i = 0; i < alreadyAdd.size();i++){
                        if (dataSnapshot.getKey().equals(alreadyAdd.get(i))){
                            add = true;
                        }
                    }

                    usersArrayList.add(new ItemUsers(dataSnapshot.getKey(),username,"Roll No :- "+roll_no,"Add By :- "+add_by,type, add));
                    adapterUsers.notifyDataSetChanged();
                    Log.d(TAG,dataSnapshot.toString());
                }



            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }

    private void getInitAdd() {

        mRef.child("class").child(token).child("list_users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                alreadyAdd = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    alreadyAdd.add(dataSnapshot.getKey());

                    Log.d(TAG,dataSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


    }
}