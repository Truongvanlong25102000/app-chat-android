package com.example.messenger.fragments.gallery;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.messenger.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

public class BottomSheetGallery extends BottomSheetDialogFragment {

    private Context context;

    public BottomSheetGallery(Context context) {
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_grallery, null, false);
        bottomSheetDialog.setContentView(view);

        return bottomSheetDialog;
    }
}
