package com.example.haha.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserDataActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textdata;
    private Button chaxun;
    private Button huoqu;
    private Button jiexi;
    private Button daoru;
    private String jsondata;
    private MySQLiteHelper sqLiteHelper;
    private SQLiteDatabase myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        init();
        sqLiteHelper=new MySQLiteHelper(UserDataActivity.this,"usersdb.db",null,1);
        myDb=sqLiteHelper.getWritableDatabase();
        doUrlGet();
    }

    private void init() {
        textdata= (TextView) findViewById(R.id.text_data);
        chaxun= (Button) findViewById(R.id.chaxun);
        huoqu= (Button) findViewById(R.id.huoqu);
        jiexi= (Button) findViewById(R.id.jiexi);
        daoru= (Button) findViewById(R.id.daoru);
        daoru.setOnClickListener(this);
        huoqu.setOnClickListener(this);
        jiexi.setOnClickListener(this);
        chaxun.setOnClickListener(this);
    }
    private void doUrlGet()
    {
        try {

            OkHttpClient okClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url("http://192.168.1.100/usersdata.json");
            Request request=builder.build();
            Response response = okClient.newCall(request).execute();
            jsondata=response.body().string();


        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void showResult(final  String result)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textdata.setText(result);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chaxun:
                checkchaxun();
                break;
            case R.id.huoqu:
                checkhuoqu();
                showResult(jsondata);//显示数据
                break;
            case R.id.jiexi:
                JsonData(jsondata);
                break;
            case R.id.daoru:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        checkdaoru(jsondata);
                    }
                }).start();

                break;
        }

    }

    private void checkdaoru(String data) {
        try {
            JSONArray jsonArray=new JSONArray(data);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject!=null){
                    ContentValues cv=new ContentValues();
                    cv.put("username",jsonObject.getString("username"));
                    cv.put("password",jsonObject.getString("password"));
                    cv.put("usertype",jsonObject.getString("usertype"));
                    myDb.insert("users",null,cv);
                }
            }
            showResult(jsondata);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //解析Json数据
    private void JsonData(String data) {
        try {
            JSONArray jsonArray=new JSONArray(data);
            String result="";
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject!=null){
                    String username=jsonObject.getString("username");
                    String password=jsonObject.getString("password");
                    String usertype=jsonObject.getString("usertype");
                    result+=username+"  "+password+"  "+usertype+"\n";
                }
            }
            showResult(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkhuoqu() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                doUrlGet();
            }
        }).start();
    }

    //查询users表
    private void checkchaxun() {
        if(myDb==null)return;
        Cursor c=myDb.query("users",null,null,null,null,null,null);
        String msg="当前用户如下：\n";
        if(c.moveToFirst()){
            do{
                msg+="username:"+c.getString(c.getColumnIndex("username"))+"  "+
                        "password:"+c.getString(c.getColumnIndex("password"))+"  "+
                        "usertype:"+c.getString(c.getColumnIndex("usertype"))+"\n";
            }while (c.moveToNext());
        }
        showResult(msg);
    }
}
