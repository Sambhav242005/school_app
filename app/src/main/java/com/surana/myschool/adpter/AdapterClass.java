package com.surana.myschool.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.surana.myschool.item.ItemClass;
import com.surana.myschool.R;

import java.util.ArrayList;
import java.util.Comparator;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder>{

    private ArrayList<ItemClass> class_array = new ArrayList<>();
    private OnClassListener mClassListener;

    public AdapterClass(ArrayList<ItemClass> class_array, OnClassListener mClassListener) {
        this.class_array = class_array;
        this.mClassListener = mClassListener;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_name_layout,parent,false);
        AdapterClass.ViewHolder holder = new AdapterClass.ViewHolder(view,mClassListener);
        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        holder.className.setText(class_array.get(position).getClass_name());
        holder.createBy.setText(class_array.get(position).getCreate_by());
    }

    @Override
    public int getItemCount() {
        return class_array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView className,createBy;
        OnClassListener onClassListener;


        public ViewHolder(View itemView,OnClassListener onClassListener) {
            super(itemView);
            className = itemView.findViewById(R.id.class_layout_name);
            createBy = itemView.findViewById(R.id.class_layout_create_by);

            this.onClassListener = onClassListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClassListener.onClassClick(getAdapterPosition());
        }
    }

    public interface OnClassListener{
        void onClassClick(int position);
    }

}
