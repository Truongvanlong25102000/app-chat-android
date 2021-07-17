package com.example.messenger.base;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.example.messenger.helpers.databases.FireBaseController;
import com.example.messenger.models.AccountResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;

import java.util.UUID;

public class MainApplication extends Application {

    private static MainApplication mSelf;

    public static MainApplication self() {
        return mSelf;
    }

    private FireBaseController fireBaseController;

    @Override
    public void onCreate() {
        super.onCreate();
        mSelf = this;
        fireBaseController = new FireBaseController();
        fireBaseController.init();
//        testData();
//        pushData();
    }

    private void pushData() {
        AccountResponse accountResponse=new AccountResponse("17/07/2021","xyz","@xyz","111",0L,"zzz","username");
        fireBaseController.pushData("Account/"+ UUID.randomUUID().toString(),accountResponse);
    }

    private void testData() {
        fireBaseController.getData("Account/123456789", new FireBaseController.RetrieveCallBack() {
            @Override
            public void retrieveSuccess(Object data, String key) {
                if (data instanceof AccountResponse) {
                    ((AccountResponse) data).setId(key);
                    Log.d("LONGTV", ((AccountResponse) data).getDisplay_name());
                    Log.d("LONGTV", ((AccountResponse) data).getId());
                }
            }

            @Override
            public void retrieveFail(DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
