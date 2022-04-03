package com.surana.myschool;

public class ItemClass {
    String class_name;
    String latest_message;

    public ItemClass(String class_name, String latest_message) {
        this.class_name = class_name;
        this.latest_message = latest_message;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getLatest_message() {
        return latest_message;
    }

    public void setLatest_message(String latest_message) {
        this.latest_message = latest_message;
    }
}
