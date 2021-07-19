package com.example.messenger.components.adapters;

import android.content.Context;
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
import com.example.messenger.models.AccountResponse;
import com.google.android.material.imageview.ShapeableImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;
    private ArrayList<AccountResponse> accountResponses;
    private ItemClickHandler itemClickHandler;

    public SearchAdapter(Context context, ArrayList<AccountResponse> accountResponses, ItemClickHandler itemClickHandler) {
        this.context = context;
        this.accountResponses = accountResponses;
        this.itemClickHandler = itemClickHandler;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_in_seach_item_row, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
//        Glide.with(context).load(accountResponses.get(position).getThumbnail()).placeholder(R.drawable.avatar).into(holder.imgUser);
        holder.txtName.setText(accountResponses.get(position).getDisplay_name());
//
//        if (accountResponses.get(position).getState() == 0) {
//            holder.imgStatusAccount.setVisibility(View.GONE);
//        } else {
//            holder.imgStatusAccount.setVisibility(View.VISIBLE);
//        }
        holder.rootItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickHandler.itemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountResponses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView imgUser, imgStatusAccount;
        private TextView txtName;
        private LinearLayout rootItem;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.message_item_row_img_user);
            imgStatusAccount = itemView.findViewById(R.id.message_item_row_img_online);
            txtName = itemView.findViewById(R.id.txt_name_user);
            rootItem = itemView.findViewById(R.id.rootItem);
        }
    }
}
