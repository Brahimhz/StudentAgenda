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

public class Exam_Manage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected DBHandler mydb;
    protected MyexamcursorAdapter adapter;
    protected ListView examlv;
    protected Examen ex;
    protected String [] modabv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam__manage);

        examlv = (ListView) findViewById(R.id.lvexam);
        registerForContextMenu(examlv);
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
        TextView titleexam=findViewById(R.id.titleexam);
        titleexam.setText(titleexam.getText()+" "+cursor.getString(4));
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addex, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.addexamen: addexamen();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void addexamen() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogexam);
        dialog.setTitle("Création d'un type");
        dialog.show();
        spinday(dialog);
        spinmounth(dialog);
        spinmod(dialog);
        Button btnValider = (Button) dialog.findViewById(R.id.btnvalid);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                Spinner modex=(Spinner) dialog.findViewById(R.id.modexam);
                EditText profex=(EditText) dialog.findViewById(R.id.profexam);
                Spinner dex=(Spinner) dialog.findViewById(R.id.dex);
                Spinner mex=(Spinner) dialog.findViewById(R.id.mex);
                EditText yex=(EditText) dialog.findViewById(R.id.yex);
                EditText hexam=(EditText) dialog.findViewById(R.id.hexam);
                EditText lieuexam=(EditText) dialog.findViewById(R.id.lieuexam);
                EditText superexam=(EditText) dialog.findViewById(R.id.superexam);

                ex= new Examen();
                ex.setNomex(modex.getSelectedItem().toString());
                ex.setProfex(profex.getText().toString());
                ex.setDateex(dex.getSelectedItem().toString() +"-"+ mex.getSelectedItem().toString() +"-"+ yex.getText().toString());
                ex.setHex(hexam.getText().toString());
                ex.setLieuex(lieuexam.getText().toString());
                ex.setSuperex(superexam.getText().toString());
                dialog.dismiss();
                mydb.addExam(ex);
                adaptelv();

            } });

        Button btnAnnuler = (Button) dialog.findViewById(R.id.btncanc);
        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                dialog.dismiss();
            } });
    }

    private void adaptelv() {
        mydb = new DBHandler(this) ;
        Cursor cursor = mydb.showPerson();
        adapter=new MyexamcursorAdapter(this,cursor);
        examlv.setAdapter(adapter);
    }

    private void spinmounth(Dialog dialog) {

        Spinner mex=(Spinner) dialog.findViewById(R.id.mex);

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

    private void spinday(Dialog dialog) {

        Spinner dex=(Spinner) dialog.findViewById(R.id.dex);
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

    private void spinmod(Dialog dialog) {

        Spinner dex=(Spinner) dialog.findViewById(R.id.modexam);
        mydb=new DBHandler(this);
        Cursor cursor=mydb.showModule();
        cursor.moveToFirst();
        modabv=new String[cursor.getCount()];
        System.out.println(cursor.getCount());
        for (int i=0;i<(cursor.getCount());i++)
        {
            modabv[i]=cursor.getString(2);
            cursor.moveToNext();
        }
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,modabv);
        dex.setAdapter(adapter);
        dex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3)
            {
                int index = arg0.getSelectedItemPosition();
                String [] spind = modabv;
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
        menu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.drawable.baseline_edit_black_18), getResources().getString(R.string.modify)));
        menu.add(0, 2, 2, menuIconWithText(getResources().getDrawable(R.drawable.baseline_delete_forever_black_18), getResources().getString(R.string.delete)));
        inflater.inflate(R.menu.gstex, menu);
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
        ex=new Examen();
        try {
            ex.setNomex((String)( (TextView) info.targetView.findViewById(R.id.modexam)).getText());
            ex.setProfex((String)( (TextView) info.targetView.findViewById(R.id.profexam)).getText());
            ex.setDateex((String)( (TextView) info.targetView.findViewById(R.id.dayexam)).getText());
            ex.setHex((String)( (TextView) info.targetView.findViewById(R.id.hexam)).getText());
            ex.setLieuex((String) ((TextView) info.targetView.findViewById(R.id.lieuexam)).getText());
            ex.setSuperex((String) ((TextView) info.targetView.findViewById(R.id.superexam)).getText());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        switch (item.getItemId())
        {
            case 1:  modifexam(ex);
                return true;
            case 2:  delexam(ex.getNomex());
                return true;

            default: return super.onOptionsItemSelected(item); } }

    private void delexam(final String nomex) {

        final Dialog confirm = new Dialog(this);
        confirm.setTitle("Suppression");
        confirm.setContentView(R.layout.alertdelete);
        confirm.show();
        Button yes = (Button) confirm.findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                confirm.dismiss();
                mydb.delExam(nomex);
                adaptelv();
            } });
        Button no = (Button) confirm.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                confirm.dismiss();
            } });


    }

    private void modifexam(final Examen ex1) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogexam);
        dialog.setTitle("Création d'un type");
        dialog.show();


        EditText profex=(EditText) dialog.findViewById(R.id.profexam);
        EditText hexam=(EditText) dialog.findViewById(R.id.hexam);
        EditText lieuexam=(EditText) dialog.findViewById(R.id.lieuexam);


        profex.setText(ex1.getProfex());
        hexam.setText(ex1.getHex());
        lieuexam.setText(ex1.getLieuex());

        spinday(dialog);
        spinmounth(dialog);
        Button btnValider = (Button) dialog.findViewById(R.id.btnvalid);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                Spinner modex=(Spinner) dialog.findViewById(R.id.modexam);
                EditText profex=(EditText) dialog.findViewById(R.id.profexam);
                Spinner  dex=(Spinner) dialog.findViewById(R.id.dex);
                Spinner mex=(Spinner) dialog.findViewById(R.id.mex);
                EditText yex=(EditText) dialog.findViewById(R.id.yex);
                EditText hexam=(EditText) dialog.findViewById(R.id.hexam);
                EditText lieuexam=(EditText) dialog.findViewById(R.id.lieuexam);
                EditText superexam=(EditText) dialog.findViewById(R.id.superexam);

                ex= new Examen();
                ex.setNomex(modex.getSelectedItem().toString());
                ex.setProfex(profex.getText().toString());
                ex.setDateex(dex.getSelectedItem().toString() +"-"+ mex.getSelectedItem().toString() +"-"+ yex.getText().toString());
                ex.setHex(hexam.getText().toString());
                ex.setLieuex(lieuexam.getText().toString());
                ex.setSuperex(superexam.getText().toString());
                dialog.dismiss();
                mydb.editExam(ex,ex1.getNomex());
                adaptelv();

            } });

        Button btnAnnuler = (Button) dialog.findViewById(R.id.btncanc);
        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                dialog.dismiss();
            } });

    }

}
