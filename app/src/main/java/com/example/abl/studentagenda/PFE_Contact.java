package com.example.abl.studentagenda;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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

public class PFE_Contact extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Contact c,c1;
    protected DBHandler mydb;
    public ListView lvcontbin;
    public ListView lvcontenc;
    protected MycontcursorAdapter adapter;
    protected MycontcursorAdapter adapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pfe__contact);

        lvcontbin=(ListView) findViewById(R.id.lvcontbin);
        lvcontenc=(ListView) findViewById(R.id.lvcontenc);
        registerForContextMenu(lvcontbin);
        registerForContextMenu(lvcontenc);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addcont, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.addc: addcont();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void addcont() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogcont);
        dialog.setTitle("Création d'un type");
        dialog.show();

        spinsex(dialog);
        spintype(dialog);

        Button btnValider = (Button) dialog.findViewById(R.id.btnvalid);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                EditText nomcont=(EditText) dialog.findViewById(R.id.nomcont);
                EditText prenomcont=(EditText) dialog.findViewById(R.id.prenomcont);
                EditText tel=(EditText) dialog.findViewById(R.id.tel);
                Spinner type=(Spinner) dialog.findViewById(R.id.type);
                Spinner sex=(Spinner) dialog.findViewById(R.id.sex);

                c=new Contact();
                c.setSexe(sex.getSelectedItem().toString());
                c.setType(type.getSelectedItem().toString());

                c.setNom(nomcont.getText().toString());
                c.setPrenom(prenomcont.getText().toString());
                c.setTel(tel.getText().toString());


                mydb.addContact(c);
                adaptelv();
                dialog.dismiss();
            } });

        Button btnAnnuler = (Button) dialog.findViewById(R.id.btncanc);
        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                dialog.dismiss();
            } });
    }

    private void spintype(Dialog dialog) {

        Spinner type=(Spinner) dialog.findViewById(R.id.type);
        final String[]  nbr={getResources().getString(R.string.bin),getResources().getString(R.string.enc)};
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,nbr);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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



    private void adaptelv() {
        mydb = new DBHandler(this) ;
        Cursor cursor = mydb.showContactbin();
        adapter=new MycontcursorAdapter(this,cursor);
        lvcontbin.setAdapter(adapter);
        Cursor cursor2 = mydb.showContactenc();
        adapter=new MycontcursorAdapter(this,cursor2);
        lvcontenc.setAdapter(adapter);

    }


    private void spinsex(Dialog dialog) {

        Spinner sex=(Spinner) dialog.findViewById(R.id.sex);
        final String[]  nbr={getResources().getString(R.string.male),getResources().getString(R.string.fem)};
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,nbr);
        sex.setAdapter(adapter);
        sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
        menu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.drawable.baseline_call_black_18), getResources().getString(R.string.call)));
        menu.add(0, 2, 2, menuIconWithText(getResources().getDrawable(R.drawable.baseline_message_black_18), getResources().getString(R.string.msg)));

        menu.add(0, 3, 3, menuIconWithText(getResources().getDrawable(R.drawable.baseline_edit_black_18), getResources().getString(R.string.mod)));
        menu.add(0, 4, 4, menuIconWithText(getResources().getDrawable(R.drawable.baseline_delete_forever_black_18), getResources().getString(R.string.del)));
        inflater.inflate(R.menu.gstcont, menu);
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
        c=new Contact();
        try {
            c.setNom((String)( (TextView) info.targetView.findViewById(R.id.nomcont)).getText());
            c.setPrenom((String)( (TextView) info.targetView.findViewById(R.id.prenomcont)).getText());
            c.setTel((String)( (TextView) info.targetView.findViewById(R.id.telcont)).getText());

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        switch (item.getItemId())
        {
            case 1: callcont(c.getTel());
                return true;

            case 2: sndmsgcont(c.getTel());
                return true;

            case 3:  modifcont(c);
                return true;
            case 4:  delcont(c.getTel());
                return true;

            default: return super.onOptionsItemSelected(item); } }

    private void sndmsgcont(final String tel) {
        final String telephone = "sms:"+ tel;

        final Dialog msg = new Dialog(this);
        msg.setContentView(R.layout.dialomsg);
        msg.show();
        Button valid = (Button) msg.findViewById(R.id.btnvalid);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                msg.dismiss();
                EditText msgg=msg.findViewById(R.id.msg);
                String message=msgg.getText().toString();
                Uri uri=Uri.parse(telephone);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.putExtra("sms_body", message);
                startActivity(intent);

            } });
        Button canc = (Button) msg.findViewById(R.id.btncanc);
        canc.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                msg.dismiss();
            } });


    }

    private void callcont(String tel) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + tel));
        startActivity(intent);
    }

    private void delcont(final String tel) {

        final Dialog confirm = new Dialog(this);
        confirm.setTitle("Suppression");
        confirm.setContentView(R.layout.alertdelete);
        confirm.show();
        Button yes = (Button) confirm.findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                confirm.dismiss();
                mydb.delcontact(tel);
                adaptelv();
            } });
        Button no = (Button) confirm.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                confirm.dismiss();
            } });

    }

    private void modifcont(final Contact c) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogcont);
        dialog.setTitle("Création d'un type");
        dialog.show();
        EditText nomcont=(EditText) dialog.findViewById(R.id.nomcont);
        EditText prenomcont=(EditText) dialog.findViewById(R.id.prenomcont);
        EditText tel=(EditText) dialog.findViewById(R.id.tel);
        nomcont.setText(c.getNom());
        prenomcont.setText(c.getPrenom());
        tel.setText(c.getTel());
        spinsex(dialog);
        spintype(dialog);

        Button btnValider = (Button) dialog.findViewById(R.id.btnvalid);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                EditText nomcont=(EditText) dialog.findViewById(R.id.nomcont);
                EditText prenomcont=(EditText) dialog.findViewById(R.id.prenomcont);
                EditText tel=(EditText) dialog.findViewById(R.id.tel);
                Spinner type=(Spinner) dialog.findViewById(R.id.type);
                Spinner sex=(Spinner) dialog.findViewById(R.id.sex);

                c1=new Contact();
                c1.setSexe(sex.getSelectedItem().toString());
                c1.setType(type.getSelectedItem().toString());

                c1.setNom(nomcont.getText().toString());
                c1.setPrenom(prenomcont.getText().toString());
                c1.setTel(tel.getText().toString());


                mydb.editContact(c1,c.getTel());
                adaptelv();
                dialog.dismiss();
            } });

        Button btnAnnuler = (Button) dialog.findViewById(R.id.btncanc);
        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                dialog.dismiss();
            } });
    }

}
