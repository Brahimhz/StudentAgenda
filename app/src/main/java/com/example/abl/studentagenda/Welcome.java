package com.example.abl.studentagenda;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Welcome extends AppCompatActivity {
    protected DBHandler mydb;
    private static int TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toast.makeText(this,getResources().getString(R.string.welc),Toast.LENGTH_LONG);

        mydb=new DBHandler(this);
        if(mydb.showUser()==null)
            {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                         Intent intent=new Intent(Welcome.this,register.class);
                        startActivity(intent);
                        finish();
                    }
                }, TIME_OUT);
            }

        else
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(Welcome.this,TimeTable.class);
                        startActivity(intent);
                        finish();
                    }
                }, TIME_OUT);
            }
    }
}
