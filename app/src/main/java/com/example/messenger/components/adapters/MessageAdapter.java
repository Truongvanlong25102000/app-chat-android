package com.example.messenger.components.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.messenger.R;
import com.example.messenger.models.AccountResponse;
import com.example.messenger.models.MessageResponse;
import com.google.android.material.imageview.ShapeableImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MessageResponse> messageResponses;
    private ArrayList<AccountResponse> accountResponses;

    public MessageAdapter(Context context, ArrayList<MessageResponse> messageResponses, ArrayList<AccountResponse> accountResponses) {
        this.context = context;
        this.messageResponses = messageResponses;
        this.accountResponses = accountResponses;
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
            Glide.with(context).load("https://afamilycdn.com/150157425591193600/2021/2/17/4-nguoi-ton-ngo-khong-so-nhat-trong-tay-du-ky-gom-nhung-ai1-aeub-1613533973120733625078.jpg").into(holder.imgUser);
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
        private ShapeableImageView imgUser;
        private TextView txtNameUser;
        private TextView txtContent;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.message_item_row_img_user);
            txtContent = itemView.findViewById(R.id.message_item_row_txt_content);
            txtNameUser = itemView.findViewById(R.id.message_item_row_txt_content);
        }
    }

    class ViewHolderOnline extends ViewHolder {
        private RecyclerView recyclerViewOnline;
        private OnlineAdapter onlineAdapter;

        public ViewHolderOnline(@NonNull @NotNull View itemView) {
            super(itemView);
            recyclerViewOnline = itemView.findViewById(R.id.recyclerViewOnlineHeaderRow);
            LinearLayoutManager linearLayoutManagerOnline = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewOnline.setLayoutManager(linearLayoutManagerOnline);

            onlineAdapter = new OnlineAdapter(context, accountResponses);
            recyclerViewOnline.setAdapter(onlineAdapter);
        }
    }
}
