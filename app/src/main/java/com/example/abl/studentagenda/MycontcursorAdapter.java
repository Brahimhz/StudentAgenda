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

public class MycontcursorAdapter extends CursorAdapter
{
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public MycontcursorAdapter(Context context, Cursor cursor)
    {
        super(context,cursor);
        this.mContext=context;
        this.mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return mLayoutInflater.inflate(R.layout.contlv_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {

        TextView nomcont=(TextView) view.findViewById(R.id.nomcont);
        TextView prenomcont=(TextView) view.findViewById(R.id.prenomcont);
        TextView telcont=(TextView) view.findViewById(R.id.telcont);
        TextView tycont=(TextView) view.findViewById(R.id.tycont);
        ImageView imgcont=(ImageView) view.findViewById(R.id.imgcont);
        LinearLayout lnlcont=(LinearLayout) view.findViewById(R.id.lnlcont);


        nomcont.setText(cursor.getString(1));
        prenomcont.setText(cursor.getString(2));
        telcont.setText(cursor.getString(3));
        tycont.setText(cursor.getString(5));



        if((cursor.getString(5)).equals("Binome"))
        {
            if ((cursor.getString(4).equals("Male"))){
            imgcont.setImageResource(R.drawable.studentmal);
            lnlcont.setBackground(mContext.getDrawable(R.drawable.maleback));

            }else {
                imgcont.setImageResource(R.drawable.studentfemal);
                lnlcont.setBackground(mContext.getDrawable(R.drawable.femaleback));
            }

        }
        else {
            if ((cursor.getString(4)).equals("Male")){
                imgcont.setImageResource(R.drawable.manteacher);
                lnlcont.setBackground(mContext.getDrawable(R.drawable.maleback));
            }else {
                imgcont.setImageResource(R.drawable.womenteachher);
                lnlcont.setBackground(mContext.getDrawable(R.drawable.femaleback));
            }
        }
    }
}

