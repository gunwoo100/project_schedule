package com.example.myapplication.Activity;

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

    EditText et_content;

    ScheduleService service;

    String EditContent;

    RadioButton rb_exercise,rb_meet,rb_rest,rb_study,rb_hobby;

    ScheduleClass schedule;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);

        //Retrofit,Service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ScheduleService.class);


        //INTENT - By RecyclerView
        Intent intent = getIntent();
        schedule = (ScheduleClass) intent.getSerializableExtra("schedule");


        //TextView
        TextView tv_b_content = findViewById(R.id.tv_b_beforeContent);
        TextView tv_b_category = findViewById(R.id.tv_beforeCategory);
        TextView tv_displayDate = findViewById(R.id.tv_displayDate);


        //변경전 일정내용과 카테고리를 edit화면에 표시
        tv_displayDate.setText(schedule.getYear()+"/"+schedule.getMonth()+"/"+schedule.getDay()+"의 일정");
        tv_b_content.setText(schedule.getContent());
        tv_b_category.setText(schedule.getCategory());


        //RadioButton
        rb_exercise = findViewById(R.id.rb_edit_exercise);
        rb_meet = findViewById(R.id.rb_edit_meet);
        rb_hobby = findViewById(R.id.rb_edit_hobby);
        rb_rest = findViewById(R.id.rb_edit_rest);
        rb_study = findViewById(R.id.rb_edit_study);


        //EditText
        et_content = findViewById(R.id.et_content);


        //Button
        Btn_back = findViewById(R.id.back_button);
        Btn_change = findViewById(R.id.change_Button);


        //Button_onclick
        Btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Animation
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
                    Btn_back.startAnimation(scaleUp);
                    Btn_back.startAnimation(scaleDown);
                }


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
                    //Animation
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
                        Btn_change.startAnimation(scaleUp);
                        Btn_change.startAnimation(scaleDown);
                    }

                    //SET_COLOR
                    Btn_back.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    Btn_change.setBackgroundColor(Color.parseColor("#B2FFAF"));


                    //EditText - getText()
                    EditContent = et_content.getText().toString();


                    //RadioButton_isChecked()
                    if (rb_exercise.isChecked())    editData("운동", EditContent);
                    else if (rb_meet.isChecked())   editData("만남", EditContent);
                    else if (rb_hobby.isChecked())  editData("취미", EditContent);
                    else if (rb_rest.isChecked())   editData("여가", EditContent);
                    else if (rb_study.isChecked())  editData("공부", EditContent);

                }
            }
        });


    }

    public void editData(String c_category,String c_content){
        ScheduleClass newSchedule = new ScheduleClass(c_content,
                c_category,
                schedule.getYear(),
                schedule.getMonth(),
                schedule.getDay(),
                schedule.getId(),
                false);

        //CALL
        Call<Integer> call =  service.editSchedule(
                newSchedule.getId(),
                newSchedule);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
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
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.v("onFailure",t.getMessage());
            }
        });
    }


}
