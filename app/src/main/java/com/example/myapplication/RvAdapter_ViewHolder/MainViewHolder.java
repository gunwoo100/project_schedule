package com.example.myapplication.RvAdapter_ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView tv_content,tv_category;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_category = itemView.findViewById(R.id.tv_category);
        tv_content = itemView.findViewById(R.id.tv_content);
    }
}
