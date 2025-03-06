package com.example.myapplication.RvAdapter_ViewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class SearchViewHolder extends RecyclerView.ViewHolder {
    TextView tv_date,tv_category,tv_content;
    LinearLayout layout;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_date = itemView.findViewById(R.id.tv_date);
        tv_category = itemView.findViewById(R.id.tv_search_category);
        tv_content = itemView.findViewById(R.id.tv_search_content);
        layout = itemView.findViewById(R.id.bar);
    }
}
