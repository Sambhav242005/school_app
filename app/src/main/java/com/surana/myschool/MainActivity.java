package com.surana.myschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "My App";
    NavigationView mNavigationView;
    ImageButton btnMenu,btnMenuBack,btnAddClass;
    EditText searchALl;
    FirebaseUser mUser;
    DatabaseReference mRef;
    ArrayList<ItemClass> classArrayList;
    AdapterClass adapterClass;
    RecyclerView classRecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolBar();
        classRecycle = findViewById(R.id.class_recyclerView);
        classArrayList = new ArrayList<>();
        adapterClass = new AdapterClass(classArrayList,MainActivity.this);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference();
        btnAddClass = findViewById(R.id.add_class);
        mNavigationView = findViewById(R.id.navigation_view);
        btnMenu = findViewById(R.id.main_menu_btn);
        searchALl = findViewById(R.id.main_search_edit_text);

        classRecycle.setAdapter(adapterClass);
        classRecycle.setLayoutManager(new LinearLayoutManager(this));

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigationView.setVisibility(View.VISIBLE);
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

        mRef.child("class_name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    if (dataSnapshot.child("list_users").child(mUser.getUid()).exists()){
                        String class_name = dataSnapshot.child("class_name").getValue().toString();
                        String create_by = dataSnapshot.child("create_by").getValue().toString();
                        classArrayList.add(new ItemClass(class_name,create_by));
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
                }

                return true;
            }
        });
    }

    private void setUpToolBar() {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

}