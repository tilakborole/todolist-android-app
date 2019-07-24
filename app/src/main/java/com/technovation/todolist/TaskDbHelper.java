package com.technovation.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dell on 11/14/2018.
 */

public class TaskDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "taskstodo.db";
    public TaskDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + TaskContract.TaskEntry.TABLE_NAME + " ("
                + TaskContract.TaskEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TaskContract.TaskEntry.TASKS_COLOUMN + " TEXT, "
                + TaskContract.TaskEntry.TIME_COLOUMN + " TEXT, "
                + TaskContract.TaskEntry.TASK_STATUS_COLOUMN + " TEXT ); ";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //No use of upgrade
    }
}
