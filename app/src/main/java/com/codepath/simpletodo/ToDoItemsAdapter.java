package com.codepath.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.simpletodo.db.ToDoItem;

import java.util.ArrayList;

import static com.codepath.simpletodo.R.id.tvItem;

public class ToDoItemsAdapter extends ArrayAdapter<ToDoItem> {
    public ToDoItemsAdapter(Context context, ArrayList<ToDoItem> todoItems) {
        super(context, 0, todoItems);
    }

    static class ViewHolder {
        TextView itemView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ToDoItem todoItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.to_do_item, parent, false);
            holder = new ViewHolder();
            holder.itemView = (TextView) convertView.findViewById(tvItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemView.setText(todoItem.item);
        // Return the completed view to render on screen
        return convertView;
    }

}