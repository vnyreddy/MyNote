package com.vinay.wizdem.mynote.DataBase;

/**
 * Created by vinay_1 on 12/29/2017.
 */

public class Note {
    private int _id;
    private String data;
    private String time;

    public Note(int _id, String data, String time){
        this._id = _id;
        this.data = data;
        this.time = time;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

