package com.codepath.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.simpletodo.db.ToDoItem;

import java.util.ArrayList;

public class ToDoItemsAdapter extends ArrayAdapter<ToDoItem> {
    public ToDoItemsAdapter(Context context, ArrayList<ToDoItem> todoItems) {
        super(context, 0, todoItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ToDoItem todoItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.to_do_item, parent, false);
        }

//        if (todoItem != null) {
//            // Lookup view for data population
//            TextView tvItem = (TextView) convertView.findViewById(R.id.tvItem);
//            if (tvItem != null) {
//                //Populate the data into the template view using the data object
//                tvItem.setText(todoItem.item);
//            }
//        }

        TextView tvItem = (TextView) convertView.findViewById(R.id.tvItem);
        tvItem.setText(todoItem.item);

        // Return the completed view to render on screen
        return convertView;
    }

}