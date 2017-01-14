package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.simpletodo.db.ToDoItem;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> todoItems;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    EditText etEditText;
    int itemPosition;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView)findViewById(R.id.lvItems);
        //pass adapter to the list view
        lvItems.setAdapter(itemsAdapter);
        etEditText = (EditText)findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // first parameter is the context, second is the class of the activity to launch
                Intent goToEdit = new Intent(MainActivity.this, EditItemActivity.class);
                //Get the value of the item you clicked
                goToEdit.putExtra("to do item", todoItems.get(position));
                goToEdit.putExtra("position", position);
                startActivityForResult(goToEdit, REQUEST_CODE); // brings up the second activity
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String edited_item = intent.getStringExtra("edited");
            itemPosition = intent.getExtras().getInt("item_position");
            Toast.makeText(this, "Task Edited", Toast.LENGTH_SHORT).show();
            todoItems.set(itemPosition, edited_item);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    public void populateArrayItems() {
        todoItems = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    public void onAddItem(View view) {
        itemsAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
    }

    private void readItems() {
        List<ToDoItem> todoItems = SQLite.select().from(ToDoItem.class).queryList();
    }

    private void writeItems() {
        ToDoItem todoItems = new ToDoItem();
        todoItems.async().save();
    }
}
