package com.example.messenger.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.messenger.R;
import com.example.messenger.components.adapters.MessageAdapter;
import com.example.messenger.components.adapters.OnlineAdapter;
import com.example.messenger.models.AccountResponse;
import com.example.messenger.models.MessageResponse;

import java.util.ArrayList;

public class MessageFragment extends Fragment {

    private RecyclerView recyclerViewMessage;
    private MessageAdapter messageAdapter;
    private ArrayList<MessageResponse> messageResponses=new ArrayList<>();
    private ArrayList<AccountResponse> accountResponses=new ArrayList<>();

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        recyclerViewMessage=view.findViewById(R.id.fragment_message_recycler);
        messageAdapter=new MessageAdapter(getContext(),messageResponses,accountResponses);

        for(int i=0;i<100;i++){
            messageResponses.add(new MessageResponse());
            accountResponses.add(new AccountResponse());
        }

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewMessage.setLayoutManager(linearLayoutManager);
        recyclerViewMessage.setAdapter(messageAdapter);
        return view;
    }
}