package com.codepath.simpletodo.db;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = ToDoListDatabase.NAME, version = ToDoListDatabase.VERSION)
public class ToDoListDatabase {
    public static final String NAME = "ToDoList";

    public static final int VERSION = 1;
}
