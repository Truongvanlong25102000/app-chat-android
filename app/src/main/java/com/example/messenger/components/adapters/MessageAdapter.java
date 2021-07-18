package com.example.messenger.components.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.messenger.R;
import com.example.messenger.activites.send_message.SendMessageActivity;
import com.example.messenger.components.ItemClickHandler;
import com.example.messenger.helpers.commons.SharedPreferencesHelper;
import com.example.messenger.helpers.commons.SharedPreferencesKeys;
import com.example.messenger.helpers.databases.FireBaseController;
import com.example.messenger.helpers.databases.FireBaseTableKey;
import com.example.messenger.models.AccountResponse;
import com.example.messenger.models.MessageResponse;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MessageResponse> messageResponses;
    private ArrayList<AccountResponse> accountResponses;
    private ItemClickHandler itemClickHandler;
    private OnlineAdapter onlineAdapter;
    private ArrayList<AccountResponse> friendChats = new ArrayList<>();

    public MessageAdapter(Context context, ArrayList<MessageResponse> messageResponses, ArrayList<AccountResponse> accountResponses, ItemClickHandler itemClickHandler, ArrayList<AccountResponse> friendChats) {
        this.context = context;
        this.messageResponses = messageResponses;
        this.accountResponses = accountResponses;
        this.itemClickHandler = itemClickHandler;
        this.friendChats = friendChats;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType, parent, false);
        if (viewType == R.layout.message_online_item_header) {
            return new ViewHolderOnline(view);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        if (holder instanceof ViewHolderOnline) {

        } else {
            holder.rootLayout.setOnClickListener(v -> {
                itemClickHandler.itemClick(position - 1);
            });

            holder.txtNameUser.setText(messageResponses.get(position - 1).getId_sender());
            holder.txtContent.setText(messageResponses.get(position - 1).getContent());
//            if (friendChats.get(position-1).getState() == 0) {
//                holder.message_item_row_img_online.setVisibility(View.GONE);
//            } else {
//                holder.message_item_row_img_online.setVisibility(View.VISIBLE);
//            }
//
//            if (friendChats.get(position-1).getThumbnail() != null) {
//                Glide.with(context).load(friendChats.get(position-1).getThumbnail()).placeholder(R.drawable.avatar).into(holder.imgUser);
//            }

        }
    }

    @Override
    public int getItemCount() {
        return 1 + messageResponses.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return R.layout.message_online_item_header;
        }
        return R.layout.message_item_row;

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView imgUser, message_item_row_img_online;
        private TextView txtNameUser;
        private TextView txtContent;
        private LinearLayout rootLayout;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.message_item_row_img_user);
            message_item_row_img_online = itemView.findViewById(R.id.message_item_row_img_online);
            txtContent = itemView.findViewById(R.id.message_item_row_txt_content);
            txtNameUser = itemView.findViewById(R.id.message_item_row_txt_content);
            rootLayout = itemView.findViewById(R.id.message_item_row_root_layout);
        }
    }

    public void refreshAdapterOnline() {
        onlineAdapter.notifyDataSetChanged();
    }

    class ViewHolderOnline extends ViewHolder implements ItemClickHandler {
        private RecyclerView recyclerViewOnline;
        private ItemClickHandler itemClickHandler = this;

        public ViewHolderOnline(@NonNull @NotNull View itemView) {
            super(itemView);
            recyclerViewOnline = itemView.findViewById(R.id.recyclerViewOnlineHeaderRow);
            LinearLayoutManager linearLayoutManagerOnline = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewOnline.setLayoutManager(linearLayoutManagerOnline);

            onlineAdapter = new OnlineAdapter(context, accountResponses, itemClickHandler);
            recyclerViewOnline.setAdapter(onlineAdapter);
        }

        @Override
        public void itemClick(int position) {
            Intent intent = new Intent(context, SendMessageActivity.class);
            intent.putExtra(SendMessageActivity.FRIEND_ACCOUNT_KEY, accountResponses.get(position));
            context.startActivity(intent);
        }
    }
}
