package com.example.myapplication.Service;

import com.example.myapplication.Class.ScheduleClass;
import com.example.myapplication.RvAdapter_ViewHolder.MainRvAdapter;

import java.util.ArrayList;

import retrofit2.Call;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

import retrofit2.http.Body;
import retrofit2.http.Path;

public interface ScheduleService {

    //GET
    /**SearchScheduleActivity - {@link com.example.myapplication.Activity.SearchScheduleActivity}*/
    @GET("/rest/scheduleApp/schedule/getByYM/{year}/{month}")
    Call<ArrayList<ScheduleClass>> getDataListByYM(@Path("year")int year, @Path("month")int month);

    /**SearchScheduleActivity - {@link com.example.myapplication.Activity.SearchScheduleActivity}*/
    @GET("/rest/scheduleApp/schedule/getByYMC/{year}/{month}/{category}")
    Call<ArrayList<ScheduleClass>> getDataListByYMC(@Path("year")int year, @Path("month")int month, @Path("category")String category);

    /**MainActivity - <b>displayDate()</b>*/
    @GET("/rest/scheduleApp/schedule/getByYMD/{year}/{month}/{day}")
    Call<ArrayList<ScheduleClass>> getDataListByYMD(@Path("year")int year, @Path("month")int month, @Path("day")int day);  //MainActivity - displayDate()


    //CREATE
    /**CreateActivity - {@link com.example.myapplication.Activity.CreateActivity}*/
    @POST("/rest/scheduleApp/schedule/create")
    Call<ScheduleClass> createSchedule(@Body ScheduleClass schedule);


    //EDIT
    /**EditActivity - {@link com.example.myapplication.Activity.EditActivity}*/
    @PATCH("/rest/scheduleApp/schedule/edit/{id}")
    Call<Integer> editSchedule(@Path("id")Long id,@Body ScheduleClass schedule);


    //DELETE
    /**SearchViewAdapter - {@link com.example.myapplication.RvAdapter_ViewHolder.SearchViewAdapter}<br>
     * MyRvAdapter - {@link com.example.myapplication.RvAdapter_ViewHolder.MainRvAdapter}*/
    @DELETE("/rest/scheduleApp/schedule/delete/{id}")
    Call<Integer> deleteSchedule(@Path("id")Long id);

}
