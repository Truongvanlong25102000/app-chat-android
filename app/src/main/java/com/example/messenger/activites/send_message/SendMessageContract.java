package com.example.messenger.activites.send_message;

import com.example.messenger.models.AccountResponse;
import com.example.messenger.models.ChatResponse;

import java.util.ArrayList;

public class SendMessageContract {
    interface view {
        void getAccountFriendSuccess(AccountResponse accountResponse);

        void getAccountFriendFail();

        void getHistoryChatSuccess(ArrayList<ChatResponse> chatResponses);

        void getHistoryChatFail();

        void sendMessageSuccess();

        void sendMessageFail();
    }

    interface presenter {
        void getAccountFriend(String path);

        void getHistoryChat(String path);

        void sendMessage(String content, String idSender, String idReceiver);

    }
}
