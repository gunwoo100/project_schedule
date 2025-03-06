package com.example.myapplication.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Class.ScheduleClass;
import com.example.myapplication.R;
import com.example.myapplication.RvAdapter_ViewHolder.MyRvAdapter;
import com.example.myapplication.Service.ScheduleService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView tv_day;

    Button Btn_schedule,Btn_budget,Btn_ToDo;

    ImageView Btn_create,Btn_search;

    RecyclerView rv;

    MyRvAdapter adapter;

    ScheduleService service;

    int date_day,date_month,date_year;

    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_display);

        //Retrofit,Service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ScheduleService.class);


        //TextView
        tv_day = findViewById(R.id.tv_Day);

        //Calender
        calendar = findViewById(R.id.calendarView);


        //DAY_DISPLAY(오늘 날짜 표시)
        {
            Date today = new Date();
            SimpleDateFormat format = new SimpleDateFormat("•MM/dd/EEE",getResources().getConfiguration().locale);
            String test = format.format(today);
            tv_day.setText(test);
        }


        //BUTTON
        Btn_create = findViewById(R.id.create_button);
        Btn_schedule = findViewById(R.id.schedule_button);
        Btn_budget = findViewById(R.id.budget_button);
        Btn_ToDo = findViewById(R.id.ToDo_button);
        Btn_search = findViewById(R.id.search_button);


        //recyclerview
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));


        //Calender - onclick
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String date = year+"-"+(month+1)+"-"+day;
                Toast.makeText(MainActivity.this, date, Toast.LENGTH_SHORT).show();
                date_day = day;
                date_month = month+1; //월은 0월부터 시작함
                date_year = year;

                Call<ArrayList<ScheduleClass>> call = service.getDataListByYMD(date_year,date_month,date_day);
                call.enqueue(new Callback<ArrayList<ScheduleClass>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ScheduleClass>> call, Response<ArrayList<ScheduleClass>> response) {
                        if (response.isSuccessful()){
                            ArrayList<ScheduleClass> list = response.body();
                            adapter = new MyRvAdapter(list,date_year,date_month,date_day);
                            rv.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ScheduleClass>> call, Throwable t) {
                        Log.v("onFailure2",t.getMessage());
                    }
                });

            }
        });


        //onclick - HeaderButton
        Btn_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SET_COLOR
                Btn_budget.setBackgroundColor(Color.parseColor("#00FF6B"));
                Btn_schedule.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Btn_ToDo.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });
        Btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SET_COLOR
                Btn_budget.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Btn_schedule.setBackgroundColor(Color.parseColor("#00FF6B"));
                Btn_ToDo.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });
        Btn_ToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SET_COLOR
                Btn_budget.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Btn_schedule.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Btn_ToDo.setBackgroundColor(Color.parseColor("#00FF6B"));

                Toast.makeText(MainActivity.this, "잠시만 기다려주세요", Toast.LENGTH_SHORT).show();


                //INTENT
                Intent intent = new Intent(MainActivity.this,ToDoActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //onclick - BottomButton
        Btn_create.setOnClickListener(new View.OnClickListener() {
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
                    scaleUp.setDuration(700);  // 0.5초 동안 커짐
                    scaleUp.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 작아지는 애니메이션 생성
                    ScaleAnimation scaleDown = new ScaleAnimation(
                            1.4f, 1f, // X축 크기: 1.5배에서 1배로
                            1.4f, 1f, // Y축 크기: 1.5배에서 1배로
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f, // 기준점을 뷰의 중심으로 설정
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    scaleDown.setDuration(400);  // 0.3초 동안 작아짐
                    scaleDown.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 애니메이션을 순차적으로 실행
                    Btn_create.startAnimation(scaleUp);
                    Btn_create.startAnimation(scaleDown);
                }

                if (date_day==0||date_month==0||date_year==0){
                    Toast.makeText(MainActivity.this, "날자를 선택해 주세요", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "잠시만 기다려주세요", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,CreateActivity.class);
                    intent.putExtra("year",date_year);
                    intent.putExtra("month",date_month);
                    intent.putExtra("day",date_day);
                    startActivity(intent);
                    finish();
                }
            }
        });
        Btn_search.setOnClickListener(new View.OnClickListener() {
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
                    scaleUp.setDuration(700);  // 0.5초 동안 커짐
                    scaleUp.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 작아지는 애니메이션 생성
                    ScaleAnimation scaleDown = new ScaleAnimation(
                            1.4f, 1f, // X축 크기: 1.5배에서 1배로
                            1.4f, 1f, // Y축 크기: 1.5배에서 1배로
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f, // 기준점을 뷰의 중심으로 설정
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    scaleDown.setDuration(400);  // 0.3초 동안 작아짐
                    scaleDown.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 애니메이션을 순차적으로 실행
                    Btn_search.startAnimation(scaleUp);
                    Btn_search.startAnimation(scaleDown);
                }

                Toast.makeText(MainActivity.this, "잠시만 기다려주세요", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, SearchScheduleActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}