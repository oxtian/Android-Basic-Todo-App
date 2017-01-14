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
    ArrayList<ToDoItem> todoItems;
    ArrayAdapter<ToDoItem> itemsAdapter;
    ListView lvItems;
    EditText etEditText;
    int itemPosition;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        todoItems = new ArrayList<ToDoItem>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todoItems);

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
//                writeItems();
                return true;
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
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
        for (ToDoItem val : todoItemsFromDb) {
            itemsAdapter.add(val);
        }
    }

    private void writeItems(ToDoItem itemData) {
        itemData.async().save();
    }
}
