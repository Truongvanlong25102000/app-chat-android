package com.example.messenger.components.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    private MessagePictureAdapter messagePictureAdapter;
    private ArrayList<Uri> uris;

    public ChatAdapter(Context context, ArrayList<ChatResponse> chatResponses, ItemClickHandler itemClickHandler, AccountResponse friendAccount, MessagePictureAdapter messagePictureAdapter, ArrayList<Uri> uris) {
        this.chatResponses = chatResponses;
        this.context = context;
        this.itemClickHandler = itemClickHandler;
        this.friendAccount = friendAccount;
        this.messagePictureAdapter = messagePictureAdapter;
        this.uris = uris;
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
            holder.imgSender.setVisibility(View.INVISIBLE);
            holder.layout_content.setBackground(context.getDrawable(R.drawable.custom_send_message_me_sender));
            holder.message_item_row_img_online.setVisibility(View.GONE);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;
            holder.rootItem.setLayoutParams(params);
            holder.spacer.setVisibility(View.VISIBLE);
        } else {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.LEFT;
            holder.imgSender.setVisibility(View.VISIBLE);
            holder.rootItem.setLayoutParams(params);
            holder.layout_content.setBackground(context.getDrawable(R.drawable.custom_messager_sender));
            holder.spacer.setVisibility(View.GONE);
        }

        holder.txtContent.setText(chatResponses.get(position).getContent());
        holder.txt_time_last_at.setText(chatResponses.get(position).getLast_at());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

//        holder.list_image.setLayoutManager(linearLayoutManager);
//        holder.list_image.setAdapter(messagePictureAdapter);
//        if (uris.size() == 1) {
//            holder.img_in_message1.setVisibility(View.VISIBLE);
//            Glide.with(context).load(uris.get(0)).into(holder.img_in_message1);
//        } else if (uris.size() == 2) {
//            holder.img_in_message1.setVisibility(View.VISIBLE);
//            Glide.with(context).load(uris.get(1)).into(holder.img_in_message1);
//            holder.img_in_message2.setVisibility(View.VISIBLE);
//            Glide.with(context).load(uris.get(0)).into(holder.img_in_message2);
//        } else if (uris.size() == 3) {
//            holder.img_in_message1.setVisibility(View.VISIBLE);
//            Glide.with(context).load(uris.get(0)).into(holder.img_in_message1);
//            holder.img_in_message2.setVisibility(View.VISIBLE);
//            Glide.with(context).load(uris.get(1)).into(holder.img_in_message2);
//            holder.img_in_message3.setVisibility(View.VISIBLE);
//            Glide.with(context).load(uris.get(2)).into(holder.img_in_message3);
//        } else if (uris.size() == 4) {
//            holder.img_in_message1.setVisibility(View.VISIBLE);
//            Glide.with(context).load(uris.get(0)).into(holder.img_in_message1);
//            holder.img_in_message2.setVisibility(View.VISIBLE);
//            Glide.with(context).load(uris.get(1)).into(holder.img_in_message2);
//            holder.img_in_message3.setVisibility(View.VISIBLE);
//            Glide.with(context).load(uris.get(2)).into(holder.img_in_message3);
//            holder.img_in_message4.setVisibility(View.VISIBLE);
//            Glide.with(context).load(uris.get(3)).into(holder.img_in_message4);
//        } else if (uris.size() == 5) {
//            holder.img_in_message1.setVisibility(View.VISIBLE);
//            Glide.with(context).load(uris.get(0)).into(holder.img_in_message1);
//            holder.img_in_message2.setVisibility(View.VISIBLE);
//            Glide.with(context).load(uris.get(1)).into(holder.img_in_message2);
//            holder.img_in_message3.setVisibility(View.VISIBLE);
//            Glide.with(context).load(uris.get(2)).into(holder.img_in_message3);
//            holder.img_in_message4.setVisibility(View.VISIBLE);
//            Glide.with(context).load(uris.get(3)).into(holder.img_in_message4);
//            holder.img_in_message5.setVisibility(View.VISIBLE);
//            Glide.with(context).load(uris.get(4)).into(holder.img_in_message5);
//        }else{
//            holder.img_in_message1.setVisibility(View.GONE);
//            holder.img_in_message2.setVisibility(View.GONE);
//            holder.img_in_message3.setVisibility(View.GONE);
//            holder.img_in_message4.setVisibility(View.GONE);
//            holder.img_in_message5.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return chatResponses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView imgSender, imgReceiver, message_item_row_img_online, img_in_message2, img_in_message3, img_in_message4, img_in_message5, img_in_message1;
        private TextView txtContent, txt_time_last_at;
        private LinearLayout layout_content;
        private FrameLayout rootItem;
        private View spacer;
        private RecyclerView list_image;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtContent = itemView.findViewById(R.id.txt_message_content);
            imgSender = itemView.findViewById(R.id.message_item_row_img_user);
//            img_in_message1 = itemView.findViewById(R.id.img_in_message1);
//            img_in_message2 = itemView.findViewById(R.id.img_in_message2);
//            img_in_message3 = itemView.findViewById(R.id.img_in_message3);
//            img_in_message4 = itemView.findViewById(R.id.img_in_message4);
//            img_in_message5 = itemView.findViewById(R.id.img_in_message5);
            imgReceiver = itemView.findViewById(R.id.message_item_row_img_user);
            message_item_row_img_online = itemView.findViewById(R.id.message_item_row_img_online);
            txt_time_last_at = itemView.findViewById(R.id.message_item_txt_time_last_at);
            layout_content = itemView.findViewById(R.id.layout_content);
            rootItem = itemView.findViewById(R.id.rootItem);
            spacer = itemView.findViewById(R.id.spacer);
            list_image = itemView.findViewById(R.id.list_image);
        }
    }
}
