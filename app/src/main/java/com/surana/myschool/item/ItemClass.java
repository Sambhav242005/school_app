package com.surana.myschool.item;

public class ItemClass {
    String class_name;
    String create_by;
    String token;

    public ItemClass(String class_name, String create_by, String token) {
        this.class_name = class_name;
        this.create_by = create_by;
        this.token = token;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
