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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class TimeTable extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected TextViewWithContextMenuInfo [] seance=new TextViewWithContextMenuInfo[30];
    protected TextView[] h= new TextView[5];
    protected DBHandler mydb;
    protected Seance sea;
    protected Hour hour;
    protected String[]  modabv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findview();
        menuconlinkDS();
        clicklis();
        seashow();
        hourshow();

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

        TextView timetab=findViewById(R.id.titletimetab);
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

    public void clicklis() {
        h[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialomdftm(h[0]);
            }
        });

        h[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialomdftm(h[1]);
            }
        });

        h[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialomdftm(h[2]);
            }
        });

        h[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialomdftm(h[3]);
            }
        });

        h[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialomdftm(h[4]);
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        menu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.drawable.baseline_add_black_18), getResources().getString(R.string.add)));
        menu.add(0, 2, 2, menuIconWithText(getResources().getDrawable(R.drawable.baseline_info_black_18), getResources().getString(R.string.det)));
        menu.add(0, 3, 3, menuIconWithText(getResources().getDrawable(R.drawable.baseline_edit_black_18), getResources().getString(R.string.mod)));
        menu.add(0, 4, 4, menuIconWithText(getResources().getDrawable(R.drawable.baseline_delete_forever_black_18), getResources().getString(R.string.del)));
        inflater.inflate(R.menu.menuconds, menu);
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
        TextViewWithContextMenuInfo.TextViewContextMenuInfo menuInfo = (TextViewWithContextMenuInfo.TextViewContextMenuInfo) item.getMenuInfo();
        TextViewWithContextMenuInfo txt = (TextViewWithContextMenuInfo) menuInfo.targetView;

        switch (item.getItemId())
        {
            case 1: dialoadd(txt);
                return true;

            case 2: dialodetmds(txt);
                return true;

            case 3: dialoedit(txt);
                return true;

            case 4: dialidel(txt);
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    private void dialoedit(final TextViewWithContextMenuInfo txt) {
        final Dialog dialog = new Dialog(this); // créer le dialogue
        dialog.setContentView(R.layout.dialog_add);
        dialog.setTitle(getResources().getString(R.string.dialaddtitle));
        dialog.show();

        sea=new Seance();
        sea=seafound(txt.getId());

        EditText nbrclass =(EditText) dialog.findViewById(R.id.nbrclass);
        EditText namprof =(EditText) dialog.findViewById(R.id.namprof);

        nbrclass.setText(sea.getNbrclass());

        spintypesea(dialog);
        spinmod(dialog);

        Button btnValider = (Button) dialog.findViewById(R.id.btnvalid);
        btnValider.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Spinner nammod = dialog.findViewById(R.id.nammod);
                Spinner spinnertypesea =(Spinner) dialog.findViewById(R.id.spinnertypemod);
                EditText nbrclass =(EditText) dialog.findViewById(R.id.nbrclass);
                EditText namprof =(EditText) dialog.findViewById(R.id.namprof);
                int idsea=txt.getId();
                sea=new Seance();
                sea.setModule(nammod.getSelectedItem().toString());
                sea.setTypesea(spinnertypesea.getSelectedItem().toString());
                sea.setNbrclass(nbrclass.getText().toString());
                sea.setProfsea(namprof.getText().toString());
                mydb.editSeance(sea,idsea);
                seashow();
                dialog.dismiss();
            } });
    }

    public void dialidel(final TextViewWithContextMenuInfo txt) {

        final Dialog dialog4 = new Dialog(this); // créer le dialogue
        dialog4.setContentView(R.layout.dialog_del);
        dialog4.show();
        Button btnValider = (Button) dialog4.findViewById(R.id.btnvaliddel);
        btnValider.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mydb.delSeance(txt.getId());
                txt.setText("");
                dialog4.dismiss();
                seashow();
            } });

        Button btnCanc = (Button) dialog4.findViewById(R.id.btncancdel);
        btnCanc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                dialog4.dismiss();
            } });

    }

    public void dialodetmds(final TextViewWithContextMenuInfo txt) {

        final Dialog dialog3 = new Dialog(this); // créer le dialogue
        dialog3.setContentView(R.layout.dialog_det);
        dialog3.show();

        sea=new Seance();
        sea=seafound(txt.getId());
        TextView nammodtv=dialog3.findViewById(R.id.nammodtv);
        TextView seatypetv=dialog3.findViewById(R.id.seatypetv);
        TextView seaclasstv=dialog3.findViewById(R.id.seaclasstv);
        TextView seaprofetv=dialog3.findViewById(R.id.seaprofetv);

        nammodtv.setText(sea.getModule());
        seatypetv.setText(sea.getTypesea());
        seaclasstv.setText(sea.getNbrclass());
        seaprofetv.setText(sea.getProfsea());
        Button btnValider = (Button) dialog3.findViewById(R.id.btnok);
        btnValider.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                dialog3.dismiss();
            } });
    }

    private Seance seafound(int id) {
        mydb = new DBHandler(this) ;
        sea=new Seance();
        Cursor cursor = mydb.showSeance();
        cursor.moveToFirst();
        for (int i=0;i<30;i++)
        {
            if(cursor.getInt(1)==id)
            {
                sea.setModule(cursor.getString(2));
                sea.setTypesea(cursor.getString(3));
                sea.setNbrclass(cursor.getString(4));
                sea.setProfsea(cursor.getString(5));
                break;
            }
            else
            {
                cursor.moveToNext();
            }
        }
        return sea;
    }

    public void dialoadd(final TextViewWithContextMenuInfo txt) {

        final Dialog dialog = new Dialog(this); // créer le dialogue
        dialog.setContentView(R.layout.dialog_add);
        dialog.setTitle(getResources().getString(R.string.dialaddtitle));
        dialog.show();
        spintypesea(dialog);
        spinmod(dialog);
        Button btnValider = (Button) dialog.findViewById(R.id.btnvalid);
        btnValider.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Spinner nammod = dialog.findViewById(R.id.nammod);
                Spinner spinnertypesea =(Spinner) dialog.findViewById(R.id.spinnertypemod);
                EditText nbrclass =(EditText) dialog.findViewById(R.id.nbrclass);
                EditText namprof =(EditText) dialog.findViewById(R.id.namprof);
                int idsea=txt.getId();
                sea=new Seance();
                sea.setModule(nammod.getSelectedItem().toString());
                sea.setTypesea(spinnertypesea.getSelectedItem().toString());
                sea.setNbrclass(nbrclass.getText().toString());
                sea.setProfsea(namprof.getText().toString());
                mydb.editSeance(sea,idsea);
                seashow();
                dialog.dismiss();
            } });


        // afficher le dialogue
    }

    private void seashow() {
        mydb = new DBHandler(this) ;
        Cursor cursor = mydb.showSeance();
        cursor.moveToFirst();
        for (int i=0;i<30;i++)
        {
            if(cursor.getString(2).equals(" ")){ cursor.moveToNext(); }
            else
            {
                String classtype="";
                if((cursor.getString(3)).equals("Cour")) {classtype+=getResources().getString(R.string.typeclassC);}
                if((cursor.getString(3)).equals("TD")) {classtype+=getResources().getString(R.string.typeclassTD);}
                if((cursor.getString(3)).equals("TP")) {classtype+=getResources().getString(R.string.typeclassTP);}

                seance[i].setText(cursor.getString(2)+" - "+cursor.getString(3)+" @ "+ classtype +" "+ cursor.getString(4)+" ( "+cursor.getString(5)+" )");
                if(cursor.getString(2).equals("DAM")){ seance[i].setBackgroundColor(Color.parseColor("#275707"));}
                if(cursor.getString(2).equals("CRYPT")){ seance[i].setBackgroundColor(Color.parseColor("#FF8C02"));}
                if(cursor.getString(2).equals("RS")){ seance[i].setBackgroundColor(Color.parseColor("#86828B"));}
                if(cursor.getString(2).equals("SI")){ seance[i].setBackgroundColor(Color.parseColor("#0A6AFF"));}
                if(cursor.getString(2).equals("WEBSEM")){ seance[i].setBackgroundColor(Color.parseColor("#FE2445"));}
                cursor.moveToNext();
            }
        }


    }

    public void dialomdftm(final TextView v) {
        final Dialog dialog2 = new Dialog(this); // créer le dialogue
        dialog2.setContentView(R.layout.dialog_mdf_time);
        dialog2.show();

        spinhbeg(dialog2);
        spinmbeg(dialog2);
        spinhend(dialog2);
        spinmend(dialog2);
        hour=new Hour();
        hour.setIdhour(v.getId());
        Button btnValider = (Button) dialog2.findViewById(R.id.btnvalid);
        btnValider.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Spinner hbeg=(Spinner) dialog2.findViewById(R.id.hbeg);
                Spinner mbeg=(Spinner) dialog2.findViewById(R.id.mbeg);
                Spinner hend=(Spinner) dialog2.findViewById(R.id.hend);
                Spinner mend=(Spinner) dialog2.findViewById(R.id.mend);


                hour.setHbeg(hbeg.getSelectedItem().toString());
                hour.setMbeg(mbeg.getSelectedItem().toString());
                hour.setHend(hend.getSelectedItem().toString());
                hour.setMend(mend.getSelectedItem().toString());

                mydb.editHour(hour);
                hourshow();
                dialog2.dismiss();
            } });
    }

    private void hourshow() {
        mydb = new DBHandler(this) ;
        Cursor cursor = mydb.showHour();
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++)
        {
            h[i].setText(cursor.getString(2)+"."+cursor.getString(3)+"-"+cursor.getString(4)+"."+cursor.getString(5));
            cursor.moveToNext();
        }
    }

    public void menuconlinkDS() {

        for(int i=0;i<30;i++) {registerForContextMenu(seance[i]);}

    }

    public void findview() {
        mydb=new DBHandler(this);
        if(mydb.showSeance()==null)
        {
            mydb.addSeance(R.id.D1S1);
            mydb.addSeance(R.id.D1S2);
            mydb.addSeance(R.id.D1S3);
            mydb.addSeance(R.id.D1S4);
            mydb.addSeance(R.id.D1S5);
            mydb.addSeance(R.id.D2S1);
            mydb.addSeance(R.id.D2S2);
            mydb.addSeance(R.id.D2S3);
            mydb.addSeance(R.id.D2S4);
            mydb.addSeance(R.id.D2S5);
            mydb.addSeance(R.id.D3S1);
            mydb.addSeance(R.id.D3S2);
            mydb.addSeance(R.id.D3S3);
            mydb.addSeance(R.id.D3S4);
            mydb.addSeance(R.id.D3S5);
            mydb.addSeance(R.id.D4S1);
            mydb.addSeance(R.id.D4S2);
            mydb.addSeance(R.id.D4S3);
            mydb.addSeance(R.id.D4S4);
            mydb.addSeance(R.id.D4S5);
            mydb.addSeance(R.id.D5S1);
            mydb.addSeance(R.id.D5S2);
            mydb.addSeance(R.id.D5S3);
            mydb.addSeance(R.id.D5S4);
            mydb.addSeance(R.id.D5S5);
            mydb.addSeance(R.id.D6S1);
            mydb.addSeance(R.id.D6S2);
            mydb.addSeance(R.id.D6S3);
            mydb.addSeance(R.id.D6S4);
            mydb.addSeance(R.id.D6S5);

        }


        seance[0]=findViewById(R.id.D1S1);
        seance[1]= findViewById(R.id.D1S2);
        seance[2]=findViewById(R.id.D1S3);
        seance[3]= findViewById(R.id.D1S4);
        seance[4]=findViewById(R.id.D1S5);

        seance[5]= findViewById(R.id.D2S1);
        seance[6]= findViewById(R.id.D2S2);
        seance[7]=findViewById(R.id.D2S3);
        seance[8]=findViewById(R.id.D2S4);
        seance[9]=findViewById(R.id.D2S5);

        seance[10]=findViewById(R.id.D3S1);
        seance[11]= findViewById(R.id.D3S2);
        seance[12]=findViewById(R.id.D3S3);
        seance[13]=findViewById(R.id.D3S4);
        seance[14]=findViewById(R.id.D3S5);

        seance[15]=findViewById(R.id.D4S1);
        seance[16]= findViewById(R.id.D4S2);
        seance[17]=findViewById(R.id.D4S3);
        seance[18]= findViewById(R.id.D4S4);
        seance[19]=findViewById(R.id.D4S5);

        seance[20]= findViewById(R.id.D5S1);
        seance[21]=findViewById(R.id.D5S2);
        seance[22]=findViewById(R.id.D5S3);
        seance[23]=findViewById(R.id.D5S4);
        seance[24]= findViewById(R.id.D5S5);

        seance[25]=findViewById(R.id.D6S1);
        seance[26]=findViewById(R.id.D6S2);
        seance[27]=findViewById(R.id.D6S3);
        seance[28]=findViewById(R.id.D6S4);
        seance[29]=findViewById(R.id.D6S5);

        if (mydb.showHour()==null)
        {
            hour=new Hour();
            hour.setIdhour(R.id.S1);
            hour.setHbeg("08");
            hour.setMbeg("00");
            hour.setHend("09");
            hour.setMend("30");
            mydb.addHour(hour);

            hour=new Hour();
            hour.setIdhour(R.id.S2);
            hour.setHbeg("09");
            hour.setMbeg("40");
            hour.setHend("11");
            hour.setMend("10");
            mydb.addHour(hour);

            hour=new Hour();
            hour.setIdhour(R.id.S3);
            hour.setHbeg("11");
            hour.setMbeg("20");
            hour.setHend("12");
            hour.setMend("50");
            mydb.addHour(hour);

            hour=new Hour();
            hour.setIdhour(R.id.S4);
            hour.setHbeg("13");
            hour.setMbeg("00");
            hour.setHend("14");
            hour.setMend("30");
            mydb.addHour(hour);

            hour=new Hour();
            hour.setIdhour(R.id.S5);
            hour.setHbeg("14");
            hour.setMbeg("40");
            hour.setHend("16");
            hour.setMend("10");
            mydb.addHour(hour);
        }

        h[0]=(TextView) findViewById(R.id.S1);
        h[1]=(TextView) findViewById(R.id.S2);
        h[2]=(TextView) findViewById(R.id.S3);
        h[3]=(TextView) findViewById(R.id.S4);
        h[4]=(TextView) findViewById(R.id.S5);

    }

    public void spintypesea(Dialog dialog) {
        Spinner spinnertypesea=(Spinner) dialog.findViewById(R.id.spinnertypemod);

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.seatype, android.R.layout.simple_spinner_item);
////        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////
////        spinnertypesea.setAdapter(adapter);




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.seatype, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertypesea.setAdapter(adapter);

        spinnertypesea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                int index = arg0.getSelectedItemPosition();

                // storing string resources into Array
                String [] spintypesea = getResources().getStringArray(R.array.seatype);

                String lvldb=(spintypesea[index]).toString();

              }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }

    private void spinhbeg(Dialog dialog) {

        Spinner dex=(Spinner) dialog.findViewById(R.id.hbeg);
        final String[]  nbr={"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","00"};
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
    private void spinmbeg(Dialog dialog) {

        Spinner dex=(Spinner) dialog.findViewById(R.id.mbeg);
        final String[]  nbr={"00","05","10","15","20","25","30","35","40","45","50","55"};
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
    private void spinhend(Dialog dialog) {

        Spinner dex=(Spinner) dialog.findViewById(R.id.hend);
        final String[]  nbr={"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","00"};
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
    private void spinmend(Dialog dialog) {

        Spinner dex=(Spinner) dialog.findViewById(R.id.mend);
        final String[]  nbr={"00","05","10","15","20","25","30","35","40","45","50","55"};
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

        Spinner dex=(Spinner) dialog.findViewById(R.id.nammod);
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
}
