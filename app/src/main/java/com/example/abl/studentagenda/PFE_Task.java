package com.example.abl.studentagenda;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.ContextMenu;
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

public class PFE_Task extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected ListView lvtask;
    protected DBHandler mydb;
    protected MytaskcursorAdapter adapter;
    protected Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pfe__task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvtask=findViewById(R.id.lvtask);
        registerForContextMenu(lvtask);
        adaptelv();

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
        Cursor cursor = mydb.showTask();
        adapter=new MytaskcursorAdapter(this,cursor);
        lvtask.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addtask, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.adddt: addtask();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void addtask() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogtask);
        dialog.setTitle("Création d'un type");
        dialog.show();
        spindaybeg(dialog);
        spinmounthbeg(dialog);
        spinyearbeg(dialog);
        spindayend(dialog);
        spinmounthend(dialog);
        spinyearend(dialog);
        Button btnValider = (Button) dialog.findViewById(R.id.btnvalid);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                EditText subtask=(EditText) dialog.findViewById(R.id.subtask);
                EditText dettask=(EditText) dialog.findViewById(R.id.dettask);
                Spinner daybeg=(Spinner) dialog.findViewById(R.id.daybeg);
                Spinner mounthbeg=(Spinner) dialog.findViewById(R.id.mounthbeg);
                Spinner yearbeg=(Spinner) dialog.findViewById(R.id.yearbeg);
                Spinner dayend=(Spinner) dialog.findViewById(R.id.dayend);
                Spinner mounthend=(Spinner) dialog.findViewById(R.id.mounthend);
                Spinner yearend=(Spinner) dialog.findViewById(R.id.yearend);

                task= new Task();
                task.setSubtask(subtask.getText().toString());
                task.setDetails(dettask.getText().toString());
                task.setDatebeg(daybeg.getSelectedItem().toString() +"-"+ mounthbeg.getSelectedItem().toString() +"-"+ yearbeg.getSelectedItem().toString());
                task.setDatefin(dayend.getSelectedItem().toString() +"-"+ mounthend.getSelectedItem().toString() +"-"+ yearend.getSelectedItem().toString());
                dialog.dismiss();
                mydb.addTask(task);
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

        Spinner dex=(Spinner) dialog.findViewById(R.id.daybeg);
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

        Spinner mex=(Spinner) dialog.findViewById(R.id.mounthbeg);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mex.setAdapter(adapter);

        mex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                int index = arg0.getSelectedItemPosition();

                // storing string resources into Array
                String [] spintypesea = getResources().getStringArray(R.array.months_array);

                String lvldb=(spintypesea[index]).toString();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
    }

    private void spinyearbeg(Dialog dialog) {

        Spinner dex=(Spinner) dialog.findViewById(R.id.yearbeg);
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
    private void spindayend(Dialog dialog) {

        Spinner dex=(Spinner) dialog.findViewById(R.id.dayend);
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

    private void spinmounthend(Dialog dialog) {

        Spinner mex=(Spinner) dialog.findViewById(R.id.mounthend);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mex.setAdapter(adapter);

        mex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                int index = arg0.getSelectedItemPosition();

                // storing string resources into Array
                String [] spintypesea = getResources().getStringArray(R.array.months_array);

                String lvldb=(spintypesea[index]).toString();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
    }

    private void spinyearend(Dialog dialog) {

        Spinner dex=(Spinner) dialog.findViewById(R.id.yearend);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        menu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.drawable.baseline_info_black_18), getResources().getString(R.string.det)));
        menu.add(0, 2, 2, menuIconWithText(getResources().getDrawable(R.drawable.baseline_edit_black_18), getResources().getString(R.string.modify)));
        menu.add(0, 3, 3, menuIconWithText(getResources().getDrawable(R.drawable.baseline_delete_forever_black_18), getResources().getString(R.string.delete)));
        inflater.inflate(R.menu.gsttask, menu);
    }
    private CharSequence menuIconWithText(Drawable r, String title) {

        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String subtask = null;
        try {
            subtask=(String)( (TextView) info.targetView.findViewById(R.id.subtask)).getText();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        switch (item.getItemId())
        {
            case 1:  detailsTask(subtask);
                return true;
            case 2:  editTask(subtask);
                return true;
            case 3:  deleteTask(subtask);
                return true;

            default: return super.onOptionsItemSelected(item); } }

    private void detailsTask(String subtas) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogdettask);
        dialog.setTitle("Création d'un type");
        dialog.show();

        mydb = new DBHandler(this);
        task=new Task();
        task=mydb.showdetTask(subtas);

        TextView subtask=(TextView) dialog.findViewById(R.id.subtask);
        TextView dettask=(TextView) dialog.findViewById(R.id.dettask);
        TextView datebeg=(TextView) dialog.findViewById(R.id.datebeg);
        TextView dateend=(TextView) dialog.findViewById(R.id.dateend);

        subtask.setText(task.getSubtask());
        dettask.setText(task.getDetails());
        datebeg.setText(task.getDatebeg());
        dateend.setText(task.getDatefin());

        Button btnok = (Button) dialog.findViewById(R.id.btnok);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {dialog.dismiss();}});
    }

    private void editTask(final String subtas) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogtask);
        dialog.setTitle("Création d'un type");
        dialog.show();
        spindaybeg(dialog);
        spinmounthbeg(dialog);
        spinyearbeg(dialog);
        spindayend(dialog);
        spinmounthend(dialog);
        spinyearend(dialog);
        EditText subtask=(EditText) dialog.findViewById(R.id.subtask);
        subtask.setText(subtas);
        Button btnValider = (Button) dialog.findViewById(R.id.btnvalid);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                EditText subtask=(EditText) dialog.findViewById(R.id.subtask);
                EditText dettask=(EditText) dialog.findViewById(R.id.dettask);
                Spinner daybeg=(Spinner) dialog.findViewById(R.id.daybeg);
                Spinner mounthbeg=(Spinner) dialog.findViewById(R.id.mounthbeg);
                Spinner yearbeg=(Spinner) dialog.findViewById(R.id.yearbeg);
                Spinner dayend=(Spinner) dialog.findViewById(R.id.dayend);
                Spinner mounthend=(Spinner) dialog.findViewById(R.id.mounthend);
                Spinner yearend=(Spinner) dialog.findViewById(R.id.yearend);

                task= new Task();
                task.setSubtask(subtask.getText().toString());
                task.setDetails(dettask.getText().toString());
                task.setDatebeg(daybeg.getSelectedItem().toString() +"-"+ mounthbeg.getSelectedItem().toString() +"-"+ yearbeg.getSelectedItem().toString());
                task.setDatefin(dayend.getSelectedItem().toString() +"-"+ mounthend.getSelectedItem().toString() +"-"+ yearend.getSelectedItem().toString());
                dialog.dismiss();
                mydb.editTask(task,subtas);
                adaptelv();

            } });

        Button btnAnnuler = (Button) dialog.findViewById(R.id.btncanc);
        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                dialog.dismiss();
            } });
    }


    private void deleteTask(final String subtask) {

        final Dialog confirm = new Dialog(this);
        confirm.setTitle("Suppression");
        confirm.setContentView(R.layout.alertdelete);
        confirm.show();
        Button yes = (Button) confirm.findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                confirm.dismiss();
                mydb.deleteTask(subtask);
                adaptelv();
            } });
        Button no = (Button) confirm.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                confirm.dismiss();
            } });


    }
}
