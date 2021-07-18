package com.example.messenger.components.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.messenger.R;
import com.example.messenger.components.ItemClickHandler;
import com.example.messenger.helpers.commons.SharedPreferencesHelper;
import com.example.messenger.helpers.commons.SharedPreferencesKeys;
import com.example.messenger.models.AccountResponse;
import com.example.messenger.models.ChatResponse;
import com.google.android.material.imageview.ShapeableImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ChatResponse> chatResponses;
    private ItemClickHandler itemClickHandler;
    private AccountResponse friendAccount;

    public ChatAdapter(Context context, ArrayList<ChatResponse> chatResponses, ItemClickHandler itemClickHandler, AccountResponse friendAccount) {
        this.chatResponses = chatResponses;
        this.context = context;
        this.itemClickHandler = itemClickHandler;
        this.friendAccount = friendAccount;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item_row, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String idAccount = (String) SharedPreferencesHelper.INSTANCE.get(SharedPreferencesKeys.ID_ACCOUNT, String.class);

        if (friendAccount != null) {
            if (friendAccount.getState() == 0) {
                holder.message_item_row_img_online.setVisibility(View.GONE);
            } else {
                holder.message_item_row_img_online.setVisibility(View.VISIBLE);
            }
        }

        if (idAccount.equals(chatResponses.get(position).getId_sender())) {
            holder.imgSender.setVisibility(View.GONE);
            holder.layout_content.setBackground(context.getDrawable(R.drawable.custom_send_message_me_sender));
            holder.message_item_row_img_online.setVisibility(View.GONE);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity=Gravity.RIGHT;
            holder.rootItem.setLayoutParams(params);
        } else {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity=Gravity.LEFT;
            holder.imgSender.setVisibility(View.VISIBLE);
            holder.rootItem.setLayoutParams(params);

            holder.layout_content.setBackground(context.getDrawable(R.drawable.custom_messager_sender));
        }

        holder.txtContent.setText(chatResponses.get(position).getContent());
        holder.txt_time_last_at.setText(chatResponses.get(position).getLast_at());
    }

    @Override
    public int getItemCount() {
        return chatResponses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView imgSender, imgReceiver, message_item_row_img_online;
        private TextView txtContent, txt_time_last_at;
        private LinearLayout layout_content;
        private FrameLayout rootItem;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtContent = itemView.findViewById(R.id.txt_message_content);
            imgSender = itemView.findViewById(R.id.message_item_row_img_user);
            imgReceiver = itemView.findViewById(R.id.message_item_row_img_user);
            message_item_row_img_online = itemView.findViewById(R.id.message_item_row_img_online);
            txt_time_last_at = itemView.findViewById(R.id.message_item_txt_time_last_at);
            layout_content = itemView.findViewById(R.id.layout_content);
            rootItem=itemView.findViewById(R.id.rootItem);
        }
    }
}
