package com.example.myapplication.RvAdapter_ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ToDoFinishViewHolder extends RecyclerView.ViewHolder {
    TextView tv_finish_content;

    public ToDoFinishViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_finish_content = itemView.findViewById(R.id.tv_finish_content);
    }
}
