package com.example.haha.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {
   private ListView listView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        textView= (TextView) findViewById(R.id.mainusername);
        Intent intent=getIntent();
        String aa=intent.getStringExtra("mainusername");
        String aaa=intent.getStringExtra("mainusertype");
        textView.setText(aa+aaa+"收藏的图书");

        //创建图书列表
        listView = (ListView) findViewById(R.id.mylistview);
        final List<Book>  bookList=new ArrayList<Book>();
        String[]  names=getResources().getStringArray(R.array.booknamearray);
        String[]  zuoze=getResources().getStringArray(R.array.zuozearray);
        Book book1=new Book(R.drawable.java,names[0],zuoze[0]);
        bookList.add(book1);
        Book book2=new Book(R.drawable.cplus,names[1],zuoze[1]);
        bookList.add(book2);
        Book book3=new Book(R.drawable.andr,names[2],zuoze[2]);
        bookList.add(book3);
        Book book4=new Book(R.drawable.ui,names[3],zuoze[3]);
        bookList.add(book4);
       BookAdapter adapter=new BookAdapter(BookActivity.this,R.layout.list_book,bookList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book book=bookList.get(i);
                Intent intent=new Intent(BookActivity.this,MybookActivity.class);
                intent.putExtra("id",i);
                startActivity(intent);
            }
        });
    }
}
