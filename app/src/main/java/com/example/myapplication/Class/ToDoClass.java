package com.example.myapplication.Class;

import java.io.Serializable;

public class ToDoClass implements Serializable {
    private String todo_content;
    private boolean achievement;

    public ToDoClass(String todo_content, boolean achievement) {
        this.todo_content = todo_content;
        this.achievement = achievement;
    }

    public String getTodo_content() {
        return todo_content;
    }

    public void setTodo_content(String todo_content) {
        this.todo_content = todo_content;
    }

    public boolean isAchievement() {
        return achievement;
    }

    public void setAchievement(boolean achievement) {
        this.achievement = achievement;
    }

    @Override
    public String toString() {
        return "ToDoClass{" +
                "todo_content='" + todo_content + '\'' +
                ", achievement=" + achievement +
                '}';
    }
}
