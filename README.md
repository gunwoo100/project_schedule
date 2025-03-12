# 자바, 안드로이드, 스프링 부트,PostgreSQL를 이용한 일정관리앱(2조) 📋

**-- 프로젝트 이름 : 일정관리앱 📋**

**-- 걸린시간 ⏰ : 2/25~3/12(15일)**

**--팀원 : 🙍‍♂️나(백건우), 🙍‍♂️김지욱, 🙍‍♂️엄정현**

**--담당역활 📅  : 일정추가, 수정, 삭제, 조회, TODO_List  [화면 5개, DB 2개]**

**--사용한 기능 : 안드로이드, 자바, postgresql(DataBase), SpringBoot**


## 목차

**• 1. 메인화면과 코드설명**

**• 2. 일정 생성화면과 코드설명(CREATE)**

**• 3. 일정 수정화면과 코드설명(EDIT)**

**• 4. 일정 삭제화면과 코드설명(DELETE)**

**• 5. 일정 조회화면과 코드설명(SEARCH)**

**• 6. ToDoList화면과 코드설명**

**• 7. 그 외의 기능들**

**• 8. 어려웠던 부분**

**• 마무리**


# • 1. 메인 화면과 코드설명

![메인화면](https://github.com/user-attachments/assets/86f7536c-8fb7-4115-9b00-94f55687bc65)

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

![화면 캡처 2025-03-12 144011](https://github.com/user-attachments/assets/59f0171a-fca9-4d5a-b103-2b0a71fed6d1)

• 하단의 **'추가하기'** 와 **'조회하기'** 버튼은 2,5에서 설명할 예정이다.

# • 2. 일정생성화면과 코드설명

![create_display](https://github.com/user-attachments/assets/d10f98a1-a696-4b15-b1fe-f8a7b3ad7157)

• 메인화면에서 하단의 **'추가하기'** 버튼을 누르면 일정 생성하기 화면이 뜨고, 일정생성화면으로 넘어가면서 사용자가선택한 날짜값이 CreateActivity쪽으로 넘어간다. 

_**• MainActivity**_

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
    
            //INTENT - MainActivity
            Intent intent = getIntent();
            CreateDataYear = intent.getIntExtra("year",0);
            CreateDataMonth = intent.getIntExtra("month",0);
            CreateDataDay = intent.getIntExtra("day",0);


• **일정을 추가할려면** 우선 사용자는 일정의 카테코리를 먼저 선택해야한다. 카테고리(RadioButton)를 누르면 오른쪽에 사용자가 선택한 카테고리가 표시된다.

• 일정내용을 적어준 다음에 "추가하기"버튼을 누르면 성공적으로 추가가 된다.

![ezgif-6bceb3770a4d38](https://github.com/user-attachments/assets/8f39343f-7a28-49b7-ac92-d52782a8e40a) -->
![ezgif-6360f2c53e7647](https://github.com/user-attachments/assets/ad62dd5e-fe89-48a1-b05c-8170e0373f2e) -->
![ezgif-673dc6fc5bc7e6](https://github.com/user-attachments/assets/e3bd467e-dd64-414a-8327-74ed79bfab73)

•"추가하기"버튼을 누르면 서버쪽으로 **createSchedule()** 이 호출되면서 DB로 저장이 된다.

_**• CreateActivity**_

    Btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Animation
                ...

                CreateContent = et_content.getText().toString();
                if (CreateContent.isEmpty() || CreateCategory==null){
                    Toast.makeText(CreateActivity.this, "현재 빈값이 존재합니다.", Toast.LENGTH_SHORT).show();
                }else{
                    ScheduleClass schedule = new ScheduleClass(
                            CreateContent,
                            CreateCategory,
                            CreateDataYear,
                            CreateDataMonth,
                            CreateDataDay,
                            null);

                    Call<ScheduleClass> call = service.createSchedule(schedule);
                    call.enqueue(new Callback<ScheduleClass>() {
                        @Override
                        public void onResponse(Call<ScheduleClass> call, Response<ScheduleClass> response) {
                            if (response.isSuccessful()){
                                Intent intent1 = new Intent();
                                setResult(RESULT_OK,intent1);
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

• 일정이 추가되면 메인화면으로 넘어가서 추가된 일정을 **displayData()** 함수통해 메인에서 화면하단에 표시가 된다. 

• **displayData()** 함수가 실행이 되면 서버쪽으로 데이터를 다시 가지고 온 다음에 어뎁터로 전달해준다.

_**• MainActivity**_

    launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if (result.getResultCode() == MainActivity.RESULT_OK){
                    displayData(selected_year,selected_month,selected_day);
                }
            });

    public void displayData(int year,int month,int day){
        Call<ArrayList<ScheduleClass>> call = service.getDataListByYMD(year, month, day);
        call.enqueue(new Callback<ArrayList<ScheduleClass>>() {
            @Override
            public void onResponse(Call<ArrayList<ScheduleClass>> call, Response<ArrayList<ScheduleClass>> response) {
                if (response.isSuccessful()){
                    adapter = new MyRvAdapter(response.body());
                    rv.setAdapter(adapter);
                    tv_display_date.setText(year+"/"+month+"/"+day+"의 일정");

                }
            }

            @Override
            public void onFailure(Call<ArrayList<ScheduleClass>> call, Throwable t) {
                Log.v("onFailure",t.getMessage());
            }
        });        

**_•DB_**

![image](https://github.com/user-attachments/assets/e896c265-601a-4b02-9ff8-83cbe677d4c3)


• _**만약에 사용자가 카테고리를 선택하지 않았거나, 일정내용을 적지 않았다면 "현제 빈 값이 존재합니다." 라고 경고문(Toast)이 뜬다**_

_**• CreateActivity**_

    content = et_content.getText().toString();
        if (content.isEmpty() || selected_category==null){
            Toast.makeText(CreateActivity.this, "현재 빈값이 존재합니다.", Toast.LENGTH_SHORT).show();
            }

# • 3. 일정수정화면과 코드설명

• 우선 일정을 수정할려면 메인화면에서 하단의 일정중 수정,삭제하고 싶은 일정을 누른 다음, 대화창이 뜨면 "수정하기"버튼을 누르면 수정화면으로 이동한다.

![ezgif-1456d6d5dbd58e](https://github.com/user-attachments/assets/7f7fb4ab-0983-484a-956f-3190c1c151e4)


• 이때 하단의 일정은 리사이클러뷰(MyRvAdapter)로 통해서 표시되기 때문에 **대화창이 화면에 표시되는 코드는 **MyRvAdapter** 에 있다**.

_**• MyRvAdapter**_
   
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

• 수정화면으로 넘어가면서 해당 일정의 객체 데이터가 **EditActivity** 쪽으로 넘어간다.

**_• EditActivity_**

    //INTENT - By RecyclerView
    Intent intent = getIntent();
    schedule = (ScheduleClass) intent.getSerializableExtra("schedule");

![edit_display](https://github.com/user-attachments/assets/9c0ae132-3a6e-4bcc-9111-ef6af9056663)

• 내용과 카테고리를 변경해주고 "변경하기"버튼을 누르면 **editData()** 함수를 통해 성공적으로 변경이 된다.


**_• EditActivity_**

    Btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if -- Chang_Content is empty
                if (et_content==null){
                    Toast.makeText(EditActivity.this, "빈값이 존재합니다.", Toast.LENGTH_SHORT).show();
                }else{
                    //Animation
                    ...

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
                schedule.getId());

        //CALL
        Call<Void> call =  service.editSchedule(
                newSchedule.getId(),
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

• 변경하기 버튼을 누르면 서버쪽으로 **editSchedule()** 이 호출되면서 변경한 내용값을 통해 데이터의 값을 변경한다.

**_이때 수정내용이 빈값이거나 카테고리중 하나라도 선택되지 않으면 "빈 값이 존재합니다"라고 경고문(Toast)이 화면에 표시된다._**

    if (et_content==null){
        Toast.makeText(EditActivity.this, "빈값이 존재합니다.", Toast.LENGTH_SHORT).show();
    }

![ezgif-5c7233e37bbfc8](https://github.com/user-attachments/assets/7f94fc64-aaaa-4aa4-b06d-f624ec0ae4ee)

# • 4. 일정 삭제화면과 코드설명

• 일정을 삭제하려면 메인화면에서 하단의 일정중 삭제하고 싶은 일정을 클릭하면 대화상자가 나온다.

그런 다음에 "삭제하기" 버튼을 누르면 성공적으로 삭제가 된다.

![ezgif-5c20cc00c6fbec](https://github.com/user-attachments/assets/c6487021-5e64-4828-be78-cd35b9d10e68)

_• 일정삭제코드는 adapter쪽에 있다._

_**• MyRvAdapter**_

    Btn_d_delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(ScheduleService.class);

            Call<ArrayList<ScheduleClass>> call = service.deleteSchedule(schedule.getId());
            call.enqueue(new Callback<ArrayList<ScheduleClass>>() {
                @Override
                public void onResponse(Call<ArrayList<ScheduleClass>> call, Response<ArrayList<ScheduleClass>> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(view.getContext(), "성공", Toast.LENGTH_SHORT).show();
                        UpdateData(response.body());
                        dialog.dismiss();
                    }else{
                        Toast.makeText(view.getContext(), "실패", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ScheduleClass>> call, Throwable t) {
                    Log.v("onFailure",t.getMessage());
                }
            });


        }
    });

• "삭제하기"버튼을 누르면 해당 일정의 id값을 얻은 다음에 서버쪽으로 전달해서 전달받은 id값을 이용해 해당 데이터를 없앤다.

# • 5. 일정조회화면과 코드설명

• 하단의 "조회하기"버튼을 누르면 조회하기 화면으로 넘어간다.

![ezgif-7a0507e03e9189](https://github.com/user-attachments/assets/b0255713-aff7-45e4-91ed-6611b9a8b832)

• 여기서 사용자는 몇월달의 일정을 조회할건지와 어떤 일정을 조회할지 버튼(RadioButton)을 통해 선택한다.

• 선택을 다 하고 난 다음에 조회하기 버튼을 누르면 일정이 아래에 표시된다.

_**• SearchScheduleActivity**_

    search_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

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
                                    tv_display_category.setText(month+"월의 모든일정 목록 \uD83D\uDCCB");
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
                    Toast.makeText(SearchScheduleActivity.this, "조회 성공!", Toast.LENGTH_SHORT).show();

                    tv_display_category.setText(month+"월달의 "+category+"일정 목록");
                    Search_adapter = new SearchViewAdapter(response.body());
                    Search_rv.setAdapter(Search_adapter);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ScheduleClass>> call, Throwable t) {

            }
    });
 
• "서버쪽에서 해당 데이터를 가지고 와서 **setAdapter() (SearchViewAdapter)** 를 통해 하단에 표시해준다.

![ezgif-74cdb0626ea68e](https://github.com/user-attachments/assets/64e3b3ba-b69a-4f86-8089-3add809a168e)


• 하단의 일정을 클릭하면 대화창이 뜨고 삭제,수정이 가능하다.(삭제, 수정은 **3,4** 를 참고)

_**• SearchViewAdaper**_

    //onclick
    holder.layout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //onclick
            Btn_d_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), EditActivity.class);

                    intent.putExtra("EContent",content);
                    intent.putExtra("ECategory",category);
                    intent.putExtra("year",schedule.getYear());
                    intent.putExtra("month",schedule.getMonth());
                    intent.putExtra("day",schedule.getDay());

                    view.getContext().startActivity(intent);

                }
            });
            Btn_d_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Retrofit, Service
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://10.0.2.2:8080")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    service = retrofit.create(ScheduleService.class);

                    //Call
                    Call<ArrayList<ScheduleClass>> call = service.deleteSchedule(schedule.getId());
                    call.enqueue(new Callback<ArrayList<ScheduleClass>>() {
                        @Override
                        public void onResponse(Call<ArrayList<ScheduleClass>> call, Response<ArrayList<ScheduleClass>> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(view.getContext(), "성공", Toast.LENGTH_SHORT).show();
                                UpdateSearchData(response.body());
                                dialog.dismiss();
                            }else{
                                Toast.makeText(view.getContext(), "실패", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                        @Override
                        public void onFailure(Call<ArrayList<ScheduleClass>> call, Throwable t) {
                            Log.v("onFailure",t.getMessage());
                        }
                    });
                }
            });

# • 6. ToDoList화면과 코드설명

![화면 캡처 2025-03-12 103346](https://github.com/user-attachments/assets/b0ca22d9-d6fd-41f5-b92f-326a27088cb8)


• **_ToDoList_** 란? : 요일과 관계없는 일정을 추가할수 있는 화면이다.( ex)설거지하기, 장보러 갔다오기...)

• "추가하기"버튼을 누르면 대화창이 뜨고 추가하려고하는 일정의 내용과 중요도를 적은 다음에 추가하기를 누르면 목록에 저장이 된다.

_**• ToDoActivity**_

    //Button - onclick
        Btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ANIMATION
                ...

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
                EditText et_todo_importance = dialog_view.findViewById(R.id.et_todo_importance);


                //onclick - In Dialog
                add_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ToDO_content = et_todo_content.getText().toString();
                        String ToDo_str_importance = et_todo_importance.getText().toString();

                        if (isValidInput(ToDO_content,ToDo_str_importance)){
                            Toast.makeText(ToDoActivity.this, "잘못된 값이 존재합니다.", Toast.LENGTH_SHORT).show();
                        }
                         else {
                             int ToDo_importance = Integer.parseInt(ToDo_str_importance);
                             ToDoClass todo = new ToDoClass(ToDO_content,false,ToDo_importance,null);

                             //CALL
                             Call<ArrayList<ToDoClass>> call = ToDo_service.createToDoData(todo);
                             call.enqueue(new Callback<ArrayList<ToDoClass>>() {
                                 @Override
                                 public void onResponse(Call<ArrayList<ToDoClass>> call, Response<ArrayList<ToDoClass>> response) {
                                     if (response.isSuccessful()){
                                         ArrayList<ToDoClass> todoList = new ArrayList<>();

                                         for (int i = 0; i < response.body().size(); i++) {
                                             if (!response.body().get(i).isAchievement()) {
                                                 todoList.add(response.body().get(i));
                                             }
                                         }
                                         ToDo_adapter.UpdateData(todoList);
                                         ToDo_rv.setAdapter(ToDo_adapter);
                                         dialog.dismiss();
                                         Toast.makeText(ToDoActivity.this, "추가 성공", Toast.LENGTH_SHORT).show();
                                     }else{
                                         Toast.makeText(ToDoActivity.this, "서버와의 연결 실패", Toast.LENGTH_SHORT).show();
                                     }
                                 }
                                 @Override
                                 public void onFailure(Call<ArrayList<ToDoClass>> call, Throwable t) {
                                     Log.v("onFailure",t.getMessage());
                                 }
                             });
                         }
                    }
                });

![ezgif-55499eb94f2e15](https://github.com/user-attachments/assets/7327bb00-5170-4668-a9a9-c8a96fd61add)

• "추가"를 누르면 서버쪽으로 **createToDoData()** 가 호출이 되면서 DB에 저장이 된다.

• 일정을 끝냈다면 해당일정을 클릭하면 "완료된 일정"으로 넘어가면서 **ToDoData의 isAchievement값이 false-->true** 로 바뀐다.

• 그리고 하단의 새로고침버튼(🔁)을 누르면 완료한 일정이 하단에 표시가 된다.

_**• ToDoActivity**_

    Btn_refresh.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
                ...

            //CALL - getToDoData
            Call<ArrayList<ToDoClass>> call = service.getToDoData();
            call.enqueue(new Callback<ArrayList<ToDoClass>>() {
                @Override
                public void onResponse(Call<ArrayList<ToDoClass>> call, Response<ArrayList<ToDoClass>> response) {
                    ArrayList<ToDoClass> success_list = new ArrayList<>();

                    //for -- 일정이 완료된 데이터만
                    for (int i = 0; i < response.body().size(); i++) {
                        if (response.body().get(i).isAchievement()){
                        success_list.add(response.body().get(i));
                        }
                    }

                    //RecyclerView, Adapter 설정 및 생성
                    ToDoFinishRvAdapter finish_adapter = new ToDoFinishRvAdapter(success_list);
                    rv_finished_todo.setAdapter(finish_adapter);
                }

                @Override
                public void onFailure(Call<ArrayList<ToDoClass>> call, Throwable t) {
                    Log.v("onFailure",t.getMessage());
                }
            });

        }
    });

![ezgif-5c7f4a939e48f7](https://github.com/user-attachments/assets/1a3d0fa6-08ff-4a91-a4dd-6d22c4d34822)

_**• DB**_

![image](https://github.com/user-attachments/assets/f936d3d0-8d12-4296-a72e-df27f8a5c18a)

# • 7. 그 외의 기능들

• 버튼 애니메이션및 색상 변경

![ezgif-18a6cec22cc1b6](https://github.com/user-attachments/assets/6ac47b91-f129-43e2-af61-ce8ddd1fab5c)

    //ANIMATION
        {
        // 커지는 애니메이션 생성
        ScaleAnimation scaleUp = new ScaleAnimation(
        1f, 1.5f, // X축 크기: 1배에서 1.5배
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


•오늘 날짜 표시

![화면 캡처 2025-03-12 121841](https://github.com/user-attachments/assets/b026d672-62ce-41ad-b20a-47fad448df44)

    //DAY_DISPLAY(오늘 날짜 표시)
        {
            Date today = new Date();
            SimpleDateFormat format = new SimpleDateFormat("•MM/dd/EEE",getResources().getConfiguration().locale);
            String test = format.format(today);
            tv_day.setText(test);
        }


# • 8. 어려웠던 부분

**• 1. 엑티비티 화면 구성**

• 일정관리앱의 액티비티 화면과 ui배치는 각각 어떻게 해야 할지 잘 몰랐었고, 막상 만들어도 다시 수정하는 경우가 몇번 있어서 서버 구현하는 것보다 시간이 오래 걸렸다.

**• 2. 코드정리**

• ToDo화면 기능과 조회 기능을 만드는 과정에서 코드가 생각보다 복잡했고 변수명의 중복과 이름을 짓는데 어려움을 겪었다.

--> **액티비티 화면의 이름과 연관지어서 변수명을 지었고, 최대한 사용하지 않는 변수는 지우면서 코드를 정리했다**.  --> **전의 코트보다 가독성 ↑**

    //EditText - getText()
        EditContent = et_content.getText().toString();

**• 3. 2개의 RecyclerView활용**

• TodO화면에서 'ToDo일정'과 하단의 '완료된 일정'은 둘다 rv에 해당된다. 원래는 사용자가 'ToDo일정'에서 일정을 클릭하면 바로 '완료된 일정'에 표시되는 기능을 구현하려고 했지만 두가지 동시해 화면에 표시하려고  하니까 어떻게 해야 될지 감이 잡히지 않았다.

--> **'새로고침버튼'을 추가해서 adapter의 값을 버튼을 누를때마다 Update()를 통해 데이터가 변경되면서 화면에 표시하는 기능으로 변경** -->**성공**

    Btn_refresh.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {...

![화면 캡처 2025-03-11 173004](https://github.com/user-attachments/assets/8e0a3f08-1094-4f18-afb1-8ed5f8735e13)  ---->  ![화면 캡처 2025-03-11 173429](https://github.com/user-attachments/assets/45675852-1236-4097-8251-7e4bf3d73e47)

**• 4. HTTP 중 Edit,Delete 코드 구현**

• 데이터를 수정하고 삭제하는 요청코드를 작성하던도중 전달값을 year,month,day로 전달해서 만약 중복된 일정중 하나를 Edit,Delete하게 되면 두개의 데이터가 동시에 지워지는 문제점이 발생함

--> **id값을 클래스에 추가해서 "id"를 통해 해당데이터를 수정,삭제하도고 변경** --> **중복된 일정이 있어도 사용자가 선택한 일정만 지워진다.**

    public class ScheduleClass implements Serializable {               public class ToDoClass implements Serializable {
        private String content;                                            private String todo_content;   
        private String category;                                           private boolean achievement;
        private int year;                                                  private int importance;  
        private int month;                                                 **private Long todo_id;**
        private int day;                                                                
        **private Long id;**                                               ...
       
        ...
    }                                                                 }                                                          

그 이외에도 서버와의 통신 실패,데이터 저장 실패등이 있었다.

# • 마무리

지금까지 Project_schedule를 읽어 주셔서 감사합니다. 이번 프로젝트를 통해 서버와 안드로이드 통신하는 방법을 알게 되었고, 코딩속도와 사고력도 전 키오스크 프로젝트때보다 좋아진거 같습니다.

감사합니다. (～￣▽￣)～

    












