package com.example.messenger.fragments.message;

import com.example.messenger.models.AccountResponse;
import com.example.messenger.models.MessageResponse;

import java.util.ArrayList;

public class MessageContract {
    interface view {
        void getAccountFriendSuccess(ArrayList<AccountResponse> accountResponses);

        void getAccountFail();

        void getMessageSuccess(ArrayList<MessageResponse> messageResponses);

        void getMessageFail();

//        void getFriendIdSuccess(ArrayList<String> friendIds);
//
//        void getFriendIdFail();
    }

    interface Presenter {
        void getAccountFriend(String path);

        void getMessage(String path);

    }
}
