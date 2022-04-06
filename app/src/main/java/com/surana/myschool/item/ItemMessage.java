package com.surana.myschool.item;

public class ItemMessage {
    String message;
    String send_by;
    String token;
    String type;
    String time;
    String date;
    Boolean me;

    public ItemMessage(String message, String send_by, String token, String type, String time, String date, Boolean me) {
        this.message = message;
        this.send_by = send_by;
        this.token = token;
        this.type = type;
        this.time = time;
        this.date = date;
        this.me = me;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSend_by() {
        return send_by;
    }

    public void setSend_by(String send_by) {
        this.send_by = send_by;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getMe() {
        return me;
    }

    public void setMe(Boolean me) {
        this.me = me;
    }
}
