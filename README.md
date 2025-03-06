# 자바, 안드로이드, 스프링 부트서버를 이용한 일정관리앱(2조)

**-- 프로젝트 이름 : 일정관리앱**

**-- 기간 : 2/25~3/6(7일)**

**--팀원 : 🙍‍♂️나(백건우), 🙍‍♂️김지욱, 🙍‍♂️엄정현**

**--📅 담당역활 : 일정추가, 수정, 삭제, 조회, TODO_List  [화면 5개, DB 2개]**

**--사용한 기능 : 안드로이드, 자바, postgresql(DataBase)**


## 목차

**• 1. 메인화면과 코드설명**

**• 2. 일정 생성화면과 코드설명(CREATE)**

**• 3. 일정 수정화면과 코드설명(EDIT)**

**• 4. 일정 삭제화면과 코드설명(DELETE)**

**• 5. 일정 조회화면과 코드설명(SEARCH)**

**• 6. 어려웠던 부분**


# • 1. 메인 화면과 코드설명

![main_display](https://github.com/user-attachments/assets/e2c6e7e4-caf3-49a7-8a0f-52959996dd57)

**달력📅**

    //Calender
    calendar = findViewById(R.id.calendarView);

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

• 달력에서 요일을 누르면 서버쪽에서 그 날짜에 사용자가 추가한 일정데이터를 **getDataListByYMD()** 통해 서버쪽에 호출해서 값을 얻어온다.

• 얻어온 값은 **MyRvAdapter**로 전달하고 **MyRvAdapter**에서는 **bindViewHolder, createViewHolder**...에 의해서 받아온 값이 하단에 표시가 된다.

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_main_basket,parent,false);
        return new MyViewHolder(view);
    }  
    
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ScheduleClass schedule = data.get(position);
        holder.tv_content.setText(schedule.getContent());
        holder.tv_category.setText(schedule.getCategory());
        ...

⬇️

![화면 캡처 2025-03-06 112859](https://github.com/user-attachments/assets/f721e778-a111-4a2d-942f-2491fbd3528d)

• 하단의 **'추가하기'** 와 **'조회하기'** 버튼은 2,5에서 설명할 예정이다.

# • 2. 일정생성화면과 코드설명

![create_display](https://github.com/user-attachments/assets/d10f98a1-a696-4b15-b1fe-f8a7b3ad7157)

• 메인화면에서 하단의 **'추가하기'** 버튼을 누르면 일정 생성하기 화면이 뜨고, 일정생성화면으로 넘어가면서 사용자가선택한 날짜값이 CreateActivity쪽으로 넘어간다. 

    //onclick - BottomButton
    Btn_create.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            *사용자가 날자를 선택했는지 확인한다*
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

**_• CreateActicity_**
  
    @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.create_schedule);
    
            //INTENT
            Intent intent = getIntent();
            int year = intent.getIntExtra("year",0);
            int month = intent.getIntExtra("month",0);
            int day = intent.getIntExtra("day",0);


• **일정을 추가할려면** 우선 사용자는 일정의 카테코리를 먼저 선택해야한다. 카테고리(RadioButton)를 누르면 오른쪽에 사용자가 선택한 카테고리가 표시된다.

• 그 다음에는 일정내용을 적어준 다음에 "추가하기"버튼을 누르면 성공적으로 추가가 된다.

![ezgif-6bceb3770a4d38](https://github.com/user-attachments/assets/8f39343f-7a28-49b7-ac92-d52782a8e40a) -->
![ezgif-6360f2c53e7647](https://github.com/user-attachments/assets/ad62dd5e-fe89-48a1-b05c-8170e0373f2e) -->
![ezgif-673dc6fc5bc7e6](https://github.com/user-attachments/assets/e3bd467e-dd64-414a-8327-74ed79bfab73)

•"추가하기"버튼을 누르면 서버쪽으로 **createSchedule()** 이 호출되면서 DB로 저장이 된다.

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

**_•DB_**

![image](https://github.com/user-attachments/assets/e896c265-601a-4b02-9ff8-83cbe677d4c3)


• _**만약에 사용자가 카테고리를 선택하지 않았거나, 일정내용을 적지 않았다면 "현제 빈 값이 존재합니다." 라고 경고문(Toast)이 뜬다**_

    content = et_content.getText().toString();
        if (content.isEmpty() || selected_category==null){
            Toast.makeText(CreateActivity.this, "현재 빈값이 존재합니다.", Toast.LENGTH_SHORT).show();
            }

# • 2. 일정수정화면과 코드설명

• 우선 일정을 수정할려면 메인화면에서 하단의 일정중 수정,삭제하고 싶은 일정을 누른 다음, 대화창이 뜨면 "수정하기"버튼을 누르면 수정화면으로 이동한다.

![ezgif-1456d6d5dbd58e](https://github.com/user-attachments/assets/7f7fb4ab-0983-484a-956f-3190c1c151e4)


• 이때 하단의 일정은 리사이클러뷰(MyRvAdapter)로 통해서 표시되기 때문에 **대화창이 화면에 표시되는 코드는 **MyRvAdapter** 에 있다**.
   
    ...
    holder.tv_category.setOnClickListener(new View.OnClickListener() {
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


            //onclick -- EDIT
            Btn_d_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), EditActivity.class);
                    intent.putExtra("EContent",content);
                    intent.putExtra("ECategory",category);
                    intent.putExtra("year",year);
                    intent.putExtra("month",month);
                    intent.putExtra("day",day);
                    view.getContext().startActivity(intent);
            
                }
            });

• 수정화면으로 넘어가면서 해당 일정의 내용와 카테고리정보가 **EditActivity** 쪽으로 넘어간다.

![edit_display](https://github.com/user-attachments/assets/9c0ae132-3a6e-4bcc-9111-ef6af9056663)

• 수정화면으로 넘어오면 내용과 카테고리를 변경해주고 "변경하기"버튼을 누르면 성공적으로 변경이 된다.

**_이때 수정내용과 카테고리중 하나라도 선택하지 않으면 "빈 값이 존재합니다"라고 경고문(Toast)이 화면에 표시된다._**

    if (et_content==null){
        Toast.makeText(EditActivity.this, "빈값이 존재합니다.", Toast.LENGTH_SHORT).show();
    }

![ezgif-5c7233e37bbfc8](https://github.com/user-attachments/assets/7f94fc64-aaaa-4aa4-b06d-f624ec0ae4ee)

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



