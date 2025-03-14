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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Class.ScheduleClass;
import com.example.myapplication.R;
import com.example.myapplication.RvAdapter_ViewHolder.MainRvAdapter;
import com.example.myapplication.Service.ScheduleService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView tv_day,tv_display_date,tv_noSchedule;

    Button Btn_schedule,Btn_budget,Btn_ToDo;

    ImageView Btn_create,Btn_search,sad_image;

    RecyclerView rv;

    MainRvAdapter adapter;

    ScheduleService service;

    int selected_day,selected_month,selected_year;

    CalendarView calendar;

    ActivityResultLauncher<Intent> launcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Retrofit,Service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ScheduleService.class);


        //TextView
        tv_day = findViewById(R.id.tv_Day);
        tv_display_date = findViewById(R.id.tv_display_date);
        tv_noSchedule = findViewById(R.id.tv_noSchedule);

        //ImageView
        sad_image = findViewById(R.id.sad_image_main);

        //Calender
        calendar = findViewById(R.id.calendarView);

        //Calendar
        Calendar calendar1 = Calendar.getInstance();
        int cur_year = calendar1.get(Calendar.YEAR);
        int cur_month = calendar1.get(Calendar.MONTH)+1;
        int cur_day = calendar1.get(Calendar.DAY_OF_MONTH);
        displayData(cur_year,cur_month,cur_day);  //오늘 날짜 표시


        //DAY_DISPLAY(오늘 날짜 표시)
        {
            Date today = new Date();
            SimpleDateFormat format = new SimpleDateFormat("•MM/dd/EEE요일",getResources().getConfiguration().locale);
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
                selected_day = day;
                selected_month = month+1; //월은 0월부터 시작함
                selected_year = year;

                displayData(selected_year,selected_month,selected_day);

            }
        });


        //onclick - HeaderButton
        Btn_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SET_COLOR
                //ANIMATION
                {
                    // 커지는 애니메이션 생성
                    ScaleAnimation scaleUp = new ScaleAnimation(
                            1f, 1.2f, // X축 크기: 1배에서 1.5배로
                            1f, 1.2f, // Y축 크기: 1배에서 1.5배로
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f, // 기준점을 뷰의 중심으로 설정
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    scaleUp.setDuration(700);  // 0.5초 동안 커짐
                    scaleUp.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 작아지는 애니메이션 생성
                    ScaleAnimation scaleDown = new ScaleAnimation(
                            1.1f, 1f, // X축 크기: 1.5배에서 1배로
                            1.1f, 1f, // Y축 크기: 1.5배에서 1배로
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f, // 기준점을 뷰의 중심으로 설정
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    scaleDown.setDuration(400);  // 0.3초 동안 작아짐
                    scaleDown.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 애니메이션을 순차적으로 실행
                    Btn_budget.startAnimation(scaleUp);
                    Btn_budget.startAnimation(scaleDown);
                }

                Btn_budget.setBackgroundColor(Color.parseColor("#00FF6B"));
                Btn_schedule.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Btn_ToDo.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });
        Btn_schedule.setOnClickListener(new View.OnClickListener() {
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
                    scaleUp.setDuration(700);  // 0.5초 동안 커짐
                    scaleUp.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 작아지는 애니메이션 생성
                    ScaleAnimation scaleDown = new ScaleAnimation(
                            1.1f, 1f, // X축 크기: 1.5배에서 1배로
                            1.1f, 1f, // Y축 크기: 1.5배에서 1배로
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f, // 기준점을 뷰의 중심으로 설정
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    scaleDown.setDuration(400);  // 0.3초 동안 작아짐
                    scaleDown.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 애니메이션을 순차적으로 실행
                    Btn_schedule.startAnimation(scaleUp);
                    Btn_schedule.startAnimation(scaleDown);
                }

                //SET_COLOR
                Btn_budget.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Btn_schedule.setBackgroundColor(Color.parseColor("#00FF6B"));
                Btn_ToDo.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });
        Btn_ToDo.setOnClickListener(new View.OnClickListener() {
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
                    scaleUp.setDuration(700);  // 0.5초 동안 커짐
                    scaleUp.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 작아지는 애니메이션 생성
                    ScaleAnimation scaleDown = new ScaleAnimation(
                            1.1f, 1f, // X축 크기: 1.5배에서 1배로
                            1.1f, 1f, // Y축 크기: 1.5배에서 1배로
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f, // 기준점을 뷰의 중심으로 설정
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    scaleDown.setDuration(400);  // 0.3초 동안 작아짐
                    scaleDown.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 애니메이션을 순차적으로 실행
                    Btn_ToDo.startAnimation(scaleUp);
                    Btn_ToDo.startAnimation(scaleDown);
                }

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
                            1f, 1.2f, // X축 크기: 1배에서 1.5배로
                            1f, 1.2f, // Y축 크기: 1배에서 1.5배로
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f, // 기준점을 뷰의 중심으로 설정
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    scaleUp.setDuration(700);  // 0.5초 동안 커짐
                    scaleUp.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 작아지는 애니메이션 생성
                    ScaleAnimation scaleDown = new ScaleAnimation(
                            1.1f, 1f, // X축 크기: 1.5배에서 1배로
                            1.1f, 1f, // Y축 크기: 1.5배에서 1배로
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f, // 기준점을 뷰의 중심으로 설정
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    scaleDown.setDuration(400);  // 0.3초 동안 작아짐
                    scaleDown.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 애니메이션을 순차적으로 실행
                    Btn_create.startAnimation(scaleUp);
                    Btn_create.startAnimation(scaleDown);
                }

                if (selected_day==0||selected_month==0||selected_year==0){
                    Toast.makeText(MainActivity.this, "날자를 선택해 주세요", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "잠시만 기다려주세요", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,CreateActivity.class);
                    intent.putExtra("year",selected_year);
                    intent.putExtra("month",selected_month);
                    intent.putExtra("day",selected_day);

                    launcher.launch(intent);
                }
            }
        });
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result ->{
                    if (result.getResultCode() == MainActivity.RESULT_OK){
                        displayData(selected_year,selected_month,selected_day);
                    }
                });

        Btn_search.setOnClickListener(new View.OnClickListener() {
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
                    scaleUp.setDuration(700);  // 0.5초 동안 커짐
                    scaleUp.setFillAfter(true); // 애니메이션 후 최종 상태 유지

                    // 작아지는 애니메이션 생성
                    ScaleAnimation scaleDown = new ScaleAnimation(
                            1.1f, 1f, // X축 크기: 1.5배에서 1배로
                            1.1f, 1f, // Y축 크기: 1.5배에서 1배로
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

    //User Defined Function
    public void displayData(int year,int month,int day){
        Call<ArrayList<ScheduleClass>> call = service.getDataListByYMD(year, month, day);
        call.enqueue(new Callback<ArrayList<ScheduleClass>>() {
            @Override
            public void onResponse(Call<ArrayList<ScheduleClass>> call, Response<ArrayList<ScheduleClass>> response) {
                if (response.isSuccessful()){
                    if (response.body().isEmpty()){
                        sad_image.setVisibility(View.VISIBLE);
                        tv_noSchedule.setVisibility(View.VISIBLE);
                        tv_noSchedule.setText(year+"년 "+month+"월 "+day+"일의 일정이 없습니다.");
                    }
                    else{
                        sad_image.setVisibility(View.GONE);
                        tv_noSchedule.setVisibility(View.GONE);
                    }
                    Log.v("TAG#",response.isSuccessful()+"");
                    adapter = new MainRvAdapter(response.body(),tv_noSchedule,sad_image);
                    rv.setAdapter(adapter);
                    tv_display_date.setText(year+"/"+month+"/"+day+"의 일정");

                }
                else{
                    Toast.makeText(MainActivity.this, "서버와의 연결 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ScheduleClass>> call, Throwable t) {
                //Log.v("onFailure_Main",t.getMessage());
                Toast.makeText(MainActivity.this, "서버와의 연결 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
