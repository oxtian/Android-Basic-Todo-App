package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    EditText etEditItem;
    String to_do_item;
    String item_priority;
    String item_due_date;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etEditItem = (EditText) findViewById(R.id.etEditItem);
        to_do_item = getIntent().getStringExtra("to do item");
        item_priority = getIntent().getStringExtra("priority");
        item_due_date = getIntent().getStringExtra("due date");
        position = getIntent().getExtras().getInt("position");
        etEditItem.append(to_do_item);
    }

    public void onEditItem(View view) {
        Intent saveEdit = new Intent();
        saveEdit.putExtra("edited", etEditItem.getText().toString());
        saveEdit.putExtra("item_position", position);
        setResult(RESULT_OK, saveEdit);
        finish();
    }
}
