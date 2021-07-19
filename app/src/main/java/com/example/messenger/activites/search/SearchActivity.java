package com.example.messenger.activites.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.messenger.R;
import com.example.messenger.activites.send_message.SendMessageActivity;
import com.example.messenger.components.ItemClickHandler;
import com.example.messenger.components.adapters.SearchAdapter;
import com.example.messenger.helpers.commons.SharedPreferencesHelper;
import com.example.messenger.helpers.commons.SharedPreferencesKeys;
import com.example.messenger.helpers.databases.FireBaseController;
import com.example.messenger.helpers.databases.FireBaseTableKey;
import com.example.messenger.models.AccountResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class SearchActivity extends AppCompatActivity implements ItemClickHandler {

    private EditText edtSearch;
    private RecyclerView recyclerViewSearch;
    private ImageView imgBackButton;
    private SearchAdapter searchAdapter;
    private ArrayList<AccountResponse> accountResponses = new ArrayList<>();
    private ArrayList<AccountResponse> currentAccountResponses = new ArrayList<>();
    private AccountResponse me;
    private ProgressBar progress_bar;
    private ItemClickHandler itemClickHandler = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        for (int i = 0; i < 100; i++) {
//            accountResponses.add(new AccountResponse(null, UUID.randomUUID().toString(),null,null,null,null,null,null));
//        }
        initView();
        initEvent();
        try {
            getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getData() throws Exception {
        String userName = (String) SharedPreferencesHelper.INSTANCE.get(SharedPreferencesKeys.ID_ACCOUNT, String.class);
        FireBaseController.getInstance().getData(FireBaseTableKey.ACCOUNT_KEY + userName, new FireBaseController.RetrieveCallBack() {
            @Override
            public void retrieveSuccess(DataSnapshot data) {
                if (data.exists()) {
                    me = data.getValue(AccountResponse.class);
                    me.setId(data.getKey());
                    try {
                        getFriend();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void retrieveFail(DatabaseError error) {

            }
        });
    }

    private void getFriend() throws Exception {
        ArrayList<String> friendIds = new ArrayList<>();
        if (me == null || me.getFriend() == null) {
            searchAdapter.notifyDataSetChanged();
            return;
        }

        for (Map.Entry<String, String> map : me.getFriend().entrySet()) {
            friendIds.add(map.getValue());
        }

        for (int i = 0; i < friendIds.size(); i++) {
            FireBaseController.getInstance().getData(FireBaseTableKey.ACCOUNT_KEY + friendIds.get(i), new FireBaseController.RetrieveCallBack() {
                @Override
                public void retrieveSuccess(DataSnapshot data) {
                    if (data.getValue() instanceof Map) {
                        AccountResponse accountResponse = data.getValue(AccountResponse.class);
                        accountResponse.setId(data.getKey());

                        accountResponses.add(accountResponse);

                        if (accountResponses.size() == friendIds.size()) {
                            currentAccountResponses.addAll(accountResponses);
                            searchAdapter.notifyDataSetChanged();
                            progress_bar.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void retrieveFail(DatabaseError error) {
                    searchAdapter.notifyDataSetChanged();
                }
            });
        }
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
                    accountResponses.clear();
                    for (int i = 0; i < currentAccountResponses.size(); i++) {
                        if (currentAccountResponses.get(i).getDisplay_name().contains(s)) {
                            accountResponses.add(currentAccountResponses.get(i));
                        }
                        searchAdapter.notifyDataSetChanged();
                    }
                } else {
                    accountResponses.clear();
                    accountResponses.addAll(currentAccountResponses);
                    searchAdapter.notifyDataSetChanged();
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
        progress_bar = findViewById(R.id.progress_bar);
        recyclerViewSearch = findViewById(R.id.recycler_search);
        searchAdapter = new SearchAdapter(SearchActivity.this, accountResponses, itemClickHandler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewSearch.setLayoutManager(linearLayoutManager);
        recyclerViewSearch.setAdapter(searchAdapter);
    }

    @Override
    public void itemClick(int position) {
        Intent intent = new Intent(SearchActivity.this, SendMessageActivity.class);
        intent.putExtra(SendMessageActivity.FRIEND_ACCOUNT_KEY, accountResponses.get(position));
        startActivity(intent);
    }
}