package com.example.haha.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RegActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText regusername;
    private  EditText regpwd;
    private Button regbt;
    private Button regfh;
    private RadioGroup radioGroup;
    private RadioButton rb;
    private MySQLiteHelper sqLiteHelper;
    private SQLiteDatabase  myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        init();
        sqLiteHelper=new MySQLiteHelper(RegActivity.this,"usersdb.db",null,1);
        myDb=sqLiteHelper.getWritableDatabase();
        refreshList();

    }

    private void refreshList() {

    }

    private void init() {
        regusername= (EditText) findViewById(R.id.regusername);
        regpwd=(EditText) findViewById(R.id.regpwd);
        regfh= (Button) findViewById(R.id.regfh);
        regbt= (Button) findViewById(R.id.regbt);
        radioGroup = (RadioGroup) findViewById(R.id.regrg);

        regbt.setOnClickListener(this);
        regfh.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.regbt:
                rb = (RadioButton)RegActivity.this.findViewById(radioGroup.getCheckedRadioButtonId());
                if(myDb==null){
                    Toast.makeText(RegActivity.this,"注册未成功",Toast.LENGTH_LONG).show();
                    return;}
                ContentValues cv=new ContentValues();
                cv.put("username",regusername.getText().toString());
                cv.put("password",regpwd.getText().toString());
                cv.put("usertype",rb.getText().toString());
                myDb.insert("users",null,cv);

               // myDb.execSQL("insert into users(username,password,usertype)values(?,?,?)",new String[]{ cv.getAsString("regusername"), cv.getAsString("regpwd"),"学生"});
                Toast.makeText(RegActivity.this,"注册成功",Toast.LENGTH_LONG).show();

                break;
            case R.id.regfh:
                Intent intent=new Intent(RegActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
