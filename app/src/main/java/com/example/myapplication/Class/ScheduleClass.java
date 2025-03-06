package com.example.myapplication.Class;

public class ScheduleClass {
    private String content;
    private String category;
    private int year;
    private int month;
    private int day;

    //CONSTRUCTOR
    public ScheduleClass(String content, String category, int year, int month, int day) {
        this.content = content;
        this.category = category;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    //GETTER
    public String getContent() {
        return content;
    }
    public String getCategory() {
        return category;
    }
    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public int getDay() {
        return day;
    }

    //SETTER
    public void setContent(String content) {
        this.content = content;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "ScheduleClass{" +
                "content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
