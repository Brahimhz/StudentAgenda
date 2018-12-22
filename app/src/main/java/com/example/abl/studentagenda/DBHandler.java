package com.example.abl.studentagenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db";
    Context context;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CrTbuser= "CREATE TABLE IF NOT EXISTS user ( _id INTEGER PRIMARY KEY AUTOINCREMENT ,fname TEXT ,sname TEXT,sexe TEXT,level TEXT,langege TEXT)";
        db.execSQL(CrTbuser);
        String CrTbsea="CREATE TABLE IF NOT EXISTS seance ( _id INTEGER PRIMARY KEY AUTOINCREMENT ,idsea INTEGER , module TEXT , typesea TEXT , nbrclass TEXT , profsea TEXT )";
        db.execSQL(CrTbsea);
        String CrTbh="CREATE TABLE IF NOT EXISTS hour ( _id INTEGER PRIMARY KEY AUTOINCREMENT ,idhour TEXT , hbeg TEXT , mbeg TEXT , hend TEXT , mend TEXT )";
        db.execSQL(CrTbh);
        String modTab="CREATE TABLE IF NOT EXISTS module ( _id INTEGER PRIMARY KEY AUTOINCREMENT ,name TEXT UNIQUE ,nameabv TEXT ,coff REAL ,modevtest REAL ,modevexam REAL,notetd REAL,notetp REAL,noteexam REAL )";
        db.execSQL(modTab);
        String CrTbexam= "CREATE TABLE IF NOT EXISTS exam ( _id INTEGER PRIMARY KEY AUTOINCREMENT ,modex TEXT ,profex TEXT,dayex TEXT,hex TEXT,lieuex TEXT,superex TEXT)";
        db.execSQL(CrTbexam);
        String CrTbcontact="CREATE TABLE IF NOT EXISTS contact ( _id INTEGER PRIMARY KEY AUTOINCREMENT ,nom TEXT ,prenom TEXT ,tel TEXT UNIQUE ,sexe TEXT,type TEXT)";
        db.execSQL(CrTbcontact);
        String taskTab="CREATE TABLE IF NOT EXISTS task ( _id INTEGER PRIMARY KEY AUTOINCREMENT ,sub TEXT ,det TEXT,datebeg TEXT,datefin TEXT)";
        db.execSQL(taskTab);
        String rdvTab="CREATE TABLE IF NOT EXISTS rdv ( _id INTEGER PRIMARY KEY AUTOINCREMENT ,sub TEXT ,day TEXT,mou TEXT,year TEXT)";
        db.execSQL(rdvTab);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS seance");
        db.execSQL("DROP TABLE IF EXISTS hour");
        db.execSQL("DROP TABLE IF EXISTS module");
        db.execSQL("DROP TABLE IF EXISTS exam");
        db.execSQL("DROP TABLE IF EXISTS contact");
        db.execSQL("DROP TABLE IF EXISTS task");
        db.execSQL("DROP TABLE IF EXISTS rdv");
        onCreate(db);
    }

    public void addRdv(RDV rdv)
    {

        ContentValues valeurs = new ContentValues();
        valeurs.put("sub", rdv.getSubrdv());
        valeurs.put("day", rdv.getDayrdv());
        valeurs.put("mou", rdv.getMounthrdv());
        valeurs.put("year", rdv.getYearrdv());

        try {
            this.getWritableDatabase().insertOrThrow("rdv", null, valeurs);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
    }

    public Cursor showRdv()
    {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM rdv", null);
        if (cursor.getCount() == 0) {
            Toast.makeText(context, "Empty", Toast.LENGTH_LONG).show();
            return null;
        }
        return cursor;
    }
    public void addUser(User user) {

        ContentValues valeurs = new ContentValues();
        valeurs.put("fname", user.getFirstname());
        valeurs.put("sname", user.getSurname());
        valeurs.put("sexe", user.getSexe());
        valeurs.put("level", user.getLevel());
        valeurs.put("langege", user.getLangage());
        try {
            this.getWritableDatabase().insertOrThrow("user", null, valeurs);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }

    }


    public Cursor showUser() {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM user", null);
        if (cursor.getCount() == 0) {

            return null;
        }
        return cursor;
    }

    public void addSeance(int idsea)
    {
        ContentValues valeurs = new ContentValues();
        valeurs.put("idsea",idsea);
        valeurs.put("module"," ");
        valeurs.put("typesea"," ");
        valeurs.put("nbrclass"," ");
        valeurs.put("profsea"," ");

        try{
            this.getWritableDatabase().insertOrThrow("seance",null,valeurs);
        }catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG)
                    .show();
        }

    }



    public void editSeance(Seance seance,int idsea)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues valeurs = new ContentValues();
        valeurs.put("idsea",idsea);
        valeurs.put("module",seance.getModule());
        valeurs.put("typesea",seance.getTypesea());
        valeurs.put("nbrclass",seance.getNbrclass());
        valeurs.put("profsea",seance.getProfsea());
        db.update("seance",valeurs,"idsea=?",new String[]{String.valueOf(idsea)});
    }

    public void delSeance(int idsea)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues valeurs = new ContentValues();
        valeurs.put("idsea",idsea);
        valeurs.put("module"," ");
        valeurs.put("typesea"," ");
        valeurs.put("nbrclass"," ");
        valeurs.put("profsea"," ");
        db.update("seance",valeurs,"idsea=?",new String[]{String.valueOf(idsea)});
    }

    public Cursor showSeance()
    {
        Cursor cursor=this.getReadableDatabase().rawQuery("SELECT * FROM seance",null);
        if (cursor.getCount() == 0){
            Toast.makeText(context,"Empty",Toast.LENGTH_LONG)
                    .show();
            return null;
        }
        return cursor;
    }
    public Cursor showHour()
    {
        Cursor cursor=this.getReadableDatabase().rawQuery("SELECT * FROM hour",null);
        if (cursor.getCount() == 0){
            Toast.makeText(context,"Empty",Toast.LENGTH_LONG)
                    .show();
            return null;
        }
        return cursor;
    }
    public void addHour(Hour hour)
    {

        ContentValues valeurs = new ContentValues();
        valeurs.put("idhour",hour.getIdhour());
        valeurs.put("hbeg",hour.getHbeg());
        valeurs.put("mbeg",hour.getMbeg());
        valeurs.put("hend",hour.getHend());
        valeurs.put("mend",hour.getMend());

        try{
            this.getWritableDatabase().insertOrThrow("hour",null,valeurs);
        }catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG)
                    .show();
        }

    }
    public void editHour(Hour hour) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues valeurs = new ContentValues();
        valeurs.put("idhour",hour.getIdhour());
        valeurs.put("hbeg",hour.getHbeg());
        valeurs.put("mbeg",hour.getMbeg());
        valeurs.put("hend",hour.getHend());
        valeurs.put("mend",hour.getMend());
        db.update("hour",valeurs,"idhour=?",new String[]{String.valueOf(hour.getIdhour())});
    }

    public void addModule(Module module)
    {

        ContentValues valeurs = new ContentValues();
        valeurs.put("name", module.getName());
        valeurs.put("nameabv", module.getAbvname());
        valeurs.put("coff", module.getCoff());
        valeurs.put("modevtest", module.getModevtest());
        valeurs.put("modevexam", module.getModevexam());
        valeurs.put("notetd", module.getNotetd());
        valeurs.put("notetp", module.getNotetp());
        valeurs.put("noteexam", module.getNoteexam());
        try{
            this.getWritableDatabase().insertOrThrow("module",null,valeurs);
        }catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    public void editModule(Module module,String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues valeurs = new ContentValues();
        valeurs.put("name", module.getName());
        valeurs.put("nameabv", module.getAbvname());
        valeurs.put("coff", module.getCoff());
        valeurs.put("modevtest", module.getModevtest());
        valeurs.put("modevexam", module.getModevexam());

        db.update("module",valeurs,"name=?",new String[]{name});
    }

    public void delModule(String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("module","name=?",new String[]{name});
    }

    public Cursor showModule()
    {
        Cursor cursor=this.getReadableDatabase().rawQuery("SELECT * FROM module",null);
        if (cursor.getCount() == 0){
            Toast.makeText(context,"Empty",Toast.LENGTH_LONG).show();
            return null;
        }
        return cursor;
    }

    public void addnote(Module module, String name) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues valeurs = new ContentValues();
        valeurs.put("notetd", module.getNotetd());
        valeurs.put("notetp", module.getNotetp());
        valeurs.put("noteexam", module.getNoteexam());
        try {
            db.update("module",valeurs,"name=?",new String[]{name});
        }catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void addExam(Examen ex) {

        ContentValues valeurs = new ContentValues();
        valeurs.put("modex", ex.getNomex());
        valeurs.put("profex", ex.getProfex());
        valeurs.put("dayex", ex.getDateex());
        valeurs.put("hex", ex.getHex());
        valeurs.put("lieuex", ex.getLieuex());
        valeurs.put("superex", ex.getSuperex());
        try {
            this.getWritableDatabase().insertOrThrow("exam", null, valeurs);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }

    }

    public Cursor showPerson() {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM exam", null);
        if (cursor.getCount() == 0) {
            Toast.makeText(context, "Empty", Toast.LENGTH_LONG).show();
            return null;
        }
        return cursor;
    }

    public void editExam(Examen ex, String nomex) {

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues valeurs = new ContentValues();
        valeurs.put("modex", ex.getNomex());
        valeurs.put("profex", ex.getProfex());
        valeurs.put("dayex", ex.getDateex());
        valeurs.put("hex", ex.getHex());
        valeurs.put("lieuex", ex.getLieuex());
        valeurs.put("superex", ex.getSuperex());
        db.update("exam",valeurs,"modex=?",new String[]{nomex});
    }

    public void delExam(String nomex) {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("exam","modex=?",new String[]{nomex});
    }

    public void addContact(Contact c)
    {

        ContentValues valeurs = new ContentValues();
        valeurs.put("nom", c.getNom());
        valeurs.put("prenom", c.getPrenom());
        valeurs.put("tel", c.getTel());
        valeurs.put("sexe",c.getSexe());
        valeurs.put("type",c.getType());
        try{
            this.getWritableDatabase().insertOrThrow("contact",null,valeurs);
        }catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG)
                    .show();
        }

    }

    public Cursor showContactbin()
    {
        Cursor cursor=this.getReadableDatabase().rawQuery("SELECT * FROM contact where type='Binome'",null);
        if (cursor.getCount() == 0){
            Toast.makeText(context,"Empty",Toast.LENGTH_LONG)
                    .show();
            return null;
        }
        return cursor;
    }
    public Cursor showContactenc()
    {
        Cursor cursor=this.getReadableDatabase().rawQuery("SELECT * FROM contact where type='Framing Teacher'",null);
        if (cursor.getCount() == 0){
            Toast.makeText(context,"Empty",Toast.LENGTH_LONG)
                    .show();
            return null;
        }
        return cursor;
    }

    public void delcontact(String tel) {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("contact","tel=?",new String[]{tel});
    }

    public void editContact(Contact c1, String tel) {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues valeurs = new ContentValues();
        valeurs.put("nom", c1.getNom());
        valeurs.put("prenom", c1.getPrenom());
        valeurs.put("tel", c1.getTel());
        valeurs.put("sexe",c1.getSexe());
        valeurs.put("type",c1.getType());
        db.update("contact",valeurs,"tel=?",new String[]{tel});
    }

    public void addTask(Task task) {
        ContentValues valeurs = new ContentValues();
        valeurs.put("sub", task.getSubtask());
        valeurs.put("det", task.getDetails());
        valeurs.put("datebeg", task.getDatebeg());
        valeurs.put("datefin", task.getDatefin());
        try{
            this.getWritableDatabase().insertOrThrow("task",null,valeurs);
        }catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public Cursor showTask()
    {
        Cursor cursor=this.getReadableDatabase().rawQuery("SELECT * FROM task",null);
        if (cursor.getCount() == 0){
            Toast.makeText(context,"Empty",Toast.LENGTH_LONG).show();
            return null;
        }
        return cursor;
    }

    public Task showdetTask(String subtask)
    {
        Task task=new Task();
        Cursor cursor=this.getReadableDatabase().rawQuery("SELECT * FROM task",null);
        if (cursor.getCount() == 0){
            Toast.makeText(context,"Empty",Toast.LENGTH_LONG).show();
            return null;
        }else {
            cursor.moveToFirst();
            for (int i=0;i<(cursor.getCount());i++)
            {
                if (cursor.getString(1).equals(subtask))
                {
                    task.setSubtask(cursor.getString(1));
                    task.setDetails(cursor.getString(2));
                    task.setDatebeg(cursor.getString(3));
                    task.setDatefin(cursor.getString(4));
                    return task;
                }else
                {
                    cursor.moveToNext();
                }
            }
        }
        return  null;
    }

    public void deleteTask(String subtask) {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("task","sub=?",new String[]{subtask});
    }

    public void editTask(Task task, String subtas) {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues valeurs = new ContentValues();
        valeurs.put("sub", task.getSubtask());
        valeurs.put("det", task.getDetails());
        valeurs.put("datebeg", task.getDatebeg());
        valeurs.put("datefin", task.getDatefin());

        db.update("task",valeurs,"sub=?",new String[]{subtas});

    }

    public void editUser(User user, String string) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues valeurs = new ContentValues();
        valeurs.put("fname", user.getFirstname());
        valeurs.put("sname", user.getSurname());
        valeurs.put("sexe", user.getSexe());
        valeurs.put("level", user.getLevel());
        valeurs.put("langege", user.getLangage());
        db.update("user",valeurs,"fname=?",new String[]{string});
    }
}
