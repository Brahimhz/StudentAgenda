package com.example.abl.studentagenda;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Profil extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected DBHandler mydb;
    protected User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinsexe();
        spinniv();
        spinlang();
        Button btnstart=(Button) findViewById(R.id.start);
        mydb=new DBHandler(this);

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText fname=findViewById(R.id.fname);
                EditText sname=findViewById(R.id.sname);
                Spinner sexe=findViewById(R.id.sexe);
                Spinner  langage=findViewById(R.id.lang);
                Spinner  level=findViewById(R.id.niv);
                user=new User();
                user.setFirstname(fname.getText().toString());
                user.setSurname(sname.getText().toString());
                user.setSexe(sexe.getSelectedItem().toString());
                user.setLevel(level.getSelectedItem().toString());
                user.setLangage(langage.getSelectedItem().toString());
                Cursor cursor=mydb.showUser();
                cursor.moveToFirst();
                mydb.editUser(user,cursor.getString(1));
                Toast.makeText(getBaseContext(),"Done",Toast.LENGTH_LONG).show();

            } });

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
    private void spinsexe() {
        Spinner niv = (Spinner) this.findViewById(R.id.sexe);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sexe, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        niv.setAdapter(adapter);

        niv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                int index = arg0.getSelectedItemPosition();

                // storing string resources into Array
                String[] spintypesea = getResources().getStringArray(R.array.niv);

                String lvldb = (spintypesea[index]).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void spinlang() {
        Spinner niv = (Spinner) this.findViewById(R.id.lang);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lang, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        niv.setAdapter(adapter);

        niv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                int index = arg0.getSelectedItemPosition();

                // storing string resources into Array
                String[] spintypesea = getResources().getStringArray(R.array.niv);

                String lvldb = (spintypesea[index]).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void spinniv() {

        Spinner niv = (Spinner) this.findViewById(R.id.niv);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.niv, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        niv.setAdapter(adapter);

        niv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                int index = arg0.getSelectedItemPosition();

                // storing string resources into Array
                String[] spintypesea = getResources().getStringArray(R.array.niv);

                String lvldb = (spintypesea[index]).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
