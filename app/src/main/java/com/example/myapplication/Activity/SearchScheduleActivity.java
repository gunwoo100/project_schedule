package com.example.myapplication.Activity;


import android.content.Intent;package com.example.myapplication.Activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.ScaleAnimation;
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
    EditText et_month,et_year;

    RadioButton rb_search_exercise,rb_search_rest,rb_search_hobby,
                rb_search_study,rb_search_meet,rb_search_seeAll;

    Button search_button,back_button;

    TextView tv_display_category;

    ScheduleService Schedule_service;

    RecyclerView Search_rv;

    SearchViewAdapter Search_adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_schedule);

        //Retrofit,Service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Schedule_service = retrofit.create(ScheduleService.class);


        //RadioButton
        rb_search_exercise = findViewById(R.id.rb_month_exercise);
        rb_search_rest = findViewById(R.id.rb_month_rest);
        rb_search_hobby = findViewById(R.id.rb_month_hobby);
        rb_search_study = findViewById(R.id.rb_month_study);
        rb_search_meet = findViewById(R.id.rb_month_meet);
        rb_search_seeAll = findViewById(R.id.rb_month_seeAll);


        //TextView
        tv_display_category = findViewById(R.id.tv_month);


        //EditText - 사용자가 입력하는 부분
        et_month = findViewById(R.id.et_month);
        et_year = findViewById(R.id.et_year);


        //Button
        search_button = findViewById(R.id.button_search);
        back_button = findViewById(R.id.back_button1);


        //RecyclerView
        Search_rv = findViewById(R.id.search_rv);
        Search_rv.setLayoutManager(new LinearLayoutManager(this));


        //onclick
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ANIMATION
                {
                    // 커지는 애니메이션 생성
                    ScaleAnimation scaleUp = new ScaleAnimation(
                            1f, 1.2f, // X축 크기: 1배에서 1.5배로
                            1f, 1.5f, // Y축 크기: 1배에서 1.5배로
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
                    back_button.startAnimation(scaleUp);
                    back_button.startAnimation(scaleDown);
                }
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
                {search_button.setBackgroundColor(Color.parseColor("#B2FFAF"));
                back_button.setBackgroundColor(Color.parseColor("#FFFFFF"));}

                //if--No_CONTENT
                if (et_month.getText().toString().isEmpty() || et_year.getText().toString().isEmpty()){
                    Toast.makeText(SearchScheduleActivity.this, "빈값이 존재합니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    int month = Integer.parseInt(et_month.getText().toString());
                    int year = Integer.parseInt(et_year.getText().toString());

                    //if---Wrong Month,Year
                    if (month<1 || month>12 || et_year.getText().toString().length()!=4 || year<2000 || year>=2300){
                        Toast.makeText(SearchScheduleActivity.this, "잘못된 요일입니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //ANIMATION
                        {
                            // 커지는 애니메이션 생성
                            ScaleAnimation scaleUp = new ScaleAnimation(
                                    1f, 1.2f, // X축 크기: 1배에서 1.5배로
                                    1f, 1.5f, // Y축 크기: 1배에서 1.5배로
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
                            search_button.startAnimation(scaleUp);
                            search_button.startAnimation(scaleDown);
                        }

                        if (rb_search_exercise.isChecked())    Search_Schedule(year, month,"운동");

                        else if (rb_search_hobby.isChecked())  Search_Schedule(year, month,"취미");

                        else if (rb_search_meet.isChecked())   Search_Schedule(year, month,"만남");

                        else if (rb_search_rest.isChecked())   Search_Schedule(year, month,"여가");

                        else if (rb_search_study.isChecked())  Search_Schedule(year, month,"공부");

                        else if(rb_search_seeAll.isChecked()) {
                            Call<ArrayList<ScheduleClass>> call = Schedule_service.getDataListByYM(year ,month);
                            call.enqueue(new Callback<ArrayList<ScheduleClass>>() {
                                @Override
                                public void onResponse(Call<ArrayList<ScheduleClass>> call, Response<ArrayList<ScheduleClass>> response) {
                                    if (response.isSuccessful()){
                                        tv_display_category.setText(year+"년 "+month+"월의 모든일정 목록 \uD83D\uDCCB");
                                        Search_adapter = new SearchViewAdapter(response.body());
                                        Search_rv.setAdapter(Search_adapter);
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

    public void Search_Schedule(int year, int month, String category){
        Call<ArrayList<ScheduleClass>> call = Schedule_service.getDataListByYMC(year,month,category);
        call.enqueue(new Callback<ArrayList<ScheduleClass>>() {
            @Override
            public void onResponse(Call<ArrayList<ScheduleClass>> call, Response<ArrayList<ScheduleClass>> response) {
                if (response.isSuccessful()){
                    Log.v("TESTTAGYEAR",year+"");
                    Toast.makeText(SearchScheduleActivity.this, "조회 성공!", Toast.LENGTH_SHORT).show();

                    tv_display_category.setText(year+"년 "+month+"월달의 "+category+"일정 목록");
                    Search_adapter = new SearchViewAdapter(response.body());
                    Search_rv.setAdapter(Search_adapter);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ScheduleClass>> call, Throwable t) {

            }

        });
    }

}
