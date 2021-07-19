package com.example.messenger.helpers.storage;

import android.net.Uri;

import com.example.messenger.helpers.databases.FireBaseController;
import com.example.messenger.helpers.databases.FireBaseTableKey;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class FirebaseStorageHelper {
    private static FirebaseStorageHelper mObj = new FirebaseStorageHelper();
    private static FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private static StorageReference storageReference = firebaseStorage.getReference();

    public static FirebaseStorageHelper getInstance() {
        return mObj;
    }

    private FirebaseStorageHelper() {

    }

    public interface UploadFileCallBack {
        void uploadFileSuccess(String idSender, String idReceiver, String idChat, String urlImage);

        void uploadFileFail();
    }

    public void upLoadFile(ArrayList<Uri> uris, String idSender, String idReceiver, String idChat,UploadFileCallBack callBack) {
        String pathSender = FireBaseTableKey.CHAT_KEY + idSender + "/" + idReceiver;
        String pathReceiver = FireBaseTableKey.CHAT_KEY + idReceiver + "/" + idSender;

        StorageReference mountainsRef;
        Uri file;
        for (int i = 0; i < uris.size(); i++) {
            file = Uri.fromFile(new File(uris.get(i).getPath()));
            mountainsRef = storageReference.child("images/" + idChat);
            mountainsRef.putFile(uris.get(i)).addOnSuccessListener(taskSnapshot -> {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                    callBack.uploadFileSuccess(idSender,idReceiver,idChat,uri.toString());
                });
            }).addOnFailureListener(e -> {
                e.printStackTrace();
                callBack.uploadFileFail();
            });
        }
    }


}
