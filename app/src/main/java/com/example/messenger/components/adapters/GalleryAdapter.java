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
import com.example.messenger.activites.gallery.ClosePictureHandler;
import com.google.android.material.imageview.ShapeableImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Uri> uris;
    private ClosePictureHandler closePictureHandler;

    public GalleryAdapter(Context context, ArrayList<Uri> uris, ClosePictureHandler closePictureHandler) {
        this.context = context;
        this.uris = uris;
        this.closePictureHandler = closePictureHandler;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_item, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Glide.with(context).load(uris.get(position)).placeholder(R.drawable.avatar).into(holder.imgPicture);
        holder.imgClose.setOnClickListener(v -> {
            closePictureHandler.closePicture(position);
        });
    }

    @Override
    public int getItemCount() {
        return uris.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ShapeableImageView imgClose, imgPicture;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgClose = itemView.findViewById(R.id.img_ic_close);
            imgPicture = itemView.findViewById(R.id.img_picture);
        }
    }
}
