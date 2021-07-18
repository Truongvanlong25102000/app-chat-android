package com.example.messenger.models;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class AccountResponse implements Serializable {
    private String id;
    private String birth;
    private String display_name;
    private String hashtag;
    private String password;
    private Long state;
    private String thumbnail;
    private String user_name;
    private String phone;
    private HashMap<String, String> friend;

    public AccountResponse() {

    }

    public AccountResponse(String id, String birth, String display_name, String hashtag, String password, Long state, String thumbnail, String user_name, String phone, HashMap<String, String> friend) {
        this.id = id;
        this.birth = birth;
        this.display_name = display_name;
        this.hashtag = hashtag;
        this.password = password;
        this.state = state;
        this.thumbnail = thumbnail;
        this.user_name = user_name;
        this.phone = phone;
        this.friend = friend;
    }

    public AccountResponse(String birth, String display_name, String hashtag,
                           String password, Long state, String thumbnail, String user_name, String phone) {
        this.birth = birth;
        this.display_name = display_name;
        this.hashtag = hashtag;
        this.password = password;
        this.state = state;
        this.thumbnail = thumbnail;
        this.user_name = user_name;
        this.phone = phone;
    }

    public AccountResponse toEntities(DataSnapshot dataSnapshot) {
        AccountResponse accountResponse=new AccountResponse();
        accountResponse.id = dataSnapshot.getKey();
        accountResponse.birth = dataSnapshot.child("birth").getValue(String.class);
        accountResponse.display_name = dataSnapshot.child("display_name").getValue(String.class);
        accountResponse.hashtag = dataSnapshot.child("hashtag").getValue(String.class);
        accountResponse.password = dataSnapshot.child("password").getValue(String.class);
        accountResponse.state = dataSnapshot.child("state").getValue(Long.class);
        accountResponse.thumbnail = dataSnapshot.child("thumbnail").getValue(String.class);
        accountResponse.user_name = dataSnapshot.child("user_name").getValue(String.class);
        accountResponse.phone = dataSnapshot.child("phone").getValue(String.class);
//        HashMap<String,String> friends= (HashMap<String, String>) dataSnapshot.child("friend").getValue(Map.class);
        return accountResponse;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getBirth() {
        return birth;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getHashtag() {
        return hashtag;
    }

    public String getPassword() {
        return password;
    }

    public Long getState() {
        return state;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPhone() {
        return phone;
    }

    public HashMap<String, String> getFriend() {
        return friend;
    }
}
