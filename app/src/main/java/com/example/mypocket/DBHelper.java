package com.example.mypocket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String USER_TABLE = "USER_TABLE";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String FIRSTNAME = "FIRSTNAME";
    private static final String LASTNAME = "LASTNAME";


    private static final String EXPENSE_TABLE = "EXPENSE_TABLE";
    private static final String AMOUNT = "AMOUNT";
    private static final String DATE = "DATE";
    private static final String CATEGORY = "CATEGORY";
    private static final String NOTES = "NOTES";
    private static final String EXPENSE_ID = "EXPENSE_ID";

    private static final String INCOME_TABLE = "INCOME_TABLE";
    private static final String INCOME_ID = "INCOME_ID";
    private static final String INCAMOUNT = "AMOUNT";
    private static final String INCDATE = "DATE";
    private static final String INCCATEGORY = "CATEGORY";
    private static final String INCNOTES = "NOTES";

    private static final String SAVINGS_TABLE = "SAVINGS_TABLE";
    private static final String SAVINGS_ID = "SAVINGS_ID";
    private static final String SAMOUNT = "AMOUNT";
    private static final String SDATE = "DATE";
    private static final String SNOTES = "NOTES";

    private static final String ECATEGORY_TABLE = "ECATEGORY_TABLE";
    private static final String ECAT_ID = "ECATEGORY_ID";
    private static final String ECATEGORY = "CATEGORY";

    public DBHelper(@Nullable Context context) {
        super(context, "expensetracker.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUser = "CREATE TABLE " + USER_TABLE + " (" + USERNAME + " TEXT PRIMARY KEY, " + FIRSTNAME + " TEXT, " + LASTNAME + " TEXT, " + PASSWORD + " TEXT )";
        db.execSQL(createUser);

        String createExpense = "CREATE TABLE " + EXPENSE_TABLE + " (" + EXPENSE_ID + " INTEGER PRIMARY KEY, " + USERNAME + " TEXT, " + DATE + " TEXT, " + CATEGORY + " TEXT, " + NOTES + " TEXT, " + AMOUNT + " REAL)";
        db.execSQL(createExpense);

        String createIncome= "CREATE TABLE " + INCOME_TABLE + " (" + INCOME_ID + " INTEGER PRIMARY KEY, " + USERNAME + " TEXT, " + INCDATE + " TEXT, " + INCCATEGORY + " TEXT, " + INCNOTES + " TEXT, " + INCAMOUNT + " REAL )";
        db.execSQL(createIncome);

        String createSavings= "CREATE TABLE " + SAVINGS_TABLE + " (" + SAVINGS_ID + " INTEGER PRIMARY KEY, " + USERNAME + " TEXT, " + SDATE + " TEXT, " + SNOTES + " TEXT, " + SAMOUNT + " REAL )";
        db.execSQL(createSavings);

        String createECategory = "CREATE TABLE " + ECATEGORY_TABLE + " (" + ECAT_ID + " INTEGER PRIMARY KEY, " + USERNAME + " TEXT, "  + ECATEGORY + " TEXT )";
        db.execSQL(createECategory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //none
    }

    public boolean setLogin(String user, String pass) {
        // check username and password
        boolean check = false;
        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE USERNAME ='" + user + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            if (user.matches(cursor.getString(0)) && pass.matches(cursor.getString(3))) {
                check = true;
            }
        }
        cursor.close();
        db.close();
        return check;
    }

    public boolean createUser(String firstname, String lastname, String username, String password) {
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FIRSTNAME, firstname);
        cv.put(LASTNAME, lastname);
        cv.put(USERNAME, username);
        cv.put(PASSWORD, password);
        long insert = db.insert(USER_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;

        return check;
    }

    public boolean createExpense(double amount, String user, String date, String category, String notes) {
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DATE, date);
        cv.put(NOTES, notes);
        cv.put(USERNAME, user);
        cv.put(CATEGORY, category);
        cv.put(AMOUNT, amount);
        long insert = db.insert(EXPENSE_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;

        return check;
    }

    public double getTotalExpenses(String user) {
        double total = 0.0;
        String queryString = "SELECT * FROM " + EXPENSE_TABLE + " WHERE USERNAME='" + user + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                double amount = cursor.getDouble(5);
                total += amount;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    public boolean createIncome(double amount, String user, String date, String category, String notes) {
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(INCDATE, date);
        cv.put(INCNOTES, notes);
        cv.put(USERNAME, user);
        cv.put(INCCATEGORY, category);
        cv.put(INCAMOUNT, amount);
        long insert = db.insert(INCOME_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;

        return check;
    }

    public double getTotalIncome(String user) {
        double totalinc = 0.0;
        String queryString = "SELECT * FROM " + INCOME_TABLE + " WHERE USERNAME='" + user + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                double amount = cursor.getDouble(5);
                totalinc += amount;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return totalinc;
    }
    public ArrayList<String> getAllExpenseDates(String user){
        ArrayList<String> dates =new ArrayList<>();
        String query = "SELECT DISTINCT DATE FROM EXPENSE_TABLE\n" +
                        "WHERE USERNAME = '"+user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                dates.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        return dates;
    }
    public ArrayList<String> getAllIncomeDates(String user){
        ArrayList<String> dates =new ArrayList<>();
        String query = "SELECT DISTINCT DATE FROM INCOME_TABLE\n" +
                "WHERE USERNAME = '"+user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                dates.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        return dates;
    }
    public ArrayList<Integer> getExpensesIDByDate(String user,String date){
        ArrayList<Integer> amounts =new ArrayList<>();
        String query ="SELECT EXPENSE_ID FROM EXPENSE_TABLE\n" +
                "WHERE USERNAME = '"+user+"'  AND DATE = '"+date+"'\n" +
                "ORDER BY AMOUNT desc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                amounts.add(cursor.getInt(0));
            }while(cursor.moveToNext());
        }
        return amounts;
    }
    public ArrayList<Integer> getIncomeIDByDate(String user,String date){
        ArrayList<Integer> amounts =new ArrayList<>();
        String query =" SELECT INCOME_ID FROM INCOME_TABLE\n" +
                    "WHERE USERNAME = '"+user+"' AND DATE = '"+date+"'\n" +
                    "ORDER BY AMOUNT desc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                amounts.add(cursor.getInt(0));
            }while(cursor.moveToNext());
        }
        return amounts;
    }
    public String getIncomeAmountById(String user, int id){
        String amount = "";
        String query =" SELECT AMOUNT FROM INCOME_TABLE\n" +
                "WHERE INCOME_ID = '"+id+"' AND USERNAME = '"+user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            amount = Double.toString(cursor.getDouble(0));
        }
        return amount;
    }
    public String getExpensesAmountById(String user, int id){
        String amount = "";
        String query =" SELECT AMOUNT FROM EXPENSE_TABLE\n" +
                "WHERE EXPENSE_ID = '"+id+"' AND USERNAME = '"+user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            amount = Double.toString(cursor.getDouble(0));
        }
        return amount;
    }
    public Cursor getExpensesInfoByID(String user,int id){
        String query =" SELECT * FROM EXPENSE_TABLE\n" +
                "WHERE EXPENSE_ID = '"+id+"' AND USERNAME = '"+user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor;
    }
    public Cursor getIncomeInfoByID(String user,int id){
        String query =" SELECT * FROM INCOME_TABLE\n" +
                "WHERE INCOME_ID = '"+id+"' AND USERNAME = '"+user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor;
    }

    public boolean createSavings(double amount, String user, String date, String notes) {
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SDATE, date);
        cv.put(SNOTES, notes);
        cv.put(USERNAME, user);
        cv.put(SAMOUNT, amount);
        long insert = db.insert(SAVINGS_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;

        return check;
    }
    public double getTotalSavings(String user) {
        double totals = 0.0;
        String queryString = "SELECT * FROM " + SAVINGS_TABLE + " WHERE USERNAME='" + user + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                double amount = cursor.getDouble(4);
                totals += amount;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return totals;
    }

    public boolean createECategory(String user, String category){
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ECATEGORY, "Food");
        cv.put(USERNAME, user);
        cv.put(ECATEGORY, category);
        long insert = db.insert(ECATEGORY_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;

        return check;
    }


}
