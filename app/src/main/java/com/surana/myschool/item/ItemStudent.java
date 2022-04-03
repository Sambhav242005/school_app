package com.surana.myschool.item;

import androidx.recyclerview.widget.RecyclerView;

public class ItemStudent {
    String student_name;
    String student_class;
    String student_roll_no;

    public ItemStudent(String student_name, String student_class, String student_roll_no) {
        this.student_name = student_name;
        this.student_class = student_class;
        this.student_roll_no = student_roll_no;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_class() {
        return student_class;
    }

    public void setStudent_class(String student_class) {
        this.student_class = student_class;
    }

    public String getStudent_roll_no() {
        return student_roll_no;
    }

    public void setStudent_roll_no(String student_roll_no) {
        this.student_roll_no = student_roll_no;
    }
}
