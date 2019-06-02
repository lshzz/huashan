package com.example.haha.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by haha on 2019/5/26.
 */

public class BookAdapter extends ArrayAdapter {
    public int resId;
    public BookAdapter(Context context, int resource, List<Book> objects) {
        super(context, resource,objects);
        resId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book= (Book) getItem(position);//获得当前列表项Book对象
        View view= LayoutInflater.from(getContext()).inflate(resId,parent,false);
        TextView bookname=view.findViewById(R.id.bookname);
        bookname.setText(book.getName());
        TextView zuoze=view.findViewById(R.id.zuoze);
        zuoze.setText(book.getZuoze());
        ImageView bookpic=view.findViewById(R.id.bookpic);
        bookpic.setImageResource(book.getPicId());
        return view;
    }
}
