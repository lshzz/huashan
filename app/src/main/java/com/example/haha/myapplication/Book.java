package com.example.haha.myapplication;

/**
 * Created by haha on 2019/5/26.
 */

public class Book {
    private int picId;
    private  String name;
    private  String zuoze;

    public Book(int picId, String name,String zuoze) {
        this.picId = picId;
        this.name = name;
        this.zuoze=zuoze;
    }

    public String getZuoze() {
        return zuoze;
    }

    public int getPicId() {
        return picId;
    }


    public String getName() {
        return name;
    }


}
