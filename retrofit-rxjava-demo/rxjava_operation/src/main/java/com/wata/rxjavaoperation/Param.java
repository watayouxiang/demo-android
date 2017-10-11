package com.wata.rxjavaoperation;

/**
 * Created by TaoWang on 2017/7/13.
 */

public class Param {
    public Param() {

    }

    public Param(String type, String postid) {
        this.type = type;
        this.postid = postid;
    }

    private String type;
    private String postid;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    @Override
    public String toString() {
        return "Param{" +
                "type='" + type + '\'' +
                ", postid='" + postid + '\'' +
                '}';
    }
}
