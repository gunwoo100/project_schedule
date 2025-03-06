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
    @GET("/rest/test/getByYM/{year}/{month}")
    Call<ArrayList<ScheduleClass>> getDataListByYM(@Path("year")int year, @Path("month")int month);
    @GET("/rest/test/getByYMD/{year}/{month}/{day}")
    Call<ArrayList<ScheduleClass>> getDataListByYMD(@Path("year")int year, @Path("month")int month, @Path("day")int day);
    @GET("/rest/test/getByYMC/{year}/{month}/{category}")
    Call<ArrayList<ScheduleClass>> getDataListByYMC(@Path("year")int year, @Path("month")int month, @Path("category")String category);


    //CREATE
    @POST("/rest/test/post")
    Call<ScheduleClass> createSchedule(@Body ScheduleClass schedule);


    //EDIT
    @PATCH("/rest/test/edit/{year}/{month}/{day}/{content}/{category}")
    Call<Void> editSchedule(@Path("year") int year,
                                     @Path("month") int month,
                                     @Path("day") int day,
                                     @Path("content") String content,
                                     @Path("category") String category,
                                     @Body ScheduleClass schedule);


    //DELETE
    @DELETE("/rest/test/edit/{year}/{month}/{day}/{content}/{category}")
    Call<Integer> deleteSchedule(@Path("year") int year,
                              @Path("month") int month,
                              @Path("day") int day,
                              @Path("content") String content,
                              @Path("category") String category);
}
