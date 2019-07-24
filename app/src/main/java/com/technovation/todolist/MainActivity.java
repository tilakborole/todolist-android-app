package com.technovation.todolist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText taskInput;
    private Spinner levelInput;
    private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskInput = (EditText)findViewById(R.id.taskinput);
        levelInput = (Spinner)findViewById(R.id.levelselection);
        Button button = (Button)findViewById(R.id.submit);
        listview = (ListView)findViewById(R.id.listview);

        final TextView tasktodo = (TextView)findViewById(R.id.todotasklist);
        final TextView taskdone = (TextView)findViewById(R.id.taskdonelist);

        Cursor c = displayDatabaseInfo(0);  //by default
        ToDoCursorAdapter adapter = new ToDoCursorAdapter(this, c, 1);
        listview.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = taskInput.getText().toString();
                if(task.equals(""))
                    return;
                insertTask();
                taskInput.setText("");
            }
        });

        tasktodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = displayDatabaseInfo(0);  //by default
                ToDoCursorAdapter adapter = new ToDoCursorAdapter(MainActivity.this, c, 1);
                listview.setAdapter(adapter);
                tasktodo.setBackgroundColor(Color.parseColor("#FF0000"));
                taskdone.setBackgroundColor(Color.parseColor("#00FF00"));
            }
        });

        taskdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = displayDatabaseInfo(1);  //by default
                ToDoCursorAdapter adapter = new ToDoCursorAdapter(MainActivity.this, c, 1);
                listview.setAdapter(adapter);
                tasktodo.setBackgroundColor(Color.parseColor("#00FF00"));
                taskdone.setBackgroundColor(Color.parseColor("#FF0000"));
            }
        });
    }

    void insertTask(){
        String task = taskInput.getText().toString();
        String level = levelInput.getSelectedItem().toString();
        String state = "0";

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.TASKS_COLOUMN, task);
        values.put(TaskContract.TaskEntry.TIME_COLOUMN, level);
        values.put(TaskContract.TaskEntry.TASK_STATUS_COLOUMN, state);

        TaskDbHelper mDbHelper = new TaskDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.insert(TaskContract.TaskEntry.TABLE_NAME,null, values);
    }

    Cursor displayDatabaseInfo(int state) {
        String s = "" + state;
        TaskDbHelper dbhelper = new TaskDbHelper(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String[] projection = {
                TaskContract.TaskEntry.ID,
                TaskContract.TaskEntry.TASKS_COLOUMN,
                TaskContract.TaskEntry.TIME_COLOUMN,
                TaskContract.TaskEntry.TASK_STATUS_COLOUMN
        };

        String selection = TaskContract.TaskEntry.TASK_STATUS_COLOUMN + " =? ";
        String[] selectionArgs = new String[]{
                s
        };

        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE_NAME,projection, selection,selectionArgs,null,null,null);
        return cursor;
    }

}


