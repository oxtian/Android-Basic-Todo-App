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
    String id;

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
        return item;
    }
}