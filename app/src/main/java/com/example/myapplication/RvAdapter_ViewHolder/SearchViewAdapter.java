package com.example.myapplication.RvAdapter_ViewHolder;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.EditActivity;
import com.example.myapplication.Class.ScheduleClass;
import com.example.myapplication.R;
import com.example.myapplication.Service.ScheduleService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchViewAdapter extends RecyclerView.Adapter<SearchViewHolder> {
    ArrayList<ScheduleClass> data;

    String category,content;

    ScheduleService service;

    public SearchViewAdapter(ArrayList<ScheduleClass> data) {
        this.data = data;
    }

    public void UpdateSearchData(ArrayList<ScheduleClass> data){
        this.data = data;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_month_basket,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        ScheduleClass schedule = data.get(position);
        holder.tv_date.setText(schedule.getYear()+"년 "+schedule.getMonth()+"월 "+schedule.getDay()+"일 ");
        holder.tv_category.setText(schedule.getCategory());
        holder.tv_content.setText(schedule.getContent());

        //onclick
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = holder.tv_category.getText().toString();
                content = holder.tv_content.getText().toString();

                //AlertDialog 생성 및 설정
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View dialogView = inflater.inflate(R.layout.edit_dialog,null);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();


                //TextView - In Dialog
                TextView tv_d_content = dialogView.findViewById(R.id.tv_d_content);
                TextView tv_d_category = dialogView.findViewById(R.id.tv_d_category);
                tv_d_content.setText(content);
                tv_d_category.setText(category);


                //Button - In Dialog
                Button Btn_d_edit = dialogView.findViewById(R.id.edit_button);
                Button Btn_d_delete = dialogView.findViewById(R.id.delete_button);
                Button Btn_d_cancel = dialogView.findViewById(R.id.cancel_button);


                //onclick
                Btn_d_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), EditActivity.class);

                        intent.putExtra("EContent",content);
                        intent.putExtra("ECategory",category);
                        intent.putExtra("year",schedule.getYear());
                        intent.putExtra("month",schedule.getMonth());
                        intent.putExtra("day",schedule.getDay());

                        view.getContext().startActivity(intent);

                    }
                });
                Btn_d_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://10.0.2.2:8080")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        service = retrofit.create(ScheduleService.class);

                        Call<Integer> call = service.deleteSchedule(schedule.getYear(), schedule.getMonth(), schedule.getDay(), content, category);
                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(view.getContext(), "성공", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else{
                                    Toast.makeText(view.getContext(), "실패", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Log.v("onFailure",t.getMessage());
                            }
                        });


                    }
                });
                Btn_d_cancel.setOnClickListener(new View.OnClickListener() {
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
