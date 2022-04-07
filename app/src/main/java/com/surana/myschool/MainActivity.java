package com.surana.myschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.surana.myschool.adpter.AdapterClass;
import com.surana.myschool.item.ItemClass;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterClass.OnClassListener {

    private static final String TAG = "My App";
    NavigationView mNavigationView;
    ImageButton btnMenu,btnMenuBack,btnAddClass;
    EditText searchALl;
    FirebaseUser mUser;
    DatabaseReference mRef;
    ArrayList<ItemClass> classArrayList;
    AdapterClass adapterClass;
    RecyclerView classRecycle;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        classRecycle = findViewById(R.id.class_recyclerView);
        classArrayList = new ArrayList<>();
        adapterClass = new AdapterClass(classArrayList,this);
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference();
        btnAddClass = findViewById(R.id.add_class);
        mNavigationView = findViewById(R.id.navigation_view);
        btnMenu = findViewById(R.id.main_menu_btn);
        searchALl = findViewById(R.id.main_search_edit_text);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigationView.setVisibility(View.VISIBLE);
            }
        });
        classRecycle.setAdapter(adapterClass);
        classRecycle.setLayoutManager(new LinearLayoutManager(this));

        searchALl.addTextChangedListener(new TextWatcher() {
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

        View header_menu = mNavigationView.getHeaderView(0);
        btnMenuBack = header_menu.findViewById(R.id.main_menu_back_btn);
        btnMenuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigationView.setVisibility(View.GONE);
            }
        });

        mRef.child("users").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String type = snapshot.child("type").getValue().toString();
                if (type.equals("student")){
                    btnAddClass.setVisibility(View.GONE);
                }else {
                    btnAddClass.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        mRef.child("class").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    if (dataSnapshot.child("list_users").child(mUser.getUid()).exists()){
                        String class_name = dataSnapshot.child("class_name").getValue().toString();
                        String create_by = dataSnapshot.child("create_by").getValue().toString();
                        String token = dataSnapshot.child("token").getValue().toString();
                        classArrayList.add(new ItemClass(class_name,create_by,token));
                        adapterClass.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddClassActivity.class));
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.add_users:
                        startActivity(new Intent(MainActivity.this, AddUsersActivity.class));
                        break;
                    case R.id.sign_out:
                        mAuth.signOut();
                        startActivity(new Intent(MainActivity.this,StartActivity.class));
                        finish();
                        break;

                }

                return true;
            }
        });
    }

    private void filter(String s) {
        ArrayList<ItemClass> stringArrayList = new ArrayList<>();
        AdapterClass adapter = new AdapterClass(stringArrayList,this);

        for (ItemClass itemListTask : classArrayList){
            if (itemListTask.getClass_name().toLowerCase().contains(s.toLowerCase()) ||
                    itemListTask.getCreate_by().toLowerCase().contains(s.toLowerCase())){

                stringArrayList.add(new ItemClass(itemListTask.getClass_name(),itemListTask.getCreate_by() ,itemListTask.getToken()));
            }
            adapter.notifyDataSetChanged();
        }
        classRecycle.setAdapter(adapter);
        classRecycle.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public void onClassClick(int position) {

        String token = classArrayList.get(position).getToken();
        String name = classArrayList.get(position).getClass_name();

        Intent intent = new Intent(MainActivity.this,ClassActivity.class);
        intent.putExtra("token",token);
        intent.putExtra("name",name);
        startActivity(intent);
        finish();

    }
}