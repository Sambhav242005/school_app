package com.surana.myschool.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.surana.myschool.R;
import com.surana.myschool.item.ItemUsers;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.ViewHolder>{

    ArrayList<ItemUsers> usersArrayList = new ArrayList<>();
    Context context;

    public AdapterUsers(ArrayList<ItemUsers> usersArrayList, Context context) {
        this.usersArrayList = usersArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_layout_select,parent,false);
        AdapterUsers.ViewHolder holder = new AdapterUsers.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String type = usersArrayList.get(position).getType();
        String username =usersArrayList.get(position).getUsername();
        String create_by = usersArrayList.get(position).getCreate_by();
        String roll_no = usersArrayList.get(position).getRollNo();

        holder.type.setText(type);
        holder.username.setText(username);
        holder.create_by.setText(create_by);
        holder.roll_no.setText(roll_no);
        holder.username.setChecked(usersArrayList.get(position).getAdd());

        if (type.equals("teacher")){
            holder.roll_no.setVisibility(View.GONE);
        }


        holder.username.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                usersArrayList.get(position).setAdd(b);
            }
        });

    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox username;
        TextView create_by,type,roll_no;
        public ViewHolder(View itemView) {
            super(itemView);
            roll_no = itemView.findViewById(R.id.users_layout_roll_no);
            username = itemView.findViewById(R.id.users_layout_username);
            type = itemView.findViewById(R.id.users_layout_type);
            create_by = itemView.findViewById(R.id.users_layout_create_by);

        }
    }
}
