package com.example.saif.gradetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "Gradetracker.db";

    private static final String TABLE_NAME = "grade_table";

    private static final String COL1 = "ID";

    private static final String COL2 = "COURSE_NAME";

    private static final String COL3 = "CREDIT";

    private static final String COL4 = "GRADE";





    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        String createTable = "CREATE TABLE "+TABLE_NAME+" ( ID INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +COURSE_NAME+" TEXT, "+CREDIT+" TEXT, "+GRADE+" TEXT)";

                */

        String createTable = "CREATE TABLE "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,COURSE_NAME TEXT,CREDIT TEXT,GRADE TEXT)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }



    public boolean insertData(String item1,String item2,String item3){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,item1);
        contentValues.put(COL3,item2);
        contentValues.put(COL4,item3);

        Log.d(TAG,"addData : Adding "+item1+" "+item2+""+item3+" to "+TABLE_NAME);

        long result = db.insert(TABLE_NAME,null,contentValues);

        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }



    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getAllItemOf(String course_name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+
                " WHERE "+COL2+" = '" + course_name +"'";

        Cursor data = db.rawQuery(query,null);
        return data;

    }

    public Cursor getAllItemOfId(int course_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+
                " WHERE "+COL1+" = '" + course_id +"'";

        Cursor data = db.rawQuery(query,null);
        return data;

    }

    //query for updating database table
    public void updateData(int id, String newCourseName,String newCredit,String newGrade){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "+TABLE_NAME+" SET "+COL2+" = '"+newCourseName+"',"+COL3+" = '"+newCredit+"',"+COL4+" = '"+newGrade+"' WHERE "+COL1+
                "= '"+id+"'";

        Log.d(TAG,"updateName: query: "+query);
        db.execSQL(query);

    }

    //query for deleting database
    public void deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM "+TABLE_NAME+" WHERE "+COL1+" = '"+id+"'";
        Log.d(TAG,"deleteName: query: "+query);

        db.execSQL(query);

    }

    /*public int countRows(){
        int countRow = 0;
        String query = "SELECT COUNT(*) FROM "+TABLE_NAME;

        Cursor cursor = getReadableDatabase().rawQuery(query,null);


        if (cursor.getCount()>0){
            cursor.moveToFirst();
            countRow = cursor.getInt(0);
        }
        cursor.close();
        return countRow;

    }*/



}
