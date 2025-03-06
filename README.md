# 자바, 안드로이드, 스프링 부트서버를 이용한 일정관리앱(2조)

**-- 프로젝트 이름 : 일정관리앱**

**-- 기간 : 2/25~3/6(7일)**

**--팀원 : 🙍‍♂️나(백건우), 🙍‍♂️김지욱, 🙍‍♂️엄정현**

**--📅 담당역활 : 일정추가, 수정, 삭제, 조회, TODO_List**


## 목차

**• 1. 메인화면과 코드설명**

**• 2. 일정 생성화면과 코드설명(CREATE)**

**• 3. 일정 수정화면과 코드설명(EDIT)**

**• 4. 일정 삭제화면과 코드설명(DELETE)**

**• 5. 일정 조회화면과 코드설명(SEARCH)**

**• 6. 어려웠던 부분**


# • 1.메인 화면과 코드설명

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

• 얻어온 값은 **MyRvAdapter**로 전달하고 **MyRvAdapter**에 있는 **bindViewHolder, createViewHolder**...에 의해서 하단에 표시가 된다.

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

하단의 **'추가하기'** 와 **'조회하기'** 버튼은 2,5에서 설명할 예정이다.

  


