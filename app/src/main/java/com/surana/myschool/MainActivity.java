package com.surana.myschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "My App";
    NavigationView mNavigationView;
    ImageButton btnMenu,btnMenuBack;
    EditText searchALl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolBar();
        mNavigationView = findViewById(R.id.navigation_view);
        btnMenu = findViewById(R.id.main_menu_btn);
        searchALl = findViewById(R.id.main_search_edit_text);

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