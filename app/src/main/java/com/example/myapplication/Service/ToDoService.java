package com.example.myapplication.Service;

import com.example.myapplication.Class.ToDoClass;

import java.util.ArrayList;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface ToDoService {

    @POST("/rest/test/create")
    Call<Integer> createToDoData(@Body ToDoClass todo);

    @GET("/rest/test/getToDo")
    Call<ArrayList<ToDoClass>> getToDoData();

    @PATCH("/rest/test/edit")
    Call<Integer> editToDoData(@Body ToDoClass todo);

}
