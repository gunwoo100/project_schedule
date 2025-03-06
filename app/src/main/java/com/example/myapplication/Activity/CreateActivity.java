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

import com.example.myapplication.Class.ScheduleClass;
import com.example.myapplication.R;
import com.example.myapplication.Service.ScheduleService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateActivity extends AppCompatActivity{
    RadioButton rb_exercise,rb_meet,rb_rest,rb_study,rb_hobby;

    Button Btn_back,Btn_add;

    TextView tv_selected_category,tv_displayDate;

    EditText et_content;

    String selected_category,content;

    ScheduleService service;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_schedule);

        //Retrofit,Service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ScheduleService.class);


        //INTENT
        Intent intent = getIntent();
        int year = intent.getIntExtra("year",0);
        int month = intent.getIntExtra("month",0);
        int day = intent.getIntExtra("day",0);
        Log.v("TESTTAG$",year+"/"+month+"/"+day);


        //Button
        Btn_back = findViewById(R.id.back_button2);
        Btn_add = findViewById(R.id.add_Button);


        //RadioButton
        rb_exercise = findViewById(R.id.rb_exercise);
        rb_meet = findViewById(R.id.rb_meet);
        rb_rest = findViewById(R.id.rb_rest);
        rb_study = findViewById(R.id.rb_study);
        rb_hobby = findViewById(R.id.rb_hobby);


        //TextView
        tv_selected_category = findViewById(R.id.tv_selectedCategory);
        tv_displayDate = findViewById(R.id.tv_displayDate2);
        tv_displayDate.setText(year+"/"+month+"/"+day+" Schedule");


        //EditText
        et_content = findViewById(R.id.et_create_content);


        //onclick - RadioButton
        rb_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String exercise = rb_exercise.getText().toString();
                selected_category = "운동";
                Log.v("TESTTAG@",selected_category);
                tv_selected_category.setText(exercise);
            }
        });
        rb_meet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String meet = rb_meet.getText().toString();
                selected_category = "만남";
                Log.v("TESTTAG@",selected_category);
                tv_selected_category.setText(meet);
            }
        });
        rb_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rest = rb_rest.getText().toString();
                selected_category = "여가";
                Log.v("TESTTAG@",selected_category);
                tv_selected_category.setText(rest);
            }
        });
        rb_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String study = rb_study.getText().toString();
                selected_category = "공부";
                Log.v("TESTTAG@",selected_category);
                tv_selected_category.setText(study);
            }
        });
        rb_hobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hobby = rb_hobby.getText().toString();
                selected_category = "취미";
                Log.v("TESTTAG@",selected_category);
                tv_selected_category.setText(hobby);
            }
        });


        //onclick - Button
        Btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Btn_back.setBackgroundColor(Color.parseColor("#B2FFAF"));
                Intent intent = new Intent(CreateActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Btn_add.setBackgroundColor(Color.parseColor("#B2FFAF"));
                content = et_content.getText().toString();
                if (content.isEmpty() || selected_category==null){
                    Toast.makeText(CreateActivity.this, "현재 빈값이 존재합니다.", Toast.LENGTH_SHORT).show();
                }else{
                    ScheduleClass schedule = new ScheduleClass(content,selected_category,year,month,day);
                    Call<ScheduleClass> call = service.createSchedule(schedule);
                    call.enqueue(new Callback<ScheduleClass>() {
                        @Override
                        public void onResponse(Call<ScheduleClass> call, Response<ScheduleClass> response) {
                            if (response.isSuccessful()){
                                Intent intent = new Intent(CreateActivity.this,MainActivity.class);
                                Toast.makeText(CreateActivity.this, "일정추가 성공", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(CreateActivity.this, "일정추가 실패", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<ScheduleClass> call, Throwable t) {
                            Log.v("onFailure",t.getMessage());
                        }
                    });

                }

            }
        });    //CREATE_DATA

        //전달데이터 : year,month,day,content,selected_category

    }
}
