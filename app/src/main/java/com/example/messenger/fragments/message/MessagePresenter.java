package com.example.messenger.fragments.message;

import android.content.Context;
import android.util.Log;

import com.example.messenger.helpers.databases.FireBaseController;
import com.example.messenger.helpers.databases.FireBaseTableKey;
import com.example.messenger.models.AccountResponse;
import com.example.messenger.models.MessageResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Map;

public class MessagePresenter implements MessageContract.Presenter {
    private ArrayList<AccountResponse> accountResponses = new ArrayList<>();
    private ArrayList<MessageResponse> messageResponses = new ArrayList<>();
    private MessageContract.view mView;
    private AccountResponse me;

    public MessagePresenter(MessageContract.view view) {
        mView = view;
    }

    @Override
    public void getAccountFriend(String path) {
        FireBaseController.getInstance().getData(path, new FireBaseController.RetrieveCallBack() {
            @Override
            public void retrieveSuccess(DataSnapshot data) {
                if (data.exists()) {
                    me = data.getValue(AccountResponse.class);
                    me.setId(data.getKey());
                    getFriend();
                }
                if (data.getValue() instanceof Map) {

                } else if (data.getValue() instanceof AccountResponse) {
//                    me = data.getValue(AccountResponse.class);
//                    me.setId(data.getKey());
                }
            }

            @Override
            public void retrieveFail(DatabaseError error) {
                mView.getAccountFail();
            }
        });
    }

    private void getFriend() {
        ArrayList<String> friendIds = new ArrayList<>();
        if (me == null || me.getFriend() == null) {
            mView.getAccountFriendSuccess(new ArrayList<>());
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
                            mView.getAccountFriendSuccess(accountResponses);
                        }
                    }
                }

                @Override
                public void retrieveFail(DatabaseError error) {
                    mView.getAccountFail();
                }
            });
        }
    }

    @Override
    public void getMessage(String path) {
        FireBaseController.getInstance().getData(path, new FireBaseController.RetrieveCallBack() {
            @Override
            public void retrieveSuccess(DataSnapshot data) {
                if (data.getValue() instanceof Map) {
                    MessageResponse messageResponse;
                    messageResponses.clear();
                    for (DataSnapshot dataSnapshot : data.getChildren()) {
                        messageResponse = dataSnapshot.getValue(MessageResponse.class);
                        messageResponse.setId(dataSnapshot.getKey());
                        messageResponses.add(messageResponse);
                    }
                    mView.getMessageSuccess(messageResponses);
                }
            }

            @Override
            public void retrieveFail(DatabaseError error) {

            }
        });
    }
}
