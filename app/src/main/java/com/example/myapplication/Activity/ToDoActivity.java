package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Class.ToDoClass;
import com.example.myapplication.R;
import com.example.myapplication.RvAdapter_ViewHolder.ToDoFinishRvAdapter;
import com.example.myapplication.RvAdapter_ViewHolder.ToDoRvAdapter;
import com.example.myapplication.Service.ToDoService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ToDoActivity extends AppCompatActivity {

    ArrayList<ToDoClass> ToDoList,finish_list,success_list;

    ImageView Btn_add,Btn_back,Btn_refresh,Btn_question;

    RecyclerView rv_todo;
    ToDoRvAdapter adapter;

    ToDoService service;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_activity);

        ToDoList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ToDoService.class);




        //RecyclerView 생성 및 설정
        rv_todo = findViewById(R.id.rv_todo);
        rv_todo.setLayoutManager(new LinearLayoutManager(ToDoActivity.this));


        //RecyclerView 데이터 삽입
        Call<ArrayList<ToDoClass>> call = service.getToDoData();
        call.enqueue(new Callback<ArrayList<ToDoClass>>() {
            @Override
            public void onResponse(Call<ArrayList<ToDoClass>> call, Response<ArrayList<ToDoClass>> response) {
                if (response.isSuccessful()){
                    for (int i = 0; i < response.body().size(); i++) {
                        if (!response.body().get(i).isAchievement()){  //아직 하지 못한 일정만 추가하기
                            ToDoList.add(response.body().get(i));
                        }
                    }
                    adapter = new ToDoRvAdapter(ToDoList);
                    rv_todo.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ToDoClass>> call, Throwable t) {
                Log.v("onfailure",t.getMessage());
            }
        });


        //Button
        Btn_add = findViewById(R.id.Btn_add);
        Btn_back = findViewById(R.id.back_btn);
        Btn_refresh = findViewById(R.id.refresh);
        Btn_question = findViewById(R.id.qusetion_image);

        //Button - onclick
        Btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ANIMATION
                {
                    // 커지는 애니메이션 생성
                    ScaleAnimation scaleUp = new ScaleAnimation(
                            1f, 1.5f, // X축 크기: 1배에서 1.5배로
                            1f, 1.5f, // Y축 크기: 1배에서 1.5배로
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f, // 기준점을 뷰의 중심으로 설정
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    scaleUp.setDuration(200);  // 0.5초 동안 커짐
                    scaleUp.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 작아지는 애니메이션 생성
                    ScaleAnimation scaleDown = new ScaleAnimation(
                            1.3f, 1f, // X축 크기: 1.5배에서 1배로
                            1.3f, 1f, // Y축 크기: 1.5배에서 1배로
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f, // 기준점을 뷰의 중심으로 설정
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    scaleDown.setDuration(300);  // 0.3초 동안 작아짐
                    scaleDown.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 애니메이션을 순차적으로 실행
                    Btn_add.startAnimation(scaleUp);
                    Btn_add.startAnimation(scaleDown);
                }


                //Dialog 설정 및 생성
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View dialog_view = inflater.inflate(R.layout.todo_dialog,null);
                AlertDialog dialog = builder.create();
                dialog.setView(dialog_view);
                dialog.show();


                //Button
                Button add_button = dialog_view.findViewById(R.id.Btn_d_add);
                Button cancel_button = dialog_view.findViewById(R.id.Btn_d_cancel);


                //EditText
                EditText et_todo_content = dialog_view.findViewById(R.id.et_todo_content);


                //onclick - In Dialog
                add_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToDoClass todo = new ToDoClass(et_todo_content.getText().toString(),false);

                        //CALL
                        Call<Integer> call = service.createToDoData(todo);
                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.isSuccessful()){
                                    ToDoList.add(todo);
                                    adapter.UpdateData(ToDoList);
                                    rv_todo.setAdapter(adapter);

                                    dialog.dismiss();
                                    Toast.makeText(ToDoActivity.this, "추가 성공", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Log.v("onFailure",t.getMessage());
                            }
                        });
                    }
                });
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ANIMATION
                {// 회전 애니메이션 생성
                RotateAnimation rotate = new RotateAnimation(0f, 360f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(550);  // 회전 시간 1초
                rotate.setFillAfter(true);  // 애니메이션 후 최종 상태 유지

                // 애니메이션 시작
                Btn_refresh.startAnimation(rotate);}

                //CALL - getToDoData
                Call<ArrayList<ToDoClass>> call = service.getToDoData();
                call.enqueue(new Callback<ArrayList<ToDoClass>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ToDoClass>> call, Response<ArrayList<ToDoClass>> response) {
                        ArrayList<ToDoClass> success_list = new ArrayList<>();

                        //for -- Succeed ToDoData
                        for (int i = 0; i < response.body().size(); i++) {
                            if (response.body().get(i).isAchievement()){
                                success_list.add(response.body().get(i));
                            }
                        }

                        //RecyclerView, Adapter 설정 및 생성
                        ToDoFinishRvAdapter finish_adapter = new ToDoFinishRvAdapter(success_list);
                        RecyclerView finish_rv = findViewById(R.id.rv_finished);
                        finish_rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        finish_rv.setAdapter(finish_adapter);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ToDoClass>> call, Throwable t) {
                        Log.v("onFailure",t.getMessage());
                    }
                });

            }
        });
        Btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ANIMATION
                {
                    // 커지는 애니메이션 생성
                    ScaleAnimation scaleUp = new ScaleAnimation(
                            1f, 1.2f, // X축 크기: 1배에서 1.5배로
                            1f, 1.2f, // Y축 크기: 1배에서 1.5배로
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f, // 기준점을 뷰의 중심으로 설정
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    scaleUp.setDuration(200);  // 0.5초 동안 커짐
                    scaleUp.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 작아지는 애니메이션 생성
                    ScaleAnimation scaleDown = new ScaleAnimation(
                            1.2f, 1f, // X축 크기: 1.5배에서 1배로
                            1.2f, 1f, // Y축 크기: 1.5배에서 1배로
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f, // 기준점을 뷰의 중심으로 설정
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    scaleDown.setDuration(300);  // 0.3초 동안 작아짐
                    scaleDown.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 애니메이션을 순차적으로 실행
                    Btn_back.startAnimation(scaleUp);
                    Btn_back.startAnimation(scaleDown);
                }


                //INTENT
                Intent intent = new Intent(ToDoActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Btn_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ANIMATION
                {
                    // 커지는 애니메이션 생성
                    ScaleAnimation scaleUp = new ScaleAnimation(
                            1f, 1.5f, // X축 크기: 1배에서 1.5배로
                            1f, 1.5f, // Y축 크기: 1배에서 1.5배로
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f, // 기준점을 뷰의 중심으로 설정
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    scaleUp.setDuration(1000);  // 0.5초 동안 커짐
                    scaleUp.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 작아지는 애니메이션 생성
                    ScaleAnimation scaleDown = new ScaleAnimation(
                            1.4f, 1f, // X축 크기: 1.5배에서 1배로
                            1.4f, 1f, // Y축 크기: 1.5배에서 1배로
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f, // 기준점을 뷰의 중심으로 설정
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    scaleDown.setDuration(700);  // 0.3초 동안 작아짐
                    scaleDown.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 애니메이션을 순차적으로 실행
                    Btn_question.startAnimation(scaleUp);
                    Btn_question.startAnimation(scaleDown);
                }

                //Dialog 설정 및 생성
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View dialog_view = inflater.inflate(R.layout.todo_introduce_dialog,null);
                AlertDialog dialog = builder.create();
                dialog.setView(dialog_view);
                dialog.show();


                //Button
                Button Btn_confirm = dialog_view.findViewById(R.id.btn_confrim);

                //onclick
                Btn_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });






    }
}


