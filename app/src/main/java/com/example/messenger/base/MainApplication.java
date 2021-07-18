package com.example.messenger.base;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.example.messenger.helpers.commons.SharedPreferencesHelper;
import com.example.messenger.helpers.commons.SharedPreferencesKeys;
import com.example.messenger.helpers.databases.FireBaseController;
import com.example.messenger.helpers.databases.FireBaseTableKey;
import com.example.messenger.models.AccountResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ServerValue;

import java.util.Date;
import java.util.UUID;

public class MainApplication extends Application {

    private static MainApplication mSelf;

    public static MainApplication self() {
        return mSelf;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSelf = this;
        FireBaseController.getInstance().init();
//        SharedPreferencesHelper.INSTANCE.put(SharedPreferencesKeys.ID_ACCOUNT,"c5728450-9dc7-4075-bcaf-9153b4960da8");
        SharedPreferencesHelper.INSTANCE.put(SharedPreferencesKeys.ID_ACCOUNT,"abc");
//        getData();
//        pushData();
//        removeData();
    }

    private void removeData() {
        FireBaseController.getInstance().removeData(FireBaseTableKey.ACCOUNT_KEY + "fa611982-676e-4a30-bc11-d2f9ee3929ab");
    }

    private void pushData() {
        AccountResponse accountResponse = new AccountResponse("17/07/2021", "xyz", "@xyz", "111", 0L, "zzz", "username", "0123456");
        FireBaseController.getInstance().pushData("Account/" + (new Date().getTime()), accountResponse);
    }

    private void getData() {
        FireBaseController.getInstance().getData("Account/123456789", new FireBaseController.RetrieveCallBack() {
            @Override
            public void retrieveSuccess(DataSnapshot data) {
                if (data.getValue() instanceof AccountResponse) {
                    ((AccountResponse) data.getValue()).setId(data.getKey());
                }
            }

            @Override
            public void retrieveFail(DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
