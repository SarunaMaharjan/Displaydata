package com.example.retrofitapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ListViewAdapter extends BaseAdapter {

    private final Context context;

    private final ArrayList<String> question;
    private final ArrayList<String> courses;

    LayoutInflater layoutInflater;

    public ListViewAdapter(Context ctx, ArrayList<String> question, ArrayList<String> courses) {
        this.context = ctx;

        this.question = question;
        this.courses = courses;

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        Holder holder = new Holder();
        layoutInflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        view = layoutInflater.inflate(R.layout.list_item, null);
        holder.txt_question=(TextView)view.findViewById(R.id.question);
        holder.txt_courses=(TextView)view.findViewById(R.id.courses);


        holder.txt_question.setText(question.get(position));
        holder.txt_courses.setText(courses.get(position));


        return view;
    }

    static class Holder {
        TextView txt_question,txt_courses;
    }
}
