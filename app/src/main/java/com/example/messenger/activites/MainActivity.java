package com.example.messenger.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.messenger.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottom_nav;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private TextView banner_txt_count_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        bottom_nav=findViewById(R.id.bottom_nav);
        navHostFragment=(NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController=navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottom_nav,navController);
        //kotlin bottom_nav.setupWithNavController(navController);
        banner_txt_count_message=findViewById(R.id.banner_txt_count_message);
    }
}