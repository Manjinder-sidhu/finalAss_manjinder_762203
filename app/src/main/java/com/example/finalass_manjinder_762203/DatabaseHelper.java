package com.example.finalass_manjinder_762203;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PersonDatabase";
    private static final int DATABASE_VERSION =1;
    private static final String TABLE_NAME = "Person";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "firstname";
    private static final String COLUMN_LAST_NAME = "lastname";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_PHONE = "phone";

    public DatabaseHelper(@Nullable Context context) {
        //cursor factory is when you are using your own custom cursor

        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID +" INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT," +
                COLUMN_FIRST_NAME + " varchar(200) NOT NULL," +
                COLUMN_LAST_NAME + " varchar(200) NOT NULL," +
                COLUMN_ADDRESS + " varchar(200) NOT NULL," +
                COLUMN_PHONE + " NUMBER NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }
    boolean addPerson(String firstname,String lastname ,String address , String phone){

        //INORDER OT INSERT ITEM INTO DATABAASE
        //WE NEED A WRITABLE DATABASE
        //THIS METHOS RETURN A SQL DATABASE
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //WE NEED TO DEFINE A CONTENT INSTANCE

        ContentValues cv = new ContentValues();

        //THE FIRST ARGUMENT OF THE PUT METHOD IS THE COLUMN NAME AND THE SECOND VALUE IS THE SHOWN AS BELOW

        cv.put(COLUMN_FIRST_NAME, firstname);
        cv.put(COLUMN_LAST_NAME,lastname);
        cv.put(COLUMN_ADDRESS,address);
        cv.put(COLUMN_PHONE,phone);

        //insert method returns row number if the inseriton is successfully and -1 if the unsuccessfull

        return   sqLiteDatabase.insert(TABLE_NAME, null, cv) != -1;


    }

    Cursor getAllPersons(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    boolean updateperson(int id,String firstname,String lastname,String address , String phone){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRST_NAME, firstname);
        cv.put(COLUMN_LAST_NAME,lastname);
        cv.put(COLUMN_ADDRESS,address);
        cv.put(COLUMN_PHONE,phone);

        //this method returns the number of rows effected

        return sqLiteDatabase.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;

    }

    boolean deletePerson(int id){
        SQLiteDatabase sqLiteDatabase  = getWritableDatabase();

        //the delete method returns the  number of rows effected
        return sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID +"=?", new String[]{String.valueOf(id)}) > 0;
    }

}
