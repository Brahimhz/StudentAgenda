package com.example.abl.studentagenda;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyrdvcursorAdapter extends CursorAdapter
{
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public MyrdvcursorAdapter(Context context, Cursor cursor)
    {
        super(context,cursor);
        this.mContext=context;
        this.mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return mLayoutInflater.inflate(R.layout.lvrdv_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {

        TextView subrdv= view.findViewById(R.id.subrdvitem);
        TextView daterdv= view.findViewById(R.id.daterdvitem);

        subrdv.setText(cursor.getString(1));
        daterdv.setText(cursor.getString(2)+"-"+cursor.getString(3)+"-"+cursor.getString(4));


    }
}


