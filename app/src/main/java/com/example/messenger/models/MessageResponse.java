package com.example.messenger.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class MessageResponse implements Serializable {
    private String id;
    private String id_sender;
    private String content;
    private String file;
    private String last_at;
    private Boolean is_read;

    public MessageResponse() {
    }

    public MessageResponse(String id, String id_sender, String content, String file, String last_at, Boolean is_read) {
        this.id = id;
        this.id_sender = id_sender;
        this.content = content;
        this.file = file;
        this.last_at = last_at;
        this.is_read = is_read;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_sender() {
        return id_sender;
    }

    public String getContent() {
        return content;
    }

    public String getFile() {
        return file;
    }

    public String getLast_at() {
        return last_at;
    }

    public Boolean getIs_read() {
        return is_read;
    }

    public void setIs_read(Boolean is_read) {
        this.is_read = is_read;
    }
}
