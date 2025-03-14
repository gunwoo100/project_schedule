package com.example.myapplication.RvAdapter_ViewHolder;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.EditActivity;
import com.example.myapplication.R;
import com.example.myapplication.Class.ScheduleClass;
import com.example.myapplication.Service.ScheduleService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;package com.example.myapplication.RvAdapter_ViewHolder;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.EditActivity;
import com.example.myapplication.R;
import com.example.myapplication.Class.ScheduleClass;
import com.example.myapplication.Service.ScheduleService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainRvAdapter extends RecyclerView.Adapter<MainViewHolder> {

    ArrayList<ScheduleClass> data;

    //if - No Schedule Data
    TextView tv_noSchedule;
    ImageView sad_image;

    ScheduleService service;


    public MainRvAdapter(ArrayList<ScheduleClass> data, TextView tv_noSchedule, ImageView sad_image) {
        this.data = data;
        this.tv_noSchedule = tv_noSchedule;
        this.sad_image = sad_image;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_main_basket,parent,false);
        return new MainViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        ScheduleClass schedule = data.get(position);
        for (int i = 0; i < data.size(); i++) {
            if (schedule.isAchievement()){
                holder.layout.setBackgroundResource(R.drawable.bolder7);
            }else{
                holder.layout.setBackgroundResource(R.drawable.bolder);
            }
        }
        holder.tv_content.setText(data.get(position).getContent());
        holder.tv_category.setText(data.get(position).getCategory());



        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ScheduleClass schedule = data.get(holder.getAdapterPosition());
                String category = holder.tv_category.getText().toString();
                String content = holder.tv_content.getText().toString();


                //AlertDialog 생성 및 설정
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View dialogView = inflater.inflate(R.layout.dialog_main,null);
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
                        intent.putExtra("schedule",schedule);
                        view.getContext().startActivity(intent);
                        dialog.dismiss();
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

                        Call<Integer> call = service.deleteSchedule(schedule.getId());
                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(view.getContext(), "성공", Toast.LENGTH_SHORT).show();
                                    data.remove(holder.getAdapterPosition());
                                    notifyDataSetChanged();
                                    if (data.isEmpty()){
                                        sad_image.setVisibility(View.VISIBLE);
                                        tv_noSchedule.setVisibility(View.VISIBLE);
                                    }
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

                return false;

            }

        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8080")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                service = retrofit.create(ScheduleService.class);

                Call<Integer> call = service.editIsAchievement(data.get(holder.getAdapterPosition()).getId());
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful()){
                            Log.v("CALL","");
                            holder.layout.setBackgroundResource(R.drawable.bolder7);
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

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
