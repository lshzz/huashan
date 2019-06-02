package com.example.haha.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MybookActivity extends AppCompatActivity {

    private ImageView xxpic;
    private TextView xxbookname;
    private TextView xxzuoze;
    private TextView xxnn;
    private  TextView neirong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybook);
        xxpic= (ImageView) findViewById(R.id.xxpic);
        xxbookname= (TextView) findViewById(R.id.xxbookname);
        xxzuoze= (TextView) findViewById(R.id.xxzuoze);
        neirong= (TextView) findViewById(R.id.neirong);
        Intent intent=getIntent();
        int id=intent.getIntExtra("id",0);
        String[]  names=getResources().getStringArray(R.array.booknamearray);
        String[]  zuoze=getResources().getStringArray(R.array.zuozearray);
        String[]  xxnn=getResources().getStringArray(R.array.bookdetails);
        xxbookname.setText("书名："+names[id]);
        xxzuoze.setText("作者："+zuoze[id]);
        neirong.setText(xxnn[id]);
        if(id==0)   xxpic.setImageResource(R.drawable.java);
        if(id==1)   xxpic.setImageResource(R.drawable.cplus);
        if(id==2)   xxpic.setImageResource(R.drawable.andr);
        if(id==3)   xxpic.setImageResource(R.drawable.ui);
    }
}
