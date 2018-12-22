package com.example.abl.studentagenda;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class register extends AppCompatActivity {

    private DBHandler mydb;
    protected User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
                Spinner  sexe=findViewById(R.id.sexe);
                Spinner  langage=findViewById(R.id.lang);
                Spinner  level=findViewById(R.id.niv);
                user=new User();
                user.setFirstname(fname.getText().toString());
                user.setSurname(sname.getText().toString());
                user.setSexe(sexe.getSelectedItem().toString());
                user.setLevel(level.getSelectedItem().toString());
                user.setLangage(langage.getSelectedItem().toString());
                mydb.addUser(user);

                Intent intent=new Intent(register.this,BeginModule.class);
                startActivity(intent);
            } });
    }
    public void langue(String lang){

        Locale myLocal=new Locale(lang);
        Locale.setDefault(myLocal);
        Configuration config=new Configuration();
        config.locale=myLocal;
        Resources resources=this.getResources();
        resources.updateConfiguration(config,resources.getDisplayMetrics());
        recreate();
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
        final Spinner niv = (Spinner) this.findViewById(R.id.lang);

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
                /*if(niv.getSelectedItem().toString().equals("Français"))
                {
                    langue("fr");
                }else
                {
                    langue("en");
                }*/


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
