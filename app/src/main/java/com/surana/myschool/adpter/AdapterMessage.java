package com.surana.myschool.adpter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.surana.myschool.R;
import com.surana.myschool.item.ItemMessage;

import java.util.ArrayList;

public class AdapterMessage extends RecyclerView.Adapter<AdapterMessage.ViewHolder> {

    private ArrayList<ItemMessage> messageArrayList =new ArrayList<>();
    private Context context;

    public AdapterMessage(ArrayList<ItemMessage> messageArrayList,Context context) {
        this.messageArrayList = messageArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout_recycler_view,parent,false);
        AdapterMessage.ViewHolder holder = new AdapterMessage.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Boolean me = messageArrayList.get(position).getMe();
        String send_by = messageArrayList.get(position).getSend_by();
        String message = messageArrayList.get(position).getMessage();
        String date = messageArrayList.get(position).getDate();
        String time = messageArrayList.get(position).getTime();
        String type = messageArrayList.get(position).getType();

        if (me){
            holder.main_layout.setBackgroundColor(Color.parseColor("#228f24"));
            holder.imageView.setBackgroundColor(Color.parseColor("#228f24"));
            holder.send_by.setText("You");
            holder.message_layout.setGravity(Gravity.END);
        }else {
            holder.send_by.setText("~"+send_by);
        }

        if (position > 0 && position < messageArrayList.size()){
            if (date.equals(messageArrayList.get(position - 1).getDate())) {
                holder.date.setVisibility(View.GONE);
            }
        }

        if (type.equals("image")){
            holder.message.setVisibility(View.GONE);
            Glide.with(context).load(message).override(500).placeholder(R.drawable.ic_image_error).into(holder.imageView);
        }else if (type.equals("text")){
            holder.imageView.setVisibility(View.GONE);
            holder.message.setText(message);
        }

        holder.date.setText(date);
        holder.time.setText(time);

    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView message,time,date,send_by;
        LinearLayout main_layout,message_layout;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.message_layout_imageView);
            message = itemView.findViewById(R.id.message_layout_text);
            time = itemView.findViewById(R.id.message_layout_time);
            date = itemView.findViewById(R.id.message_layout_date);
            send_by = itemView.findViewById(R.id.message_layout_send_by);
            main_layout = itemView.findViewById(R.id.message_main);
            message_layout = itemView.findViewById(R.id.message_layout);
        }
    }

    public interface OnMessageListener{
        void onMessageClick(int position);
    }
}
