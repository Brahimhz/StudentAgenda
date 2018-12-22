package com.example.abl.studentagenda;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MytaskcursorAdapter extends CursorAdapter
{
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public MytaskcursorAdapter(Context context, Cursor cursor)
    {
        super(context,cursor);
        this.mContext=context;
        this.mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return mLayoutInflater.inflate(R.layout.lvtask_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {

        TextView subtask=(TextView) view.findViewById(R.id.subtask);
        TextView daybeg=(TextView) view.findViewById(R.id.daybeg);
        TextView dayend=(TextView) view.findViewById(R.id.dayend);
        TextView sit=(TextView) view.findViewById(R.id.sit);

        subtask.setText(cursor.getString(1));
        daybeg.setText(cursor.getString(3));
        dayend.setText(cursor.getString(4));

        sit.setText(mContext.getResources().getString(R.string.nolate));
        sit.setTextColor(Color.parseColor("#27FF0D"));
    }
}
