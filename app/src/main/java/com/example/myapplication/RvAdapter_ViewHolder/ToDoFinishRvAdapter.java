package com.example.myapplication.RvAdapter_ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Class.ToDoClass;
import com.example.myapplication.R;

import java.util.ArrayList;


public class ToDoFinishRvAdapter extends RecyclerView.Adapter<ToDoFinishViewHolder> {
    ArrayList<ToDoClass> data;

    public ToDoFinishRvAdapter(ArrayList<ToDoClass> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public ToDoFinishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_f_todo_basket,parent,false);
        return new ToDoFinishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoFinishViewHolder holder, int position) {
        holder.tv_finish_content.setText(data.get(position).getTodo_content());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
