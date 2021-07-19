package com.example.messenger.helpers.databases;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.messenger.models.AccountResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class FireBaseController {
    public interface RetrieveCallBack<T> {
        void retrieveSuccess(DataSnapshot data);

        void retrieveFail(DatabaseError error);
    }

    public interface PushDataCallBack {
        void pushDataSuccess();

        void pushDataFail();
    }

    public interface UpdateDataCallBack {
        void updateDataSuccess();

        void updateDataFail();
    }

    public interface RemoveDataCallBack {
        void removeDataSuccess();

        void removeDataFail();
    }

    public enum DataCallBackStatus {
        SUCCESS,
        ERROR,
    }

    private static DatabaseReference mDatabase;
    private static FirebaseDatabase mFireBaseDatabase = FirebaseDatabase.getInstance();
    private static FireBaseController fireBaseController=new FireBaseController();

    public static FireBaseController getInstance(){
        return fireBaseController;
    }

    private FireBaseController(){

    }

    public void init() {
//        mFireBaseDatabase.setPersistenceEnabled(true);
    }

    public void getData(String path, RetrieveCallBack retrieveCallBack) {
        mDatabase = mFireBaseDatabase.getReference(path);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                retrieveCallBack.retrieveSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                retrieveCallBack.retrieveFail(error);
            }
        });
    }

    public void pushData(String path, Object anonymousClass) {
        mDatabase = mFireBaseDatabase.getReference(path);
        mDatabase.setValue(anonymousClass);
    }

    public void removeData(String path) {
        mDatabase = mFireBaseDatabase.getReference(path);
        mDatabase.removeValue();
    }
}
