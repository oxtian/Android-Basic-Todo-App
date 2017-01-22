package com.codepath.simpletodo.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.UUID;

@Table(database = ToDoListDatabase.class)
public class ToDoItem extends BaseModel {
    @Column
    @PrimaryKey
    public String id;

    @Column
    public String item;

    @Column
    public String priority;

    @Column
    public String due_date;

    public ToDoItem() {
    }

    public ToDoItem(String item, String priority, String due_date) {
        this.item = item;
        this.priority = priority;
        this.due_date = due_date;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDueDate() {
        return due_date;
    }

    public void setDueDate(String due_date) {
        this.due_date = due_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(id == null || id.length() == 0) {
            UUID uuid = UUID.randomUUID();
            this.id = uuid.toString();
        } else {
            this.id = id;
        }
    }

    @Override
    public String toString() {
        return item + priority + due_date;
    }
}