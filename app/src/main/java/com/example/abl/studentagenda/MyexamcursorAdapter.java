package com.example.abl.studentagenda;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

class MyexamcursorAdapter extends CursorAdapter
{
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public MyexamcursorAdapter(Context context, Cursor cursor)
    {
        super(context,cursor);
        this.mContext=context;
        this.mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return mLayoutInflater.inflate(R.layout.examlv_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {

        TextView modex=(TextView) view.findViewById(R.id.modexam);
        TextView profex=(TextView) view.findViewById(R.id.profexam);
        TextView yex=(TextView) view.findViewById(R.id.dayexam);
        TextView hexam=(TextView) view.findViewById(R.id.hexam);
        TextView lieuexam=(TextView) view.findViewById(R.id.lieuexam);


         modex.setText(cursor.getString(1));
         profex.setText(cursor.getString(2));
         yex.setText(cursor.getString(3));
         hexam.setText(cursor.getString(4));
         lieuexam.setText(cursor.getString(5));



    }
}
