package com.example.messenger.activites.send_message;

import com.example.messenger.models.AccountResponse;
import com.example.messenger.models.ChatResponse;

import java.util.ArrayList;

public class SendMessageContract {
    interface view {
        void getAccountFriendSuccess(AccountResponse accountResponse);

        void getAccountFriendFail();

        void getHistoryChatSuccess(ArrayList<ChatResponse> chatResponses, String path);

        void getHistoryChatFail();

        void sendMessageSuccess();

        void sendMessageFail();

        void dataIsExist(String path, ArrayList<ChatResponse> chatResponses);

        void dataDoesNotExist();
    }

    interface presenter {
        void getAccountFriend(String path);

        void getHistoryChat(String path);

        void sendMessage(String content, String idSender, String idReceiver);

        void checkDataIsExist(String path);
    }
}
