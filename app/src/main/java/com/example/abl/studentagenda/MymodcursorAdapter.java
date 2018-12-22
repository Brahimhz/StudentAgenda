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

public class MymodcursorAdapter extends CursorAdapter
{
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public MymodcursorAdapter(Context context, Cursor cursor)
    {
        super(context,cursor);
        this.mContext=context;
        this.mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return mLayoutInflater.inflate(R.layout.lvmod_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {

        TextView nameedit= view.findViewById(R.id.nameitem);
        TextView coffedit= view.findViewById(R.id.coffitem);
        TextView notetd=view.findViewById(R.id.notetditem);
        TextView notetp=view.findViewById(R.id.notetpitem);
        TextView noteexam=view.findViewById(R.id.noteexamitem);
        TextView moyitem=view.findViewById(R.id.moyitem);

        Module m=new Module();
        m.setName(cursor.getString(1));
        m.setCoff(Float.parseFloat(cursor.getString(3)));
        m.setNotetd(Float.parseFloat(cursor.getString(6)));
        m.setNotetp(Float.parseFloat(cursor.getString(7)));
        m.setNoteexam(Float.parseFloat(cursor.getString(8)));
        float moy=(((m.getNotetd()+m.getNotetp())/2)+m.getNoteexam())/2;


        nameedit.setText(m.getName());
        coffedit.setText(String.valueOf(m.getCoff()));

        notetd.setText(String.valueOf(m.getNotetd())+"/20");
        notetp.setText(String.valueOf(m.getNotetp())+"/20");
        noteexam.setText(String.valueOf(m.getNoteexam())+"/20");
        moyitem.setText(String.valueOf(moy)+"/20");

        if(m.getNotetd()<5){notetd.setTextColor(Color.parseColor("#CA0605"));}
        if((m.getNotetd()<10)&&(m.getNotetd()>=5)){notetd.setTextColor(Color.parseColor("#F17511"));}
        if((m.getNotetd()<15)&&(m.getNotetd()>=10)){notetd.setTextColor(Color.parseColor("#00BB56"));}
        if((m.getNotetd()<20)&&(m.getNotetd()>=15)){notetd.setTextColor(Color.parseColor("#53AFCA"));}

        if(m.getNotetp()<5){notetp.setTextColor(Color.parseColor("#CA0605"));}
        if((m.getNotetp()<10)&&(m.getNotetp()>=5)){notetp.setTextColor(Color.parseColor("#F17511"));}
        if((m.getNotetp()<15)&&(m.getNotetp()>=10)){notetp.setTextColor(Color.parseColor("#00BB56"));}
        if((m.getNotetp()<20)&&(m.getNotetp()>=15)){notetp.setTextColor(Color.parseColor("#53AFCA"));}

        if(m.getNoteexam()<5){noteexam.setTextColor(Color.parseColor("#CA0605"));}
        if((m.getNoteexam()<10)&&(m.getNoteexam()>=5)){noteexam.setTextColor(Color.parseColor("#F17511"));}
        if((m.getNoteexam()<15)&&(m.getNoteexam()>=10)){noteexam.setTextColor(Color.parseColor("#00BB56"));}
        if((m.getNoteexam()<20)&&(m.getNoteexam()>=15)){noteexam.setTextColor(Color.parseColor("#53AFCA"));}

        if(moy<5){moyitem.setTextColor(Color.parseColor("#CA0605"));}
        if((moy<10)&&(moy>=5)){moyitem.setTextColor(Color.parseColor("#F17511"));}
        if((moy<15)&&(moy>=10)){moyitem.setTextColor(Color.parseColor("#00BB56"));}
        if((moy<20)&&(moy>=15)){moyitem.setTextColor(Color.parseColor("#53AFCA"));}

    }
}


