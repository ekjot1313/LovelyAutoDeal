package com.example.user.lovelyautodeal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;


/**
 * Created by user on 08-Jan-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    String id,make,regdno,model,engineno,chassisno,purchasedfrom,purchaseraddress,purchaserphone,purchasedate,soldto,selleraddress,sellerphone,sellingdate,extrainformation,lastupdated;
Context context;

    public final static String DATABASE_NAME="LAD";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 10);
        this.context=context;
        SQLiteDatabase db=this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        deleteTable();
        onCreate(db);
    }

    public void createTable(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("create table info(" +
                "id INTEGER primary key AUTOINCREMENT not null" +
                ",make text " +
                ",regdno text " +
                ",chassisno text  " +
                ",engineno text  " +
                ",model INTEGER  " +
                ",purchasedfrom text  " +
                ",purchaseraddress text  " +
                ",purchaserphone text  " +
                ",purchasedate date  " +
                ",soldto text  " +
                ",selleraddress text  " +
                ",sellerphone text  " +
                ",sellingdate date  " +
                ",extrainformation text  " +
                ",lastupdated timestamp DEFAULT (strftime('%Y-%m-%d %H:%M:%S','now', 'localtime')) );");
    }
    public void deleteTable(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("drop table if exists info;");
    }
    public boolean insertData(String[] values,boolean isBackup){


        copyValues(values);
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        copyContentValues(contentValues);

        if(!isBackup) {
            contentValues.remove("id");
            contentValues.remove("soldto");
            contentValues.remove("selleraddress");
            contentValues.remove("sellerphone");

        }
        long result=db.insert("info",null,contentValues);
        if(result==-1)
            return false;
        else
            return true;

    }
    public boolean updateData(String id,String[] values){
        copyValues(values);
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        copyContentValues(contentValues);
        contentValues.remove("id");

        long rowsAffected=db.update("info",contentValues,"id=?",new String[]{id});
        db.execSQL("update info set lastupdated=(strftime('%Y-%m-%d %H:%M:%S','now', 'localtime')) where id="+id+";");
        if(rowsAffected>0){return true;}
        return false;
    }
    public Cursor getAllData(String query){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery(query,null);
        return res;

    }


    public boolean deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long rowsAffected = db.delete("info", "id=?", new String[]{id});

        if (rowsAffected > 0) {
            return true;
        }
        return false;
    }



    public void copyValues(String[] values){

        id=values[0];
        make=values[1];
        regdno=values[2];
        chassisno=values[3];
        engineno=values[4];
        model=values[5];
        purchasedfrom=values[6];
        purchaseraddress=values[7];
        purchaserphone=values[8];
        purchasedate=values[9];
        soldto=values[10];
        selleraddress=values[11];
        sellerphone=values[12];
        sellingdate=values[13];
        extrainformation=values[14];
        lastupdated=values[15];
    }

    public ContentValues copyContentValues(ContentValues contentValues){

        contentValues.put("id",Integer.parseInt(id.equals("")?"0":id));
        contentValues.put("make",make);
        contentValues.put("regdno",regdno);
        contentValues.put("chassisno",chassisno);
        contentValues.put("engineno",engineno);
        contentValues.put("model",Integer.parseInt(model.equals("")?"0":model));
        contentValues.put("purchasedfrom",purchasedfrom);
        contentValues.put("purchaseraddress",purchaseraddress);
        contentValues.put("purchaserphone",purchaserphone);
        contentValues.put("purchasedate",purchasedate);
        contentValues.put("soldto",soldto);
        contentValues.put("selleraddress",selleraddress);
        contentValues.put("sellerphone",sellerphone);
        contentValues.put("sellingdate",sellingdate);
        contentValues.put("extrainformation",extrainformation);

        return contentValues;
    }
}
