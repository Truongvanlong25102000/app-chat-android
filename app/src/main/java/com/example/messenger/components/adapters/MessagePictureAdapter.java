package com.example.messenger.components.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.messenger.R;
import com.google.android.material.imageview.ShapeableImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MessagePictureAdapter extends RecyclerView.Adapter<MessagePictureAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Uri> uris;
    private ArrayList<String> urls;

    public MessagePictureAdapter(Context context, ArrayList<Uri> uris, ArrayList<String> urls) {
        this.context = context;
        this.uris = uris;
        this.urls = urls;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_message_item, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        if (urls != null && urls.size() > 0) {
            Glide.with(context).load(urls.get(position)).into(holder.img);
        } else {
            Glide.with(context).load(uris.get(position)).into(holder.img);
        }
    }

    @Override
    public int getItemCount() {
        return uris.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView img;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_in_message);
        }
    }
}
