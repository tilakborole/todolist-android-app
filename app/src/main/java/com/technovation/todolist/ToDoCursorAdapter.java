package com.technovation.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


/**
 * Created by Dell on 11/14/2018.
 */

public class ToDoCursorAdapter extends CursorAdapter {

    public ToDoCursorAdapter(Context context, Cursor cursor, int flag){
        super(context, cursor, flag);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkbox);
        TextView taskView = (TextView)view.findViewById(R.id.taskview);
        TextView timeView = (TextView)view.findViewById(R.id.level);

        int taskColumnIndex = cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.TASKS_COLOUMN);
        int timeColumnIndex = cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.TIME_COLOUMN);
        int checkedColoumnIndex = cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.TASK_STATUS_COLOUMN);

        final String taskName = cursor.getString(taskColumnIndex);
        final String  time = cursor.getString(timeColumnIndex);
        final String state = cursor.getString(checkedColoumnIndex);

        if(state == "1")
            checkBox.setChecked(true);
        else
            checkBox.setChecked(false);

        taskView.setText(taskName);
        timeView.setText("" + time);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String state;
                if(checkBox.isChecked()){
                    state = "1";
                }
                else
                    state = "0";

                ContentValues values = new ContentValues();
                values.put(TaskContract.TaskEntry.TASKS_COLOUMN, taskName);
                values.put(TaskContract.TaskEntry.TIME_COLOUMN, time);
                values.put(TaskContract.TaskEntry.TASK_STATUS_COLOUMN, state);
                String[] args = new String[]{
                        taskName
                };
                TaskDbHelper mDbHelper = new TaskDbHelper(context);
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                db.update(TaskContract.TaskEntry.TABLE_NAME, values, TaskContract.TaskEntry.TABLE_NAME + "=? ",args);

            }
        });
    }
}
