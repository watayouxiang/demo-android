package com.cniao5.rxjavaop;

/**
 * Created by Ivan on 2016/11/18.
 */

public class Course {


    public Course(){}

    public Course(String name) {
        this.name = name;
    }

    private  int id;
    private  String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
