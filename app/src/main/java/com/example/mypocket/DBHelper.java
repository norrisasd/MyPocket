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
    private static final String FIRSTNAME = "FULLNAME";
    private static final String LASTNAME = "EMAIL";


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

    private static final String ICATEGORY_TABLE = "ICATEGORY_TABLE";
    private static final String ICAT_ID = "ICATEGORY_ID";
    private static final String ICATEGORY = "CATEGORY";

    private static final String BILLS_TABLE = "BILLS_TABLE";
    private static final String BILLS_ID = "BILLS_ID";
    private static final String COMPANY = "COMPANY";
    private static final String BAMOUNT = "AMOUNT";
    private static final String BDATE = "DUEDATE";

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

        String createICategory = "CREATE TABLE " + ICATEGORY_TABLE + " (" + ICAT_ID + " INTEGER PRIMARY KEY, " + USERNAME + " TEXT, "  + ICATEGORY + " TEXT )";
        db.execSQL(createICategory);

        String createBills= "CREATE TABLE " + BILLS_TABLE + " (" + BILLS_ID + " INTEGER PRIMARY KEY, " + USERNAME + " TEXT, " + BDATE + " TEXT, " + COMPANY + " TEXT, " + BAMOUNT + " REAL )";
        db.execSQL(createBills);

        String createExpenseDefaultCategory = "INSERT INTO ECATEGORY_TABLE(CATEGORY)\n" +
                "VALUES('Bills'), ('Child Care'),('Clothing'),('Education'),('Food')," +
                "('Fun'),('Health Care'), ('Home Supplies')," +
                "('Insurance'),('Pets'),('Personal Care')," +
                "('Transportation'),('Taxes')";
        db.execSQL(createExpenseDefaultCategory);

        String createIncomeDefaultCategory = "INSERT INTO ICATEGORY_TABLE(CATEGORY)\n" +
                "VALUES ('Aid'),('Asset'),('Allowance'),('Business'),('Commissions'),('Inheritance'),('Pension')," +
                "('Remittances'),('Rental'),('Salary'),('Stock Dividends'),('Talent Fee')";
        db.execSQL(createIncomeDefaultCategory);
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
    public String getFullname(String user) {
        String fname = "";
        String queryString = "SELECT FULLNAME FROM USER_TABLE where USERNAME='" + user + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            fname = cursor.getString(0);
        }
        return fname;
    }
    public String getEmail(String user) {
        String mail = "";
        String queryString = "SELECT EMAIL FROM USER_TABLE where USERNAME='" + user + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            mail = cursor.getString(0);
        }
        return mail;
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
                        "WHERE USERNAME = '"+user+"'"+"ORDER BY DATE desc";
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
                "WHERE USERNAME = '"+user+"'"+"ORDER BY DATE desc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                dates.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        return dates;
    }
    public ArrayList<String> getAllSavingsDates(String user){
        ArrayList<String> dates =new ArrayList<>();
        String query = "SELECT DISTINCT DATE FROM SAVINGS_TABLE\n" +
                "WHERE USERNAME = '"+user+"'"+"ORDER BY DATE desc";
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
    public ArrayList<Integer> getSavingsIDByDate(String user,String date){
        ArrayList<Integer> amounts =new ArrayList<>();
        String query =" SELECT SAVINGS_ID FROM SAVINGS_TABLE\n" +
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
    public String getSavingsAmountById(String user, int id){
        String amount = "";
        String query =" SELECT AMOUNT FROM SAVINGS_TABLE\n" +
                "WHERE SAVINGS_ID = '"+id+"' AND USERNAME = '"+user+"'";
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
    public Cursor getSavingsInfoByID(String user,int id){
        String query =" SELECT * FROM SAVINGS_TABLE\n" +
                "WHERE SAVINGS_ID = '"+id+"' AND USERNAME = '"+user+"'";
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
        cv.put(USERNAME, user);
        cv.put(ECATEGORY, category);
        long insert = db.insert(ECATEGORY_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;

        return check;
    }

    public boolean createICategory(String user, String category){
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, user);
        cv.put(ICATEGORY, category);
        long insert = db.insert(ICATEGORY_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;

        return check;
    }

    public void DeleteIncomeTransaction(String user, int id){
        String query = "DELETE FROM INCOME_TABLE WHERE INCOME_ID ='"+id+"' AND USERNAME ='"+user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
    }
    public void DeleteExpenseTransaction(String user, int id){
        String query = "DELETE FROM EXPENSE_TABLE WHERE EXPENSE_ID ='"+id+"' AND USERNAME ='"+user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
    }
    public void DeleteSavingsTransaction(String user, int id){
        String query = "DELETE FROM SAVINGS_TABLE WHERE SAVINGS_ID ='"+id+"' AND USERNAME ='"+user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
    }
    public boolean updateIncomeTransaction(int id,double amount,String category, String date, String note){
        boolean check = true;
        String query ="UPDATE INCOME_TABLE SET DATE ='"+date+"', CATEGORY ='"+category+"',NOTES ='"+note+"',AMOUNT ="+amount+" WHERE INCOME_ID = "+id+"";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        return check;
    }
    public boolean updateExpenseTransaction(int id,double amount,String category, String date, String note){
        boolean check = true;
        String query ="UPDATE EXPENSE_TABLE SET DATE ='"+date+"', CATEGORY ='"+category+"',NOTES ='"+note+"',AMOUNT ="+amount+" WHERE EXPENSE_ID = "+id+"";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        return check;
    }
    public boolean updateSavingsTransaction(int id,double amount, String date, String note){
        boolean check = true;
        String query ="UPDATE SAVINGS_TABLE SET DATE ='"+date+"',NOTES ='"+note+"',AMOUNT ="+amount+" WHERE SAVINGS_ID = "+id+"";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        return check;
    }
//CATEGORIES

    public void deleteExpenseCategory(String user,String category){
        String query = "DELETE FROM ECATEGORY_TABLE WHERE USERNAME ='"+user+"' AND CATEGORY ='"+category+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
    }
    public void deleteIncomeCategory(String user,String category){
        String query = "DELETE FROM ICATEGORY_TABLE WHERE USERNAME ='"+user+"' AND CATEGORY ='"+category+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
    }
    public void updateExpenseCategory(String user,String category,String prevCategory){
        String query = "UPDATE ECATEGORY_TABLE SET CATEGORY ='"+category+"' WHERE USERNAME ='"+user+"' AND CATEGORY ='"+prevCategory+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
    }
    public void updateIncomeCategory(String user,String category,String inccategory){
        String query = "UPDATE ICATEGORY_TABLE SET CATEGORY ='"+category+"' WHERE USERNAME ='"+user+"' AND CATEGORY ='"+inccategory+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
    }
    public ArrayList<String> getUserCategories(String user){//expense spinner
        ArrayList<String> result = new ArrayList<>();
        String query = "SELECT * FROM ECATEGORY_TABLE WHERE USERNAME IS NULL OR USERNAME = '"+user+"'"+"ORDER BY CATEGORY asc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                result.add(cursor.getString(2));
            }while(cursor.moveToNext());
        }
        return result;
    }
    public ArrayList<String> getUserIncomeCategories(String user){//expense spinner
        ArrayList<String> result = new ArrayList<>();
        String query = "SELECT * FROM ICATEGORY_TABLE WHERE USERNAME IS NULL OR USERNAME = '"+user+"'"+"ORDER BY CATEGORY asc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                result.add(cursor.getString(2));
            }while(cursor.moveToNext());
        }
        return result;
    }

    public boolean createBills(double amount, String user, String date, String company) {
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BDATE, date);
        cv.put(COMPANY, company);
        cv.put(USERNAME, user);
        cv.put(BAMOUNT, amount);
        long insert = db.insert(BILLS_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;

        return check;
    }
    public ArrayList<String> getBillsCompany(String user){
        ArrayList<String> result = new ArrayList<>();
        String query = "SELECT COMPANY FROM BILLS_TABLE WHERE USERNAME = '"+user+"'"+"ORDER BY COMPANY asc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                result.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        return result;
    }
    public Cursor getBillsInfoByID(String user,int id){
        String query =" SELECT * FROM BILLS_TABLE\n" +
                "WHERE BILLS_ID = '"+id+"' AND USERNAME = '"+user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor;
    }
    public ArrayList<Integer> getBillsID(String user){
        ArrayList<Integer> id =new ArrayList<>();
        String query =" SELECT BILLS_ID FROM BILLS_TABLE\n" +
                "WHERE USERNAME = '"+user+"'"+"ORDER BY COMPANY asc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                id.add(cursor.getInt(0));
            }while(cursor.moveToNext());
        }
        return id;
    }
    public void deleteBills(String user, int id){
        String query = "DELETE FROM BILLS_TABLE WHERE BILLS_ID ='"+id+"' AND USERNAME ='"+user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
    }
    public boolean updateBillsTransaction(int id,double amount, String date, String company){
        boolean check = true;
        String query ="UPDATE BILLS_TABLE SET DUEDATE ='"+date+"',COMPANY ='"+company+"',AMOUNT ="+amount+" WHERE BILLS_ID = "+id+"";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        return check;
    }


//    public void defaultExpenseCategories() {
//        String query = "INSERT INTO ECATEGORY_TABLE(CATEGORY)\n" +
//                "VALUES('Bills', ('Child Care'),('Clothing'),('Food')," +
//                "('Fun'),('Health Care'), ('Home Supplies')," +
//                "('Insurance'),('Pets'),('Personal Care')," +
//                "('Transportation'),('Taxes'))";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        cursor.moveToFirst();
//    }
//public ArrayList<String> getExpenseCategory(String user){
//    ArrayList<String> expenselist =new ArrayList<>();
//    String query =" SELECT CATEGORY FROM ECATEGORY_TABLE\n"
//            +" WHERE USERNAME='" + user + "'"+"ORDER BY CATEGORY asc";
//    SQLiteDatabase db = this.getReadableDatabase();
//    Cursor cursor = db.rawQuery(query, null);
//    if(cursor.moveToFirst()){
//        do
//            expenselist.add(cursor.getString(0));
//        while(cursor.moveToNext());
//    }
//    return expenselist;
//}
//    public ArrayList<String> getIncomeCategory(String user){
//        ArrayList<String> incomelist =new ArrayList<>();
//        String query =" SELECT CATEGORY FROM ICATEGORY_TABLE\n"
//                +" WHERE USERNAME='" + user + "'"+"ORDER BY CATEGORY asc";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        if(cursor.moveToFirst()){
//            do
//                incomelist.add(cursor.getString(0));
//            while(cursor.moveToNext());
//        }
//        return incomelist;
//    }
}
