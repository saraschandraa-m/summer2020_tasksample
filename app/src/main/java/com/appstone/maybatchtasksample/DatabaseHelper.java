package com.appstone.maybatchtasksample;

public class DatabaseHelper {

    public static String TABLE_NAME = "CHECKLIST";
    public static String COL_ID = "ID";
    public static String COL_TITLE = "TITLE";
    public static String COL_ITEM = "ITEMS";


    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY," + COL_TITLE + " TEXT," + COL_ITEM + " TEXT)";

}
