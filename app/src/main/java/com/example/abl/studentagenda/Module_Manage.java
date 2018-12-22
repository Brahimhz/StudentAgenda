package com.example.abl.studentagenda;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Module_Manage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected ListView lvmod;
    protected  Module module;
    protected MymodcursorAdapter adapter;
    protected DBHandler mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module__manage);
        lvmod=findViewById(R.id.lvmod);
        registerForContextMenu(lvmod);
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

        TextView nametv=head.findViewById(R.id.nametv);
        nametv.setText(cursor.getString(1)+" "+cursor.getString(2));
        TextView tv=head.findViewById(R.id.textView);
        tv.setText(cursor.getString(4));

        TextView timetab=findViewById(R.id.titlemodule);
        timetab.setText(timetab.getText()+" "+cursor.getString(4));
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

        } else if (id == R.id.pfecont) {
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


    public void adaptelv() {

        mydb = new DBHandler(this) ;
        Cursor cursor = mydb.showModule();
        adapter=new MymodcursorAdapter(this,cursor);
        lvmod.setAdapter(adapter);
        TextView semmoy=findViewById(R.id.semmoy);
        float coff=0;
        float moy=0;
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++)
            {
                coff+=cursor.getFloat(3);
                moy+=((((cursor.getFloat(6)+cursor.getFloat(7))/2)+cursor.getFloat(8)/2)*cursor.getFloat(3));
                cursor.moveToNext();
            }
            moy=moy/coff;
         semmoy.setText(String.valueOf(moy));
        if(moy<5){semmoy.setTextColor(Color.parseColor("#CA0605"));}
        if((moy<10)&&(moy>=5)){semmoy.setTextColor(Color.parseColor("#F17511"));}
        if((moy<15)&&(moy>=10)){semmoy.setTextColor(Color.parseColor("#00BB56"));}
        if((moy<20)&&(moy>=15)){semmoy.setTextColor(Color.parseColor("#53AFCA"));}

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addmod, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.addm: addmod();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void addmod() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogmod);
        dialog.setTitle("Création d'un Module");
        dialog.show();
        Button btnValider = (Button) dialog.findViewById(R.id.btnvalid);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText nameedit=(EditText) dialog.findViewById(R.id.dialogmodname);
                EditText abvedit=(EditText) dialog.findViewById(R.id.dialogmodabv);
                EditText coffedit=(EditText) dialog.findViewById(R.id.dialogmodcoff);
                EditText modevtdedit=(EditText) dialog.findViewById(R.id.dialogmodmodevtd);
                EditText modevexedit=(EditText) dialog.findViewById(R.id.dialogmodmodevexam);

                module=new Module();
                module.setName(nameedit.getText().toString());
                module.setAbvname(abvedit.getText().toString());
                module.setCoff(Float.parseFloat(coffedit.getText().toString()));
                module.setModevtest(Integer.parseInt(modevtdedit.getText().toString()));
                module.setModevexam(Integer.parseInt(modevexedit.getText().toString()));
                module.setNotetd((float) 0.0);
                module.setNotetp((float) 0.0);
                module.setNoteexam((float) 0.0);
                dialog.dismiss();
                mydb.addModule(module);
                adaptelv();
            } });


        Button btncan= (Button) dialog.findViewById(R.id.btncanc);
        btncan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            } });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        menu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.drawable.baseline_edit_black_18), getResources().getString(R.string.notemod)));
        menu.add(0, 2, 2, menuIconWithText(getResources().getDrawable(R.drawable.baseline_edit_black_18), getResources().getString(R.string.modify)));
        menu.add(0, 3, 3, menuIconWithText(getResources().getDrawable(R.drawable.baseline_delete_forever_black_18), getResources().getString(R.string.delete)));
        inflater.inflate(R.menu.gstmod, menu);
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
        module=new Module();
        try {
            module.setName((String)( (TextView) info.targetView.findViewById(R.id.nameitem)).getText());
            module.setCoff(Float.parseFloat(( (TextView) info.targetView.findViewById(R.id.coffitem)).getText().toString()));
            module.setNotetd(Float.parseFloat((String)( (TextView) info.targetView.findViewById(R.id.notetditem)).getText()));
            module.setNotetp(Float.parseFloat((String) ((TextView) info.targetView.findViewById(R.id.notetpitem)).getText()));
            module.setNoteexam(Float.parseFloat((String) ((TextView) info.targetView.findViewById(R.id.noteexamitem)).getText()));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        switch (item.getItemId())
        {
            case 1: notemodule(module.getNotetd(),module.getNotetp(),module.getNoteexam(),module.getName());
                return true;
            case 2:  modifexam(module);
                return true;
            case 3:  delexam(module.getName());
                return true;

            default: return super.onOptionsItemSelected(item); } }

    private void notemodule(float notetd, float notetp, float noteexam, final String name) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_note);
        dialog.setTitle("Modification d'un Module");
        dialog.show();


        final EditText notetdedit=(EditText) dialog.findViewById(R.id.dialognotetd);
        final EditText notetpedit=(EditText) dialog.findViewById(R.id.dialognotetp);
        final EditText noteexamedit=(EditText) dialog.findViewById(R.id.dialognoteexam);



        notetdedit.setText(String.valueOf(notetd));
        notetpedit.setText(String.valueOf(notetp));
        noteexamedit.setText(String.valueOf(noteexam));


        Button btnValider = (Button) dialog.findViewById(R.id.btnvalid);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Module module=new Module();
                module.setNotetd(Float.parseFloat(notetdedit.getText().toString()));
                module.setNotetp(Float.parseFloat(notetpedit.getText().toString()));
                module.setNoteexam(Float.parseFloat(noteexamedit.getText().toString()));
                mydb.addnote(module,name);
                dialog.dismiss();
                adaptelv();

            } });


        Button btncan= (Button) dialog.findViewById(R.id.btncanc);
        btncan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            } });

    }

    private void delexam(final String name) {

        final Dialog confirm = new Dialog(this);
        confirm.setTitle("Suppression");
        confirm.setContentView(R.layout.alertdelete);
        confirm.show();
        Button yes = (Button) confirm.findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                confirm.dismiss();
                mydb.delModule(name);
                adaptelv();
            } });
        Button no = (Button) confirm.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                confirm.dismiss();
            } });



    }

    private void modifexam(final Module module1) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogmod);
        dialog.setTitle("Création d'un Module");

        EditText nameedit=(EditText) dialog.findViewById(R.id.dialogmodname);
        EditText coffedit=(EditText) dialog.findViewById(R.id.dialogmodcoff);

        nameedit.setText(module1.getName());
        coffedit.setText(String.valueOf(module1.getCoff()));


        dialog.show();
        Button btnValider = (Button) dialog.findViewById(R.id.btnvalid);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText nameedit=(EditText) dialog.findViewById(R.id.dialogmodname);
                EditText abvedit=(EditText) dialog.findViewById(R.id.dialogmodabv);
                EditText coffedit=(EditText) dialog.findViewById(R.id.dialogmodcoff);
                EditText modevtdedit=(EditText) dialog.findViewById(R.id.dialogmodmodevtd);
                EditText modevexedit=(EditText) dialog.findViewById(R.id.dialogmodmodevexam);

                module=new Module();
                module.setName(nameedit.getText().toString());
                module.setAbvname(abvedit.getText().toString());
                module.setCoff(Float.parseFloat(coffedit.getText().toString()));
                module.setModevtest(Integer.parseInt(modevtdedit.getText().toString()));
                module.setModevexam(Integer.parseInt(modevexedit.getText().toString()));
                module.setNotetd(module1.getNotetd());
                module.setNotetp(module1.getNotetp());
                module.setNoteexam(module1.getNoteexam());
                dialog.dismiss();
                mydb.editModule(module,module1.getName());
                adaptelv();
            } });


        Button btncan= (Button) dialog.findViewById(R.id.btncanc);
        btncan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            } });
    }
}
