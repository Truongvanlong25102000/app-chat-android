package com.example.messenger.activites.send_message;

import android.content.Context;
import android.renderscript.Element;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.messenger.helpers.databases.FireBaseController;
import com.example.messenger.helpers.databases.FireBaseTableKey;
import com.example.messenger.models.AccountResponse;
import com.example.messenger.models.ChatResponse;
import com.example.messenger.models.MessageResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

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
                    mView.getHistoryChatSuccess(chatResponses);
                } else if (data.getValue() instanceof Map) {
                    chatResponses.clear();
                    for (DataSnapshot dataSnapshot : data.getChildren()) {
                        ChatResponse chatResponse = dataSnapshot.getValue(ChatResponse.class);
                        chatResponses.add(chatResponse);
                    }
                    mView.getHistoryChatSuccess(chatResponses);
                }
            }

            @Override
            public void retrieveFail(DatabaseError error) {
                mView.getHistoryChatFail();
            }
        });
    }

    @Override
    public void sendMessage(String content, String idSender, String idReceiver, String idChat) {
        String pathSender = FireBaseTableKey.CHAT_KEY + idSender + "/" + idReceiver + "/" + idChat;
        String pathReceiver = FireBaseTableKey.CHAT_KEY + idReceiver + "/" + idSender + "/" + idChat;

        String last_at = df.format(new Date());
        ChatResponse chatResponse = new ChatResponse(content, null, idReceiver, idSender, last_at);
        FireBaseController.getInstance().pushData(pathSender, chatResponse);
        FireBaseController.getInstance().pushData(pathReceiver, chatResponse);

        String pathMessageLast1 = FireBaseTableKey.MESSAGE_KEY + idSender + "/" + idReceiver;
        String pathMessageLast2 = FireBaseTableKey.MESSAGE_KEY + idReceiver + "/" + idSender;

//        String id, String id_sender, String content, String file, String last_at, Boolean is_read
        MessageResponse messageResponse1 = new MessageResponse(null, idSender, content, null, last_at, true);
        MessageResponse messageResponse2 = new MessageResponse(null, idSender, content, null, last_at, false);
        FireBaseController.getInstance().pushData(pathMessageLast1, messageResponse1);
        FireBaseController.getInstance().pushData(pathMessageLast2, messageResponse2);

        mView.sendMessageSuccess(idSender,idReceiver,idChat);
    }

}
