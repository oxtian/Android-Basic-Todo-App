package com.codepath.simpletodo.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = ToDoListDatabase.class)
public class ToDoItem extends BaseModel {
    @Column
    @PrimaryKey
    int id;

    @Column
    String item;

    public ToDoItem() {
    }

    public ToDoItem(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ToDoItem{" +
                "item='" + item + '\'' +
                '}';
    }
}