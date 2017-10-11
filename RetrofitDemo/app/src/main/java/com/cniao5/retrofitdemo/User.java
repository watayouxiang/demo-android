package com.cniao5.retrofitdemo;

/**
 * Created by Ivan on 2016/10/7.
 */

public class User {
    /**
     * head_url : http://cniao5-imgs.qiniudn.com/FmmzD0PdroWkFzqVFEUTKO-BQqOP
     * id : 0
     * username : cniao5
     */

    private String head_url;
    private String id;
    private String username;


    public User(String head_url, String id, String username) {
        this.head_url = head_url;
        this.id = id;
        this.username = username;
    }

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public User() {
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
