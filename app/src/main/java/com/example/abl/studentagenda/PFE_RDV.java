package com.example.abl.studentagenda;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class PFE_RDV extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected ListView lvrdv;
    protected DBHandler mydb;
    protected MyrdvcursorAdapter adapter;
    protected RDV rdv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pfe__rdv);

        lvrdv=findViewById(R.id.lvdrv);
        registerForContextMenu(lvrdv);
        adaptelv();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View head=navigationView.inflateHeaderView(R.layout.nav_header_time_table);
        mydb=new DBHandler(this);
        Cursor cursor=mydb.showUser();
        cursor.moveToFirst();

        ImageView img=(ImageView) head.findViewById(R.id.imageView);
        LinearLayout lnl=head.findViewById(R.id.lnl);
        if(cursor.getString(3).equals(getResources().getString(R.string.male)))
        {

            img.setImageResource(R.drawable.studentmal);
            lnl.setBackground(getResources().getDrawable(R.drawable.maleback));
        }
        else
        {

            img.setImageResource(R.drawable.studentfemal);
            lnl.setBackground(getResources().getDrawable(R.drawable.femaleback));
        }



        cursor.moveToFirst();
        TextView nametv=head.findViewById(R.id.nametv);
        nametv.setText(cursor.getString(1)+" "+cursor.getString(2));
        TextView tv=head.findViewById(R.id.textView);
        tv.setText(cursor.getString(4));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.gsttimetable) {
            Intent intent=new Intent(this,TimeTable.class);
            startActivity(intent);
        } else if (id == R.id.gstmod) {
            Intent intent=new Intent(this,Module_Manage.class);
            startActivity(intent);
        } else if (id == R.id.gstexam) {
            Intent intent=new Intent(this,Exam_Manage.class);
            startActivity(intent);
        }  else if (id == R.id.pfecont) {
            Intent intent=new Intent(this,PFE_Contact.class);
            startActivity(intent);

        } else if (id == R.id.pferdv) {
            Intent intent=new Intent(this,PFE_RDV.class);
            startActivity(intent);

        }else if (id == R.id.pfetask) {
            Intent intent=new Intent(this,PFE_Task.class);
            startActivity(intent);

        }
        else if (id == R.id.setprof) {
            Intent intent=new Intent(this,Profil.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void adaptelv() {
        mydb = new DBHandler(this) ;
        Cursor cursor = mydb.showRdv();
        adapter=new MyrdvcursorAdapter(this,cursor);
        lvrdv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addrdv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.addrdv: addrdv();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void addrdv() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogrdv);
        dialog.setTitle("Création d'un type");
        dialog.show();
        spindaybeg(dialog);
        spinmounthbeg(dialog);
        spinyearbeg(dialog);
        Button btnValider = (Button) dialog.findViewById(R.id.btnvalid);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {

                EditText subrdv=dialog.findViewById(R.id.subrdv);
                Spinner rdvday=dialog.findViewById(R.id.rdvday);
                Spinner rdvmou=dialog.findViewById(R.id.rdvmou);
                Spinner rdvyear=dialog.findViewById(R.id.rdvyear);
                rdv= new RDV();
                rdv.setSubrdv(subrdv.getText().toString());
                rdv.setDayrdv(rdvday.getSelectedItem().toString());
                rdv.setMounthrdv(rdvmou.getSelectedItem().toString());
                rdv.setYearrdv(rdvyear.getSelectedItem().toString());
                dialog.dismiss();
                mydb.addRdv(rdv);
                adaptelv();

            } });

        Button btnAnnuler = (Button) dialog.findViewById(R.id.btncanc);
        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                dialog.dismiss();
            } });
    }

    private void spindaybeg(Dialog dialog) {

        Spinner dex=(Spinner) dialog.findViewById(R.id.rdvday);
        final String[]  nbr={"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,nbr);
        dex.setAdapter(adapter);
        dex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3)
            {
                int index = arg0.getSelectedItemPosition();
                String [] spind = nbr;
                String lvldb=(spind[index]).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void spinmounthbeg(Dialog dialog) {
        Spinner dex=(Spinner) dialog.findViewById(R.id.rdvmou);
        final String[]  nbr={"01","02","03","04","05","06","07","08","09","10","11","12"};
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,nbr);
        dex.setAdapter(adapter);
        dex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3)
            {
                int index = arg0.getSelectedItemPosition();
                String [] spind = nbr;
                String lvldb=(spind[index]).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void spinyearbeg(Dialog dialog) {

        Spinner dex=(Spinner) dialog.findViewById(R.id.rdvyear);
        final String[]  nbr={"2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028"};
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,nbr);
        dex.setAdapter(adapter);
        dex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3)
            {
                int index = arg0.getSelectedItemPosition();
                String [] spind = nbr;
                String lvldb=(spind[index]).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
