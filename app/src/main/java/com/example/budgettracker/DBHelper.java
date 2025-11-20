package com.example.budgettracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "budget.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CALCULATIONS = "calculations";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_N1 = "n1";
    private static final String COLUMN_N2 = "n2";
    private static final String COLUMN_SUM = "sum";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_CALCULATIONS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_N1 + " REAL, "
                + COLUMN_N2 + " REAL, "
                + COLUMN_SUM + " REAL"
                + ");";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALCULATIONS);
        onCreate(db);
    }

    public void insertCalculation(double n1, double n2, double sum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_N1, n1);
        values.put(COLUMN_N2, n2);
        values.put(COLUMN_SUM, sum);
        db.insert(TABLE_CALCULATIONS, null, values);
        db.close();
    }

    public double getTotalSumFromDb() {
        double total = 0.0;
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT SUM(" + COLUMN_SUM + ") AS total_sum FROM " + TABLE_CALCULATIONS;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            int index = cursor.getColumnIndex("total_sum");
            if (index != -1) {
                total = cursor.getDouble(index);
            }
        }

        cursor.close();
        db.close();

        return total;
    }
    public java.util.List<String> getAllCalculationsAsStrings() {
        java.util.List<String> list = new java.util.ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_N1 + ", " + COLUMN_N2 + ", " + COLUMN_SUM +
                " FROM " + TABLE_CALCULATIONS +
                " ORDER BY " + COLUMN_ID + " DESC";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                double n1 = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_N1));
                double n2 = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_N2));
                double sum = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_SUM));

                String row = n1 + " + " + n2 + " = " + sum;
                list.add(row);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }
    public void deleteAllCalculations() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CALCULATIONS, null, null);
        db.close();
    }

}
