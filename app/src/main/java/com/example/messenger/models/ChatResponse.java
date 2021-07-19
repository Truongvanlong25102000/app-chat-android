package com.example.messenger.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

@IgnoreExtraProperties
public class ChatResponse {
    private String id;
    private String content;
    private HashMap<String, String> file;
    private String id_reader;
    private String id_sender;
    private String last_at;

    public ChatResponse() {

    }

    public ChatResponse(String id, String content, HashMap<String, String> file, String id_reader, String id_sender, String last_at) {
        this.id = id;
        this.content = content;
        this.file = file;
        this.id_reader = id_reader;
        this.id_sender = id_sender;
        this.last_at = last_at;
    }


    public ChatResponse(String content, HashMap<String, String> file, String id_reader, String id_sender, String last_at) {
        this.content = content;
        this.file = file;
        this.id_reader = id_reader;
        this.id_sender = id_sender;
        this.last_at = last_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public HashMap<String, String> getFile() {
        return file;
    }

    public String getId_reader() {
        return id_reader;
    }

    public String getId_sender() {
        return id_sender;
    }

    public String getLast_at() {
        return last_at;
    }
}
