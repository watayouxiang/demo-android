package com.wata.retrofitdemo;

import java.util.List;

/**
 * Created by TaoWang on 2017/7/13.
 */

public class Result {
    public String message;
    public String nu;
    public String ischeck;
    public String condition;
    public String com;
    public String status;
    public String state;
    public List<Message> data;

    @Override
    public String toString() {
        return "Result{" +
                "message='" + message + '\'' +
                ", nu='" + nu + '\'' +
                ", ischeck='" + ischeck + '\'' +
                ", condition='" + condition + '\'' +
                ", com='" + com + '\'' +
                ", status='" + status + '\'' +
                ", state='" + state + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}

class Message {
    public String time;
    public String ftime;
    public String context;
    public String location;

    @Override
    public String toString() {
        return "Message{" +
                "time='" + time + '\'' +
                ", ftime='" + ftime + '\'' +
                ", context='" + context + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}




/*
{
    "message":"参数错误",
    "nu":"",
    "ischeck":"0",
    "condition":"",
    "com":"",
    "status":"400",
    "state":"0",
    "data":[

    ]
}

{
    "message":"ok",
    "nu":"11111111111",
    "ischeck":"0",
    "condition":"00",
    "com":"yuantong",
    "status":"200",
    "state":"0",
    "data":[
        Object{...},
        Object{...},
        Object{...},
        {
            "time":"2017-06-07 11:21:03",
            "ftime":"2017-06-07 11:21:03",
            "context":"北服小北门圆通速递妈妈驿站已发出自提短信,请上门自提,如有疑问请联系13370110797",
            "location":null
        }
    ]
}


 */