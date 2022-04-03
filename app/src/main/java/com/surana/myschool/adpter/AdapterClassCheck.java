package com.surana.myschool.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.surana.myschool.R;
import com.surana.myschool.item.ItemClassCheck;

import java.util.ArrayList;

public class AdapterClassCheck extends RecyclerView.Adapter<AdapterClassCheck.ViewHolder>{

    private ArrayList<ItemClassCheck> itemClassCheckArrayList =new ArrayList<>();
    private Context context;

    public AdapterClassCheck(ArrayList<ItemClassCheck> itemClassCheckArrayList, Context context) {
        this.itemClassCheckArrayList = itemClassCheckArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_add_users_layout,parent,false);
        AdapterClassCheck.ViewHolder holder = new AdapterClassCheck.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        ItemClassCheck classAddUsers = itemClassCheckArrayList.get(position);

        holder.name.setChecked(classAddUsers.getCheck());

        holder.name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                classAddUsers.setCheck(b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemClassCheckArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox name;
        TextView create;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.add_users_all_class_name);
            create = itemView.findViewById(R.id.add_users_all_class_create);

        }
    }
}
