package com.surana.myschool.item;

public class ItemClassAddUsers {

    String className;
    String classCreate;
    Boolean isCheck;

    public ItemClassAddUsers(String className, String classCreate, Boolean isCheck) {
        this.className = className;
        this.classCreate = classCreate;
        this.isCheck = isCheck;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassCreate() {
        return classCreate;
    }

    public void setClassCreate(String classCreate) {
        this.classCreate = classCreate;
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }
}
