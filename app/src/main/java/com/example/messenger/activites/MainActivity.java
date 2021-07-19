package com.example.messenger.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.messenger.R;
import com.example.messenger.activites.login.LoginActivity;
import com.example.messenger.activites.search.SearchActivity;
import com.example.messenger.helpers.commons.SharedPreferencesHelper;
import com.example.messenger.helpers.commons.SharedPreferencesKeys;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottom_nav;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private TextView banner_txt_count_message;
    private ImageView banner_img_search,banner_img_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String userName = (String) SharedPreferencesHelper.INSTANCE.get(SharedPreferencesKeys.ID_ACCOUNT, String.class);
        if (userName.equals("")) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        initView();
        initEvent();
    }

    private void initEvent() {
        banner_img_search.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
        });

        banner_img_edit.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
        });
    }

    private void initView() {
        bottom_nav = findViewById(R.id.bottom_nav);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottom_nav, navController);
        //kotlin bottom_nav.setupWithNavController(navController);
        banner_txt_count_message = findViewById(R.id.banner_txt_count_message);
        banner_img_search = findViewById(R.id.banner_img_search);
        banner_img_edit = findViewById(R.id.banner_img_edit);
    }
}