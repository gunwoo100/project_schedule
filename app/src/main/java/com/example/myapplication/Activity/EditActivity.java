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

public class EditActivity extends AppCompatActivity {
    Button Btn_back,Btn_change;

    TextView tv_b_content,tv_b_category,tv_displayDate;

    EditText et_content;

    ScheduleService service;

    String change_content;

    RadioButton rb_exercise,rb_meet,rb_rest,rb_study,rb_hobby;

    int year,month,day;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_schedule);

        //Retrofit,Service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ScheduleService.class);


        //INTENT-By RecyclerView
        Intent intent = getIntent();
        String content = intent.getStringExtra("EContent");
        String category = intent.getStringExtra("ECategory");


        //DateData(Int)
        year = intent.getIntExtra("year",0);
        month = intent.getIntExtra("month",0);
        day = intent.getIntExtra("day",0);


        //TextView
        tv_b_content = findViewById(R.id.tv_b_beforeContent);
        tv_b_category = findViewById(R.id.tv_beforeCategory);
        tv_displayDate = findViewById(R.id.tv_displayDate);


        //RadioButton
        rb_exercise = findViewById(R.id.rb_edit_exercise);
        rb_meet = findViewById(R.id.rb_edit_meet);
        rb_hobby = findViewById(R.id.rb_edit_hobby);
        rb_rest = findViewById(R.id.rb_edit_rest);
        rb_study = findViewById(R.id.rb_edit_study);


        //변경전 일정내용과 카테고리를 edit화면에 표시
        tv_displayDate.setText(year+"/"+month+"/"+day+"의 일정");
        tv_b_content.setText(content);
        tv_b_category.setText(category);


        //EditText
        et_content = findViewById(R.id.et_content);


        //Button
        Btn_back = findViewById(R.id.back_button);
        Btn_change = findViewById(R.id.change_Button);


        //Button_onclick
        Btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SET_COLOR
                Btn_back.setBackgroundColor(Color.parseColor("#B2FFAF"));
                Btn_change.setBackgroundColor(Color.parseColor("#FFFFFF"));

                //INTENT
                Intent intent = new Intent(EditActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if -- Chang_Content is empty
                if (et_content==null){
                    Toast.makeText(EditActivity.this, "빈값이 존재합니다.", Toast.LENGTH_SHORT).show();
                }else{
                    //SET_COLOR
                    Btn_back.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    Btn_change.setBackgroundColor(Color.parseColor("#B2FFAF"));

                    change_content = et_content.getText().toString();

                    //RadioButton_isChecked()
                    if (rb_exercise.isChecked()){
                        ScheduleClass newSchedule = new ScheduleClass(change_content,
                            "운동",
                            year,
                            month,
                            day);

                        //Call
                        Call<Void> call =  service.editSchedule(
                                year,
                                month,
                                day,
                                tv_b_content.getText().toString(),
                                tv_b_category.getText().toString(),  //기존에있던 데이터를 찾기 위해서
                                newSchedule);

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(EditActivity.this, "변경성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EditActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(EditActivity.this, "변경실패", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.v("onFailure",t.getMessage());
                            }
                        });
                    }

                    else if (rb_meet.isChecked()) {
                        ScheduleClass newSchedule = new ScheduleClass(change_content,
                                "만남",
                                year,
                                month,
                                day);

                        //CALL
                        Call<Void> call =  service.editSchedule(
                                year,
                                month,
                                day,
                                tv_b_content.getText().toString(),
                                tv_b_category.getText().toString(),  //기존에있던 데이터를 찾기 위해서
                                newSchedule);

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(EditActivity.this, "변경성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EditActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(EditActivity.this, "변경실패", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.v("onFailure",t.getMessage());
                            }
                        });
                    }

                    else if (rb_hobby.isChecked()) {
                        ScheduleClass newSchedule = new ScheduleClass(change_content,
                                "취미",
                                year,
                                month,
                                day);

                        //CALL
                        Call<Void> call =  service.editSchedule(
                                year,
                                month,
                                day,
                                tv_b_content.getText().toString(),
                                tv_b_category.getText().toString(),  //기존에있던 데이터를 찾기 위해서
                                newSchedule);

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(EditActivity.this, "변경성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EditActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(EditActivity.this, "변경실패", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.v("onFailure",t.getMessage());
                            }
                        });
                    }

                    else if (rb_rest.isChecked()) {
                        ScheduleClass newSchedule = new ScheduleClass(change_content,
                                "여가",
                                year,
                                month,
                                day);

                        //CALL
                        Call<Void> call =  service.editSchedule(
                                year,
                                month,
                                day,
                                tv_b_content.getText().toString(),
                                tv_b_category.getText().toString(),  //기존에있던 데이터를 찾기 위해서
                                newSchedule);

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(EditActivity.this, "변경성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EditActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(EditActivity.this, "변경실패", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.v("onFailure",t.getMessage());
                            }
                        });
                    }

                    else if (rb_study.isChecked()) {
                        ScheduleClass newSchedule = new ScheduleClass(change_content,
                                "공부",
                                year,
                                month,
                                day);

                        //CALL
                        Call<Void> call =  service.editSchedule(
                                year,
                                month,
                                day,
                                tv_b_content.getText().toString(),
                                tv_b_category.getText().toString(),  //기존에있던 데이터를 찾기 위해서
                                newSchedule);

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(EditActivity.this, "변경성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EditActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(EditActivity.this, "변경실패", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.v("onFailure",t.getMessage());
                            }
                        });
                    }

                }
            }
        });

    }
}
