package com.example.myapplication.RvAdapter_ViewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ToDoViewHolder extends RecyclerView.ViewHolder {
    TextView tv_todo_content;
    LinearLayout layout;

    public ToDoViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_todo_content = itemView.findViewById(R.id.tv_todo_content);
        layout = itemView.findViewById(R.id.bar2);
    }
}
