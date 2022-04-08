package com.surana.myschool;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
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
import com.surana.myschool.adpter.AdapterClass;
import com.surana.myschool.adpter.AdapterUsers;
import com.surana.myschool.item.ItemClass;
import com.surana.myschool.item.ItemUsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class AddUsersInClassActivity extends AppCompatActivity {


    private static final String TAG = "My App";
    ImageButton btn_back;
    EditText search_users;
    RecyclerView recyclerView_users;
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

        search_users = findViewById(R.id.search_users);

        class_name = getIntent().getStringExtra("class_name");
        token = getIntent().getStringExtra("token");
        name = getIntent().getStringExtra("name");

        mRef = FirebaseDatabase.getInstance().getReference();

        getInitAdd();
        getInitUsers();

        search_users.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

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
                Intent intent = new Intent(AddUsersInClassActivity.this, MainActivity.class);
                intent.putExtra("name", class_name);
                intent.putExtra("token", token);
                startActivity(intent);
                finish();

            }
        });

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    private void filter(String s) {
        ArrayList<ItemUsers> stringArrayList = new ArrayList<>();
        AdapterUsers adapter = new AdapterUsers(stringArrayList,this);

        for (ItemUsers itemUsers : usersArrayList){
            if (itemUsers.getUsername().toLowerCase().contains(s.toLowerCase()) ||
                    itemUsers.getCreate_by().toLowerCase().contains(s.toLowerCase())){
                stringArrayList.add(new ItemUsers(itemUsers.getToken(),itemUsers.getUsername(),
                        itemUsers.getRollNo(),itemUsers.getCreate_by(),itemUsers.getType(),itemUsers.getAdd()));
            }
            adapter.notifyDataSetChanged();
        }
        recyclerView_users.setAdapter(adapter);
        recyclerView_users.setLayoutManager(new LinearLayoutManager(this));
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

                    Collections.sort(usersArrayList, Comparator.comparing(ItemUsers::getType));

                    usersArrayList.add(new ItemUsers(dataSnapshot.getKey(),username
                            ,"Roll No :-  "+roll_no,"Add By :- "+add_by,type, add));
                    Log.d(TAG,dataSnapshot.toString());
                }

                adapterUsers.notifyDataSetChanged();

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