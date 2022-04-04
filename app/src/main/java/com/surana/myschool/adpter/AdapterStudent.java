package com.surana.myschool.adpter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.surana.myschool.item.ItemStudent;
import com.surana.myschool.R;

import java.util.ArrayList;

public class AdapterStudent extends RecyclerView.Adapter<AdapterStudent.ViewHolder>{

    private ArrayList<ItemStudent> students = new ArrayList<>();
    private Context context;

    public AdapterStudent(ArrayList<ItemStudent> students, Context context) {
        this.students = students;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        holder.name.setText(students.get(position).getStudent_name());
        holder.roll_no.setText(students.get(position).getStudent_roll_no());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,roll_no;
        public ViewHolder( View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.student_layout_name);
            roll_no = itemView.findViewById(R.id.student_activity_roll_no);

        }
    }

}
