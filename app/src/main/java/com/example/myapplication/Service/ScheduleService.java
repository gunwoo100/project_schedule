package com.example.myapplication.Service;

import com.example.myapplication.Class.ScheduleClass;

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
    @GET("/rest/scheduleApp/schedule/getByYM/{year}/{month}")
    Call<ArrayList<ScheduleClass>> getDataListByYM(@Path("year")int year, @Path("month")int month);
    @GET("/rest/scheduleApp/schedule/getByYMD/{year}/{month}/{day}")
    Call<ArrayList<ScheduleClass>> getDataListByYMD(@Path("year")int year, @Path("month")int month, @Path("day")int day);
    @GET("/rest/scheduleApp/schedule/getByYMC/{year}/{month}/{category}")
    Call<ArrayList<ScheduleClass>> getDataListByYMC(@Path("year")int year, @Path("month")int month, @Path("category")String category);


    //CREATE
    @POST("/rest/scheduleApp/schedule/post")
    Call<ScheduleClass> createSchedule(@Body ScheduleClass schedule);


    //EDIT
    @PATCH("/rest/scheduleApp/schedule/edit/{id}")
    Call<Void> editSchedule(@Path("id")Long id,@Body ScheduleClass schedule);


    //DELETE
    @DELETE("/rest/scheduleApp/schedule/delete/{id}")
    Call<ArrayList<ScheduleClass>> deleteSchedule(@Path("id")Long id);

}
