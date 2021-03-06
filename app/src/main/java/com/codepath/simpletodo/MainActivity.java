package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.simpletodo.db.ToDoItem;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<ToDoItem> todoItems;
    ToDoItemsAdapter itemsAdapter;
    ListView lvItems;
    EditText etEditText;
    int itemPosition;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Construct the data source
        todoItems = new ArrayList<>();
        // Create the adapter to convert the array to views
        itemsAdapter = new ToDoItemsAdapter(this, todoItems);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        // Attach the adapter to a ListView
        lvItems.setAdapter(itemsAdapter);
        etEditText = (EditText)findViewById(R.id.etEditText);
        // To Delete
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ToDoItem itemToDelete = todoItems.get(position);
                todoItems.remove(position);
                itemsAdapter.notifyDataSetChanged();

                deleteItem(itemToDelete);
                return true;
            }
        });
        // To Edit
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // first parameter is the context, second is the class of the activity to launch
                Intent goToEdit = new Intent(MainActivity.this, EditItemActivity.class);
                //Get the value of the item you clicked
                goToEdit.putExtra("to do item", todoItems.get(position).getItem());
                goToEdit.putExtra("position", position);
                startActivityForResult(goToEdit, REQUEST_CODE); // brings up the second activity
            }
        });
    }

    @Override //update item value after edit
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract value from result extras
            String editedItemInString = intent.getStringExtra("edited");
            itemPosition = intent.getExtras().getInt("item_position");

            ToDoItem editedItem = todoItems.get(itemPosition);
            editedItem.setItem(editedItemInString);
            todoItems.set(itemPosition, editedItem);
            itemsAdapter.notifyDataSetChanged();
            writeItems(editedItem);

            Toast.makeText(this, "Task Edited", Toast.LENGTH_SHORT).show();
        }
    }

    public void populateArrayItems() {
        readItems();
    }

    public void onAddItem(View view) {
        String itemVal = etEditText.getText().toString();
        ToDoItem itemData = new ToDoItem();
        itemData.setItem(itemVal);
        writeItems(itemData);
        itemsAdapter.add(itemData);
        etEditText.setText("");
    }

    private void readItems() {
        List<ToDoItem> todoItemsFromDb = SQLite.select().from(ToDoItem.class).queryList();
        try {
            for (ToDoItem val : todoItemsFromDb) {
                itemsAdapter.add(val);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void writeItems(ToDoItem itemData) {
        try {
            itemData.async().save();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void deleteItem(ToDoItem itemData) {
        itemData.delete();
    }
}
