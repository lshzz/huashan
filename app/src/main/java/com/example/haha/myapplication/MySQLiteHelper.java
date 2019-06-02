package com.example.haha.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by haha on 2019/5/26.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static  String CREATE_TABLE_USER="create table users("+
            "id integer primary key autoincrement," +
            "username text,password text,usertype text)";
    private  Context sContext;

    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        sContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_USER);//创建表
        //添加记录用户登录认证
       db.execSQL("insert into users(username,password,usertype)values(?,?,?)",new String[]{"aaa","aaa","学生"});
        Toast.makeText(sContext,"成功创建数据表",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
        sqLiteDatabase.execSQL("drop table if exists types");
        onCreate(sqLiteDatabase);
    }
}
