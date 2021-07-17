package com.example.messenger.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class AccountResponse {
    private String id;
    private String birth;
    private String display_name;
    private String hashtag;
    private String password;
    private Long state;
    private String thumbnail;
    private String user_name;

    public AccountResponse() {

    }

    public AccountResponse(String id, String birth, String display_name, String hashtag,
                           String password, Long state, String thumbnail, String user_name) {
        this.id = id;
        this.birth = birth;
        this.display_name = display_name;
        this.hashtag = hashtag;
        this.password = password;
        this.state = state;
        this.thumbnail = thumbnail;
        this.user_name = user_name;
    }

    public AccountResponse(String birth, String display_name, String hashtag,
                           String password, Long state, String thumbnail, String user_name) {
        this.birth = birth;
        this.display_name = display_name;
        this.hashtag = hashtag;
        this.password = password;
        this.state = state;
        this.thumbnail = thumbnail;
        this.user_name = user_name;
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
}
