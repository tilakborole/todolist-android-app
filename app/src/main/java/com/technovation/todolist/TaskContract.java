package com.technovation.todolist;

import android.provider.BaseColumns;

/**
 * Created by Dell on 11/14/2018.
 */

public class TaskContract {
    private TaskContract() {}
    public static final class TaskEntry implements BaseColumns{
        public static final String TABLE_NAME = "tasks";
        public static final String ID = BaseColumns._ID;
        public static final String TASKS_COLOUMN = "tasks";
        public static final String TIME_COLOUMN = "time";
        public static final String TASK_STATUS_COLOUMN = "status";  //0 if not done otherwise 1
    }
}
