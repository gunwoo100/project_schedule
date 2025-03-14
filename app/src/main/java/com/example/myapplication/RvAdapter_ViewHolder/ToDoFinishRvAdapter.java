package com.example.myapplication.RvAdapter_ViewHolder;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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


public class ToDoFinishRvAdapter extends RecyclerView.Adapter<ToDoFinishViewHolder> {
    ArrayList<ToDoClass> data;

    public ToDoFinishRvAdapter(ArrayList<ToDoClass> data) {
        this.data = data;
    }

    public void updateData(ArrayList<ToDoClass> list){
        this.data = list;
    }


    @NonNull
    @Override
    public ToDoFinishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_todo_f_basket,parent,false);
        return new ToDoFinishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoFinishViewHolder holder, int position) {
        holder.tv_finish_content.setText(data.get(position).getTodo_content());
        holder.tv_finish_importance.setText(String.valueOf(data.get(position).getImportance()));

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AlertDialog 생성 및 설정
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View dialogView = inflater.inflate(R.layout.dialog_f_todo_delete,null);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();

                Button Btn_delete = dialogView.findViewById(R.id.f_todo_btn_delete);
                Button Btn_cancel = dialogView.findViewById(R.id.f_todo_btn_cancel);

                TextView tv_ToDo_content = dialogView.findViewById(R.id.tv_d_todo_content);
                TextView tv_ToDo_importance = dialogView.findViewById(R.id.tv_d_todo_importance);

                String todo_content = data.get(holder.getAdapterPosition()).getTodo_content();
                int todo_importance = data.get(holder.getAdapterPosition()).getImportance();


                tv_ToDo_content.setText(todo_content);
                tv_ToDo_importance.setText(todo_importance+" 점");

                Btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Long delete_ToDoId = data.get(holder.getAdapterPosition()).getTodo_id();

                        //Retrofit,Service
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://10.0.2.2:8080")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        ToDoService service = retrofit.create(ToDoService.class);

                        service.deleteToDoData(delete_ToDoId).enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.isSuccessful()){
                                    data.remove(holder.getAdapterPosition());
                                    notifyDataSetChanged();
                                    Toast.makeText(view.getContext(), "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Log.v("onFailure_ToDoFinishRvAdapter",t.getMessage());
                            }
                        });
                    }
                });
                Btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
