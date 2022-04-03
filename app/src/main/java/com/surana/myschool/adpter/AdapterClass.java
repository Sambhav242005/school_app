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

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder>{

    private ArrayList<ItemClass> class_array = new ArrayList<>();
    private Context context;

    public AdapterClass(ArrayList<ItemClass> class_array, Context context) {
        this.class_array = class_array;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_name_layout,parent,false);
        AdapterClass.ViewHolder holder = new AdapterClass.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        holder.className.setText(class_array.get(position).getClass_name());
        holder.latestMessage.setText(class_array.get(position).getLatest_message());
    }

    @Override
    public int getItemCount() {
        return class_array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView className,latestMessage;
        public ViewHolder(View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.class_layout_name);
            latestMessage = itemView.findViewById(R.id.class_layout_latest_message);
        }
    }

}
