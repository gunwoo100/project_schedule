package com.example.myapplication.RvAdapter_ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Class.ToDoClass;
import com.example.myapplication.R;
import com.example.myapplication.Service.ToDoService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ToDoRvAdapter extends RecyclerView.Adapter<ToDoViewHolder> {
    ArrayList<ToDoClass> data;

    String finish_content;

    Retrofit retrofit;
    ToDoService service;



    public ToDoRvAdapter(ArrayList<ToDoClass> data) {
        this.data = data;

    }

    public void UpdateData(ArrayList<ToDoClass> data){
        this.data = data;
    }


    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_todo_basket,parent,false);
        return new ToDoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        holder.tv_todo_content.setText(data.get(position).getTodo_content());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish_content = holder.tv_todo_content.getText().toString();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getTodo_content().equals(holder.tv_todo_content.getText().toString())){

                        //Retrofit, Service
                        retrofit = new Retrofit.Builder()
                                .baseUrl("http://10.0.2.2:8080")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        service = retrofit.create(ToDoService.class);


                        //Call - Edit
                        Call<Integer> call = service.editToDoData(data.get(i));
                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                Toast.makeText(view.getContext(), "달성", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {

                            }
                        });

                        data.remove(i);
                        notifyDataSetChanged();
                        break;
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
