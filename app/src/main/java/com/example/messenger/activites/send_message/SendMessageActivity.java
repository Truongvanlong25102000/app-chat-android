package com.example.messenger.activites.send_message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messenger.R;
import com.example.messenger.activites.gallery.ClosePictureHandler;
import com.example.messenger.components.ItemClickHandler;
import com.example.messenger.components.adapters.ChatAdapter;
import com.example.messenger.components.adapters.GalleryAdapter;
import com.example.messenger.components.adapters.MessagePictureAdapter;
import com.example.messenger.helpers.commons.SharedPreferencesHelper;
import com.example.messenger.helpers.commons.SharedPreferencesKeys;
import com.example.messenger.helpers.databases.FireBaseTableKey;
import com.example.messenger.helpers.storage.FirebaseStorageHelper;
import com.example.messenger.models.AccountResponse;
import com.example.messenger.models.ChatResponse;
import com.example.messenger.models.MessageResponse;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SendMessageActivity extends AppCompatActivity implements ItemClickHandler, ClosePictureHandler, SendMessageContract.view {
    public static final String FRIEND_ACCOUNT_KEY = "FRIEND_ACCOUNT_KEY";
    public static final String FRIEND_MESSAGE_KEY = "FRIEND_ACCOUNT_KEY";

    private static final int PICK_IMAGE = 9999;

    private ImageView imgBackButton, imgPhoneButton, input_message_img_picture, input_message_img_recode, input_message_img_send;
    private EditText edt_input;
    private TextView txtNameFriendBanner, banner_send_message_txt_state;
    private RecyclerView recyclerViewChat;
    private ItemClickHandler itemClickHandler = this;
    private ChatAdapter chatAdapter;
    private ArrayList<ChatResponse> chatResponses = new ArrayList<>();
    private AccountResponse accountResponse;
    private MessageResponse messageResponse;
    private SendMessageContract.presenter mPresenter;
    private InputMethodManager imm;
    private Boolean isShowKeyBroad = false;
    private RecyclerView recyclerViewPicture;
    private GalleryAdapter galleryAdapter;
    private ArrayList<Uri> uris = new ArrayList<>();
    private ArrayList<Uri> baseUris = new ArrayList<>();
    private ArrayList<String> imageEncodes = new ArrayList<>();
    private ClosePictureHandler closePictureHandler = this;
    private MessagePictureAdapter messagePictureAdapter;

    @Override
    public void closePicture(int position) {
        uris.remove(position);
        galleryAdapter.notifyDataSetChanged();
    }

    private enum StateAccount {
        ONLINE("Online now"),
        OFFLINE("Offline");

        public final String value;

        StateAccount(String s) {
            this.value = s;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        getBundle();
        initView();
        initEvent();
        getData();
    }

    private void getBundle() {
        if (getIntent() != null) {
            if (getIntent().getSerializableExtra(FRIEND_ACCOUNT_KEY) != null) {
                if (getIntent().getSerializableExtra(FRIEND_ACCOUNT_KEY) instanceof AccountResponse) {
                    accountResponse = (AccountResponse) getIntent().getSerializableExtra(FRIEND_ACCOUNT_KEY);
                } else {
                    messageResponse = (MessageResponse) getIntent().getSerializableExtra(FRIEND_MESSAGE_KEY);
                }
            } else if (getIntent().getSerializableExtra(FRIEND_MESSAGE_KEY) != null) {
                messageResponse = (MessageResponse) getIntent().getSerializableExtra(FRIEND_MESSAGE_KEY);
            }
        }
    }

    /**
     * messageResponse.getId() is id friend
     */
    private void getData() {
        String userName = (String) SharedPreferencesHelper.INSTANCE.get(SharedPreferencesKeys.ID_ACCOUNT, String.class);
        String idFriend = "";
        if (accountResponse != null) {
            idFriend = accountResponse.getId();
            txtNameFriendBanner.setText(accountResponse.getDisplay_name());
            if (accountResponse.getState() == 0) {
                banner_send_message_txt_state.setText(StateAccount.OFFLINE.value);
            } else {
                banner_send_message_txt_state.setText(StateAccount.ONLINE.value);
            }
            SharedPreferencesHelper.INSTANCE.put(SharedPreferencesKeys.PHONE, accountResponse.getPhone());
        } else if (messageResponse != null) {
            idFriend = messageResponse.getId();
            mPresenter.getAccountFriend(FireBaseTableKey.ACCOUNT_KEY + messageResponse.getId());
        }

        mPresenter.getHistoryChat(FireBaseTableKey.CHAT_KEY + userName + "/" + idFriend);
        FirebaseDatabase.getInstance().getReference(FireBaseTableKey.MESSAGE_KEY).child(userName).child(idFriend).child("is_read").setValue(true);
        FirebaseDatabase.getInstance().getReference(FireBaseTableKey.MESSAGE_KEY).child(idFriend).child(userName).child("is_read").setValue(true);

    }

    private void initEvent() {
        keyBroadHandler();
        backPressed();
        textInputListener();
        sendMessageHandler();
        sendFileHandler();
        call();
    }

    private void call() {
        imgPhoneButton.setOnClickListener(v -> {
            String phone = (String) SharedPreferencesHelper.INSTANCE.get(SharedPreferencesKeys.PHONE, String.class);
            if (phone.equals("")) {
                Toast.makeText(SendMessageActivity.this, "Phone does not exit", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }

        });
    }

    private void sendFileHandler() {
        input_message_img_picture.setOnClickListener(v -> {
//            BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSheetDialogFragment();
//            bottomSheetDialogFragment.show(this.getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        });
    }

    private void keyBroadHandler() {
        edt_input.setOnClickListener(v -> {
            isShowKeyBroad = true;
            edt_input.requestFocus();
            imm.showSoftInput(edt_input, InputMethodManager.SHOW_IMPLICIT);
        });
    }

    private void sendMessageHandler() {
        input_message_img_send.setOnClickListener(v -> {
            if (edt_input.getText().toString().trim().length() > 0) {
                String idSender = (String) SharedPreferencesHelper.INSTANCE.get(SharedPreferencesKeys.ID_ACCOUNT, String.class);
                String idReceiver = accountResponse.getId();
                String idChat = String.valueOf((new Date().getTime()));
                mPresenter.sendMessage(edt_input.getText().toString(), idSender, idReceiver, idChat);
                edt_input.getText().clear();
                baseUris.clear();
                baseUris.addAll(uris);
                uris.clear();
                galleryAdapter.notifyDataSetChanged();
            }
            setStateKeyBroad();
        });
    }

    private void textInputListener() {
        edt_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    input_message_img_picture.setVisibility(View.GONE);
                    input_message_img_send.setVisibility(View.VISIBLE);
                } else {
                    input_message_img_picture.setVisibility(View.VISIBLE);
                    input_message_img_send.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void backPressed() {
        imgBackButton.setOnClickListener(v -> {
            if (isShowKeyBroad) {
                setStateKeyBroad();
            } else {
                finish();
            }
        });
    }

    private void setStateKeyBroad() {
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        edt_input.setFocusable(false);
        isShowKeyBroad = false;
    }

    private ArrayList<String> urls = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    uris.add(imageUri);
                }
                //do something with the image (save it to some directory or whatever you need to do with it here)
            }
            galleryAdapter.notifyDataSetChanged();
            messagePictureAdapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        txtNameFriendBanner = findViewById(R.id.banner_send_message_txt_name_user);
        input_message_img_picture = findViewById(R.id.input_message_img_picture);
        input_message_img_send = findViewById(R.id.input_message_img_send);
        input_message_img_recode = findViewById(R.id.input_message_img_recode);
        input_message_img_recode.setVisibility(View.GONE);
        banner_send_message_txt_state = findViewById(R.id.banner_send_message_txt_state);
        imgBackButton = findViewById(R.id.banner_img_back);
        imgPhoneButton = findViewById(R.id.banner_img_phone);
        edt_input = findViewById(R.id.edt_input);
        recyclerViewChat = findViewById(R.id.fragment_send_message_recycler_view);

        messagePictureAdapter = new MessagePictureAdapter(SendMessageActivity.this, uris, urls);
        chatAdapter = new ChatAdapter(SendMessageActivity.this, chatResponses, itemClickHandler, accountResponse, messagePictureAdapter, uris);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SendMessageActivity.this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(linearLayoutManager);
        recyclerViewChat.setAdapter(chatAdapter);
        mPresenter = new SendMessagePresenter(this);
        imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        recyclerViewPicture = findViewById(R.id.recycler_view_picture);
        galleryAdapter = new GalleryAdapter(SendMessageActivity.this, uris, closePictureHandler);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(SendMessageActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPicture.setLayoutManager(linearLayoutManager1);
        recyclerViewPicture.setAdapter(galleryAdapter);

    }

    @Override
    public void itemClick(int position) {

    }

    @Override
    public void getAccountFriendSuccess(AccountResponse accountResponse) {
        this.accountResponse = accountResponse;
        SharedPreferencesHelper.INSTANCE.put(SharedPreferencesKeys.PHONE, accountResponse.getPhone());
        txtNameFriendBanner.setText(accountResponse.getDisplay_name());
        if (accountResponse.getState() == 0) {
            banner_send_message_txt_state.setText(StateAccount.OFFLINE.value);
        } else {
            banner_send_message_txt_state.setText(StateAccount.ONLINE.value);
        }
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void getAccountFriendFail() {
        Toast.makeText(SendMessageActivity.this, "can not show info your friend", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getHistoryChatSuccess(ArrayList<ChatResponse> chatResponses) {
        this.chatResponses.clear();
        this.chatResponses.addAll(chatResponses);
        chatAdapter.notifyDataSetChanged();
        recyclerViewChat.smoothScrollToPosition(chatAdapter.getItemCount());
    }

    @Override
    public void getHistoryChatFail() {
        Toast.makeText(SendMessageActivity.this, "can not show history chat", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendMessageSuccess(String idSender, String idReceiver, String idChat) {
        recyclerViewChat.scrollToPosition(chatResponses.size() - 1);
        chatAdapter.notifyItemInserted(chatResponses.size() - 1);
        uploadFile(idSender, idReceiver, idChat);
    }

    private void uploadFile(String idSender, String idReceiver, String idChat) {
        FirebaseStorageHelper.getInstance().upLoadFile(uris, idSender, idReceiver, idChat, new FirebaseStorageHelper.UploadFileCallBack() {
            @Override
            public void uploadFileSuccess(String idSender, String idReceiver, String idChat, String urlImage) {
                String pathSender = FireBaseTableKey.CHAT_KEY + idSender + "/" + idReceiver;
                String pathReceiver = FireBaseTableKey.CHAT_KEY + idReceiver + "/" + idSender;
                FirebaseDatabase.getInstance().getReference(pathSender).child(idChat).child("file").child(idChat).setValue(urlImage);
                FirebaseDatabase.getInstance().getReference(pathReceiver).child(idChat).child("file").child(idChat).setValue(urlImage);
            }

            @Override
            public void uploadFileFail() {
            }
        });
    }

    @Override
    public void sendMessageFail() {
        Toast.makeText(SendMessageActivity.this, "Send message fail", Toast.LENGTH_SHORT).show();
    }

}