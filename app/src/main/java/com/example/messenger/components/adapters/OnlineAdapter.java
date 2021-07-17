package com.example.messenger.components.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messenger.R;
import com.example.messenger.models.AccountResponse;
import com.google.android.material.imageview.ShapeableImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OnlineAdapter extends RecyclerView.Adapter<OnlineAdapter.ViewHolder> {
    private Context context;
    private ArrayList<AccountResponse> accountResponses;

    public OnlineAdapter(Context context, ArrayList<AccountResponse> accountResponses) {
        this.context = context;
        this.accountResponses = accountResponses;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_online_item_row, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.txtName.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return accountResponses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private ShapeableImageView imgUser,message_item_row_img_online;
        private FrameLayout userRootItem;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.user_item_txt_name);
            imgUser=itemView.findViewById(R.id.message_item_row_img_user);
            message_item_row_img_online=itemView.findViewById(R.id.message_item_row_img_online);
            userRootItem=itemView.findViewById(R.id.userRootItem);
        }
    }
}
