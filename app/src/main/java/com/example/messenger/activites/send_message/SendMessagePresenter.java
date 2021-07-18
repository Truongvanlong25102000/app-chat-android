package com.example.messenger.activites.send_message;

import android.content.Context;
import android.renderscript.Element;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.messenger.helpers.databases.FireBaseController;
import com.example.messenger.helpers.databases.FireBaseTableKey;
import com.example.messenger.models.AccountResponse;
import com.example.messenger.models.ChatResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class SendMessagePresenter implements SendMessageContract.presenter {
    private SendMessageContract.view mView;
    private ArrayList<ChatResponse> chatResponses = new ArrayList<>();
    private AccountResponse accountResponse;
    private SimpleDateFormat df = new SimpleDateFormat("hh:mm a", Locale.US);

    public SendMessagePresenter(SendMessageContract.view view) {
        this.mView = view;
    }

    @Override
    public void getAccountFriend(String path) {
        FireBaseController.getInstance().getData(path, new FireBaseController.RetrieveCallBack() {
            @Override
            public void retrieveSuccess(DataSnapshot data) {
                if (data.getValue() instanceof Map) {
                    AccountResponse accountResponse = data.getValue(AccountResponse.class);
                    accountResponse.setId(data.getKey());
                    mView.getAccountFriendSuccess(accountResponse);
                }
            }

            @Override
            public void retrieveFail(DatabaseError error) {
                mView.getAccountFriendFail();
            }
        });
    }

    @Override
    public void getHistoryChat(String path) {
        FireBaseController.getInstance().getData(path, new FireBaseController.RetrieveCallBack() {
            @Override
            public void retrieveSuccess(DataSnapshot data) {
                if (!data.exists()) {
                    mView.getHistoryChatSuccess(chatResponses, path);
                } else if (data.getValue() instanceof Map) {
                    for (DataSnapshot dataSnapshot : data.getChildren()) {
                        ChatResponse chatResponse = dataSnapshot.getValue(ChatResponse.class);
                        chatResponses.add(chatResponse);
                    }
                    mView.getHistoryChatSuccess(chatResponses, path);
                }
            }

            @Override
            public void retrieveFail(DatabaseError error) {
                mView.getHistoryChatFail();
            }
        });
    }

    @Override
    public void sendMessage(String content, String idSender, String idReceiver) {
        String path = FireBaseTableKey.CHAT_KEY + idSender + "_" + idReceiver + "/" + (new Date().getTime());

        String last_at = df.format(new Date());
        ChatResponse chatResponse = new ChatResponse(content, null, idReceiver, idSender, last_at);
        FireBaseController.getInstance().pushData(path, chatResponse);
    }

    @Override
    public void checkDataIsExist(String node) {
        FireBaseController.getInstance().getData(node, new FireBaseController.RetrieveCallBack() {
            @Override
            public void retrieveSuccess(DataSnapshot data) {
//                if (data.exists()) {
//                    for (DataSnapshot dataSnapshot : data.getChildren()) {
//                        ChatResponse chatResponse = dataSnapshot.getValue(ChatResponse.class);
//                        chatResponses.add(chatResponse);
//                    }
//                    mView.dataIsExist(node, chatResponses);
//                } else {
//
//                }
            }

            @Override
            public void retrieveFail(DatabaseError error) {
            }
        });
    }


}
