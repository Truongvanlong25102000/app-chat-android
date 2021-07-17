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
        void retrieveSuccess(T data,String key);

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
    ;

    public void init() {
        mDatabase = mFireBaseDatabase.getReference("Account/123456789");
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                AccountResponse accountResponse=snapshot.getValue(AccountResponse.class);
//                Log.d("LONGTV",accountResponse.getDisplay_name());
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
    }

    private <T> T dataToEntities(Class<T> anonymousClass, DataSnapshot dataSnapshot) {
        if (anonymousClass == AccountResponse.class) {
            AccountResponse accountResponse = dataSnapshot.getValue(AccountResponse.class);
            return (T) accountResponse;
        }

        return null;
    }

    public void getData(String path, RetrieveCallBack retrieveCallBack) {
        mDatabase = mFireBaseDatabase.getReference(path);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                retrieveCallBack.retrieveSuccess(snapshot.getValue(AccountResponse.class),snapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                retrieveCallBack.retrieveFail(error);
            }
        });
    }

    public void pushData(String path,Object anonymousClass){
        mDatabase=mFireBaseDatabase.getReference(path);
        mDatabase.setValue(anonymousClass);
    }
}
