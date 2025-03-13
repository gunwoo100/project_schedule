package com.example.myapplication.Service;

import com.example.myapplication.Class.ToDoClass;

import java.util.ArrayList;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ToDoService {

    @POST("/rest/scheduleApp/todo/create")
    Call<ArrayList<ToDoClass>> createToDoData(@Body ToDoClass todo);

    @GET("/rest/scheduleApp/todo/get")
    Call<ArrayList<ToDoClass>> getToDoData();

    @PATCH("/rest/scheduleApp/todo/edit/{id}")
    Call<ArrayList<ToDoClass>> editToDoData(@Path("id") Long todo_id);

    @DELETE("/rest/scheduleApp/todo/delete/{id}")
    Call<ArrayList<ToDoClass>> deleteToDoData(@Path("id") Long id);

}

