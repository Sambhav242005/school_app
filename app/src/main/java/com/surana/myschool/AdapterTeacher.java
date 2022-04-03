package com.surana.myschool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterTeacher extends RecyclerView.Adapter<AdapterTeacher.ViewHolder> {

    private ArrayList<ItemTeacher> teacher = new ArrayList<>();
    private Context context;

    public AdapterTeacher(ArrayList<ItemTeacher> teacher, Context context) {
        this.teacher = teacher;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item,parent,false);
        AdapterTeacher.ViewHolder holder = new AdapterTeacher.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,class_name,roll_no;
        public ViewHolder( View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.student_layout_name);
            class_name = itemView.findViewById(R.id.student_layout_class);
            roll_no = itemView.findViewById(R.id.student_layout_roll_no);

        }
    }
}
