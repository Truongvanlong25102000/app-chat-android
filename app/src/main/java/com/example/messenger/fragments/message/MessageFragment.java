package com.example.messenger.fragments.message;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.messenger.R;
import com.example.messenger.activites.send_message.SendMessageActivity;
import com.example.messenger.components.ItemClickHandler;
import com.example.messenger.components.adapters.MessageAdapter;
import com.example.messenger.helpers.commons.SharedPreferencesHelper;
import com.example.messenger.helpers.commons.SharedPreferencesKeys;
import com.example.messenger.helpers.databases.FireBaseController;
import com.example.messenger.helpers.databases.FireBaseTableKey;
import com.example.messenger.models.AccountResponse;
import com.example.messenger.models.ChatResponse;
import com.example.messenger.models.MessageResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageFragment extends Fragment implements ItemClickHandler, MessageContract.view {

    private RecyclerView recyclerViewMessage;
    private MessageAdapter messageAdapter;
    private ArrayList<MessageResponse> messageResponses = new ArrayList<>();
    private ArrayList<AccountResponse> accountResponses = new ArrayList<>();
    private ArrayList<AccountResponse> friendChats = new ArrayList<>();
    private ItemClickHandler itemClickHandler = this;
    private MessageContract.Presenter mPresenter;
    private ProgressBar progress_bar;

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        progress_bar = view.findViewById(R.id.progress_bar);
        recyclerViewMessage = view.findViewById(R.id.fragment_message_recycler);
        messageAdapter = new MessageAdapter(getContext(), messageResponses, accountResponses, itemClickHandler, friendChats);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewMessage.setLayoutManager(linearLayoutManager);
        recyclerViewMessage.setAdapter(messageAdapter);

        mPresenter = new MessagePresenter(this);

        String userName = (String) SharedPreferencesHelper.INSTANCE.get(SharedPreferencesKeys.ID_ACCOUNT, String.class);

        mPresenter.getAccountFriend(FireBaseTableKey.ACCOUNT_KEY + userName);
        mPresenter.getMessage(FireBaseTableKey.MESSAGE_KEY + userName);
    }

    @Override
    public void itemClick(int position) {
        Intent intent = new Intent(getContext(), SendMessageActivity.class);
        intent.putExtra(SendMessageActivity.FRIEND_MESSAGE_KEY, messageResponses.get(position));
        getContext().startActivity(intent);
    }

    @Override
    public void getAccountFriendSuccess(ArrayList<AccountResponse> accountResponses) {
        this.accountResponses.addAll(accountResponses);
        messageAdapter.notifyDataSetChanged();
        messageAdapter.refreshAdapterOnline();
        progress_bar.setVisibility(View.GONE);
    }

    @Override
    public void getAccountFail() {

    }

    @Override
    public void getMessageSuccess(ArrayList<MessageResponse> messageResponses) {
        this.messageResponses.clear();
        this.messageResponses.addAll(messageResponses);
        messageAdapter.notifyDataSetChanged();

    }

    @Override
    public void getMessageFail() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (messageAdapter != null) {
            messageAdapter.notifyDataSetChanged();
        }
    }

    private void fakeDataListChat() {
        String x = "Hello, xin chào bạn!, bạn có rảnh không, rảnh thì uống cafe với mình nào, nếu không rảnh thì call zalo nhé, mình vừa gửi file rồi đó";
//        MessageResponse messageResponse=new MessageResponse("c5728450-9dc7-4075-bcaf-9153b4960da8",x,"3:44");
//        FireBaseController.getInstance().pushData(FireBaseTableKey.MESSAGE_KEY+"c5728450-9dc7-4075-bcaf-9153b4960da8",messageResponse);

//        FireBaseController.getInstance().pushData(FireBaseTableKey.MESSAGE_KEY+"abc/"+"abc",messageResponse);
//        FireBaseController.getInstance().pushData(FireBaseTableKey.MESSAGE_KEY+"abc/"+"d3d67785-587d-49d4-83cf-6a473bac01b7",messageResponse);

    }

    private void fakeDataListMessage() {
//        String x="Hello, xin chào bạn!, bạn có rảnh không, rảnh thì uống cafe với mình nào, nếu không rảnh thì call zalo nhé, mình vừa gửi file rồi đó";
//
////        String content, String file, String id_reader, String id_sender, String last_at
//        ChatResponse chatResponse=new ChatResponse(x,null,null,"abc","4:01");
//        FireBaseController.getInstance().pushData(FireBaseTableKey.CHAT_KEY+"abc_c5728450-9dc7-4075-bcaf-9153b4960da8/id_tin_nhan_so_1",chatResponse);
//        FireBaseController.getInstance().pushData(FireBaseTableKey.CHAT_KEY+"abc_c5728450-9dc7-4075-bcaf-9153b4960da8/id_tin_nhan_so_2",chatResponse);
//        FireBaseController.getInstance().pushData(FireBaseTableKey.CHAT_KEY+"abc_c5728450-9dc7-4075-bcaf-9153b4960da8/id_tin_nhan_so_3",chatResponse);
////        FireBaseController.getInstance().pushData(FireBaseTableKey.MESSAGE_KEY+"abc/"+"d3d67785-587d-49d4-83cf-6a473bac01b7",messageResponse);

    }
}