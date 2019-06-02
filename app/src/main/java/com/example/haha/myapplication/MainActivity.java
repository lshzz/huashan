package com.example.haha.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mainpic;
    private EditText username;
    private  EditText pwd;
    private Button login_bt;
    private Button reg_bt;
    private TextView tel;
    private RadioGroup radioGroup;
    private CheckBox jizhu;
    private MySQLiteHelper sqLiteHelper;
    private SQLiteDatabase myDb;
    private RadioButton rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        sqlinit();

    }



    public void init() {//初始化
        mainpic= (ImageView) findViewById(R.id.mainpic);
        username= (EditText) findViewById(R.id.username);
        pwd=(EditText) findViewById(R.id.pwd);
        login_bt= (Button) findViewById(R.id.login_bt);
        reg_bt= (Button) findViewById(R.id.reg_bt);
        tel= (TextView) findViewById(R.id.tel_text);
        radioGroup = (RadioGroup) findViewById(R.id.rg);
        jizhu= (CheckBox) findViewById(R.id.jizhu);
        login_bt.setOnClickListener(this);
        reg_bt.setOnClickListener(this);
        tel.setOnClickListener(this);
        mainpic.setOnClickListener(this);
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        username.setText(sp.getString("username", ""));
        pwd.setText(sp.getString("pwd", ""));
        if(!sp.getString("pwd", "").equals(""))jizhu.setChecked(true);
    }

    public  void jizhumiam()//记住密码
    {
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (jizhu.isChecked()) {
        editor.putString("username", username.getText().toString());
        editor.putString("pwd", pwd.getText().toString());
        editor.commit();
        username.setText(sp.getString("username", ""));
        pwd.setText(sp.getString("pwd", ""));
       }
        else {
        username.setText( "");
        pwd.setText("");
            editor.remove("username");
            editor.remove("pwd");
            editor.commit();
       }
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
  //数据库
    private void sqlinit() {
        sqLiteHelper=new MySQLiteHelper(MainActivity.this,"usersdb.db",null,1);
        myDb=sqLiteHelper.getWritableDatabase();//完成创建数据库

    }
    //根据users表查询
    private  boolean login(){
        //获取单选按钮组所选中的单选按钮
         rb = (RadioButton)MainActivity.this.findViewById(radioGroup.getCheckedRadioButtonId());
        Cursor cursor=myDb.query("users",new String[]{"username"},
                "username=? and password=? and usertype=?",new String[]{username.getText().toString(),pwd.getText().toString(),rb.getText().toString()},null,null,null );
        if(cursor.moveToFirst())
        return true;
        else  return false;
            }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_bt:
                Toast toast1 = Toast.makeText(MainActivity.this, "用户名或密码输入错误!", Toast.LENGTH_SHORT);
                Toast toast2 = Toast.makeText(MainActivity.this, "请输入用户名!", Toast.LENGTH_SHORT);
                Toast toast3 = Toast.makeText(MainActivity.this, "请输入密码!", Toast.LENGTH_SHORT);
                Toast toast4 = Toast.makeText(MainActivity.this, "请输入用户名和密码!", Toast.LENGTH_SHORT);
                if(login()){
                    Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(MainActivity.this,BookActivity.class);
                    intent.putExtra("mainusername",username.getText().toString());
                    intent.putExtra("mainusertype",rb.getText().toString());
                    startActivity(intent);
                    jizhumiam();//记住密码
                }
                else if (username.getText().toString().equals("") && pwd.getText().toString().equals("")){
                    toast4.show();
                }
                else if (username.getText().toString().equals("")){
                    toast2.show();
                }
                else if (pwd.getText().toString().equals("")){
                    toast3.show();
                }
                else{
                    toast1.show();
                }

               break;
            case R.id.reg_bt:
                Intent intent=new Intent(MainActivity.this,RegActivity.class);
                startActivity(intent);
                break;
            case R.id.tel_text:
                callPhone(tel.getText().toString());
                break;
            case R.id.mainpic:
                startActivity(new Intent(MainActivity.this,UserDataActivity.class));
                break;

        }

    }
}
