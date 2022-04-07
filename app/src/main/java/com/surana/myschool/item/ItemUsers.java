package com.surana.myschool.item;

public class ItemUsers {

    String token;
    String username;
    String rollNo;
    String create_by;
    String type;
    Boolean Add;

    public ItemUsers(String token, String username, String rollNo, String create_by, String type, Boolean add) {
        this.token = token;
        this.username = username;
        this.rollNo = rollNo;
        this.create_by = create_by;
        this.type = type;
        Add = add;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAdd() {
        return Add;
    }

    public void setAdd(Boolean add) {
        Add = add;
    }
}
