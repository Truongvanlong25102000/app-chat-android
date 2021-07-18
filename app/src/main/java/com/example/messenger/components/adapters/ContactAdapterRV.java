package com.example.messenger.components.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messenger.R;
import com.example.messenger.models.PhoneContact;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ContactAdapterRV extends RecyclerView.Adapter<ContactAdapterRV.ViewHolder> {

    Activity activity;
    ArrayList<PhoneContact> arrayList;

    public ContactAdapterRV( Activity activity, ArrayList<PhoneContact> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        PhoneContact phoneContact = arrayList.get(position);

        holder.tv_name.setText(phoneContact.getName());
        holder.tv_number.setText(phoneContact.getNumber());
        holder.img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), phoneContact.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(("tel:"+phoneContact.getNumber().trim())));
                intent.setFlags((Intent.FLAG_ACTIVITY_NEW_TASK));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name, tv_number;
        ImageView img_contact, img_call;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_number = itemView.findViewById(R.id.tv_number);
            img_contact = itemView.findViewById(R.id.img_contact);
            img_call = itemView.findViewById(R.id.img_call);
        }
    }
}
