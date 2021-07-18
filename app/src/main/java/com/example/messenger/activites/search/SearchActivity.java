package com.example.messenger.activites.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.messenger.R;
import com.example.messenger.components.adapters.SearchAdapter;
import com.example.messenger.models.AccountResponse;

import java.util.ArrayList;
import java.util.UUID;

public class SearchActivity extends AppCompatActivity {

    private EditText edtSearch;
    private RecyclerView recyclerViewSearch;
    private ImageView imgBackButton;
    private SearchAdapter searchAdapter;
    private ArrayList<AccountResponse> accountResponses = new ArrayList<>();
    private ArrayList<AccountResponse> currentAccountResponses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        for (int i = 0; i < 100; i++) {
            accountResponses.add(new AccountResponse(null, UUID.randomUUID().toString(),null,null,null,null,null,null));
        }
        currentAccountResponses.addAll(accountResponses);
        initView();
        initEvent();
    }

    private void initEvent() {
        imgBackButton.setOnClickListener(v -> {
            finish();
        });

        searchHandler();
    }

    private void searchHandler() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.toString().trim().length() > 0) {
                    for (int i = 0; i < currentAccountResponses.size(); i++) {
                        if (currentAccountResponses.get(i).getDisplay_name().contains(s)) {
                            accountResponses.add(currentAccountResponses.get(i));
                        }
                        searchAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initView() {
        imgBackButton = findViewById(R.id.img_back_button);
        edtSearch = findViewById(R.id.edt_search);
        recyclerViewSearch = findViewById(R.id.recycler_search);
        searchAdapter = new SearchAdapter(SearchActivity.this, accountResponses);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewSearch.setLayoutManager(linearLayoutManager);
        recyclerViewSearch.setAdapter(searchAdapter);
    }
}