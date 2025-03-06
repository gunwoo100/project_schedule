package com.example.myapplication.Activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Class.ScheduleClass;
import com.example.myapplication.R;
import com.example.myapplication.RvAdapter_ViewHolder.SearchViewAdapter;
import com.example.myapplication.Service.ScheduleService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchScheduleActivity extends AppCompatActivity {
    EditText et_month;

    RadioButton rb_month_exercise,rb_month_rest,rb_month_hobby,
                rb_month_study,rb_month_meet,rb_month_seeAll;

    Button search_button,back_button;

    TextView tv_month;

    ScheduleService service;

    RecyclerView rv;

    SearchViewAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_schedule);

        //Retrofit,Service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ScheduleService.class);


        //RadioButton
        rb_month_exercise = findViewById(R.id.rb_month_exercise);
        rb_month_rest = findViewById(R.id.rb_month_rest);
        rb_month_hobby = findViewById(R.id.rb_month_hobby);
        rb_month_study = findViewById(R.id.rb_month_study);
        rb_month_meet = findViewById(R.id.rb_month_meet);
        rb_month_seeAll = findViewById(R.id.rb_month_seeAll);


        //TextView
        tv_month = findViewById(R.id.tv_month);


        //EditText - 사용자가 입력하는 부분
        et_month = findViewById(R.id.et_month);


        //Button
        search_button = findViewById(R.id.button_search);
        back_button = findViewById(R.id.back_button1);


        //RecyclerView
        rv = findViewById(R.id.search_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));


        //onclick
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back_button.setBackgroundColor(Color.parseColor("#B2FFAF"));
                search_button.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Intent intent = new Intent(SearchScheduleActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SET_COLOR
                search_button.setBackgroundColor(Color.parseColor("#B2FFAF"));
                back_button.setBackgroundColor(Color.parseColor("#FFFFFF"));

                //if--No_CONTENT
                if (et_month.getText().toString().isEmpty()){
                    Toast.makeText(SearchScheduleActivity.this, "빈값이 존재합니다.", Toast.LENGTH_SHORT).show();
                }

                else{
                    int month = Integer.parseInt(et_month.getText().toString());

                    //if---Wrong Month
                    if ((month<1 || month>12 || et_month.getText().toString().isEmpty())){
                        Toast.makeText(SearchScheduleActivity.this, "잘못된 요일입니다.", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        if (rb_month_exercise.isChecked()){
                            Call<ArrayList<ScheduleClass>> call = service.getDataListByYMC(2025,month,"운동");
                            call.enqueue(new Callback<ArrayList<ScheduleClass>>() {
                                @Override
                                public void onResponse(Call<ArrayList<ScheduleClass>> call, Response<ArrayList<ScheduleClass>> response) {
                                    if (response.isSuccessful()){
                                        tv_month.setText(month+"월달의 운동일정 목록 ⚽");
                                        Toast.makeText(SearchScheduleActivity.this, "조회 성공!", Toast.LENGTH_SHORT).show();
                                        adapter.UpdateSearchData(response.body());
                                        rv.setAdapter(adapter);
                                    }
                                }
                                @Override
                                public void onFailure(Call<ArrayList<ScheduleClass>> call, Throwable t) {
                                    Log.v("onFailure",t.getMessage());
                                }

                            });


                        }

                        else if (rb_month_hobby.isChecked()) {
                            Call<ArrayList<ScheduleClass>> call = service.getDataListByYMC(2025,month,"취미");
                            call.enqueue(new Callback<ArrayList<ScheduleClass>>() {
                                @Override
                                public void onResponse(Call<ArrayList<ScheduleClass>> call, Response<ArrayList<ScheduleClass>> response) {
                                    if (response.isSuccessful()){
                                        tv_month.setText(month+"월달의 '취미'일정 목록 \uD83C\uDFA8");
                                        Toast.makeText(SearchScheduleActivity.this, "조회 성공!", Toast.LENGTH_SHORT).show();
                                        adapter.UpdateSearchData(response.body());
                                        rv.setAdapter(adapter);
                                    }
                                }
                                @Override
                                public void onFailure(Call<ArrayList<ScheduleClass>> call, Throwable t) {
                                    Log.v("onFailure",t.getMessage());
                                }

                            });

                        }

                        else if (rb_month_meet.isChecked()) {
                            Call<ArrayList<ScheduleClass>> call = service.getDataListByYMC(2025,month,"만남");
                            call.enqueue(new Callback<ArrayList<ScheduleClass>>() {
                                @Override
                                public void onResponse(Call<ArrayList<ScheduleClass>> call, Response<ArrayList<ScheduleClass>> response) {
                                    if (response.isSuccessful()){
                                        tv_month.setText(month+"월달의 만남일정 목록 \uD83E\uDD1D");
                                        Toast.makeText(SearchScheduleActivity.this, "조회 성공!", Toast.LENGTH_SHORT).show();
                                        adapter.UpdateSearchData(response.body());
                                        rv.setAdapter(adapter);
                                    }
                                }
                                @Override
                                public void onFailure(Call<ArrayList<ScheduleClass>> call, Throwable t) {

                                }

                            });

                        }

                        else if (rb_month_rest.isChecked()) {
                            Call<ArrayList<ScheduleClass>> call = service.getDataListByYMC(2025,month,"여가");
                            call.enqueue(new Callback<ArrayList<ScheduleClass>>() {
                                @Override
                                public void onResponse(Call<ArrayList<ScheduleClass>> call, Response<ArrayList<ScheduleClass>> response) {
                                    if (response.isSuccessful()){
                                        tv_month.setText(month+"월달의 여가시간 목록 ✨");
                                        Toast.makeText(SearchScheduleActivity.this, "조회 성공!", Toast.LENGTH_SHORT).show();
                                        adapter.UpdateSearchData(response.body());
                                        rv.setAdapter(adapter);
                                    }
                                }
                                @Override
                                public void onFailure(Call<ArrayList<ScheduleClass>> call, Throwable t) {

                                }

                            });

                        }

                        else if (rb_month_study.isChecked()) {
                            Call<ArrayList<ScheduleClass>> call = service.getDataListByYMC(2025,month,"공부");
                            call.enqueue(new Callback<ArrayList<ScheduleClass>>() {
                                @Override
                                public void onResponse(Call<ArrayList<ScheduleClass>> call, Response<ArrayList<ScheduleClass>> response) {
                                    if (response.isSuccessful()){
                                        tv_month.setText(month+"월달의 공부일정 목록 \uD83D\uDCDA");
                                        Toast.makeText(SearchScheduleActivity.this, "조회 성공!", Toast.LENGTH_SHORT).show();
                                        adapter.UpdateSearchData(response.body());
                                        rv.setAdapter(adapter);
                                    }
                                }
                                @Override
                                public void onFailure(Call<ArrayList<ScheduleClass>> call, Throwable t) {

                                }

                            });
                        }

                        else if(rb_month_seeAll.isChecked()){
                            tv_month.setText(month+"월의 모든일정 목록 \uD83D\uDCCB");

                            Call<ArrayList<ScheduleClass>> call = service.getDataListByYM(2025,month);
                            call.enqueue(new Callback<ArrayList<ScheduleClass>>() {
                                @Override
                                public void onResponse(Call<ArrayList<ScheduleClass>> call, Response<ArrayList<ScheduleClass>> response) {
                                    Log.v("TESTTAG!!!",response.isSuccessful()+"");
                                    if (response.isSuccessful()){
                                        adapter = new SearchViewAdapter(response.body());
                                        rv.setAdapter(adapter);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ArrayList<ScheduleClass>> call, Throwable t) {
                                    Log.v("onFailure",t.getMessage());
                                }
                            });
                        }

                        //RadioButton--isNotCheck
                        else{
                            Toast.makeText(SearchScheduleActivity.this, "선택되지 않은 카테고리가 있습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }

}
