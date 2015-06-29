package com.codyhowarth.simplytodo;

/**
 * Created by cody on 6/18/15.
 *
 * An ArrayAdapter class to handle populating the listview with backend data
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class CompletedArrayAdapter extends ArrayAdapter<Model> {

    private final List<Model> list;
    private final Activity context;

    public CompletedArrayAdapter(Activity context, List<Model> list) {
        super(context, R.layout.completeitem_layout, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected TextView text, date;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // Resets the view to null
        View view = null;

        // Avoids initializing the view every time (helps with smooth-scrolling)
        if (convertView == null) {

            // Instantiates a new LayoutInflater object using the current context
            LayoutInflater inflator = context.getLayoutInflater();

            // Sets the view to the todoitem_layout
            view = inflator.inflate(R.layout.completeitem_layout, null);

            // Instantiates a new viewHolder
            final ViewHolder viewHolder = new ViewHolder();

            // Sets up the textview, checkbox elements of the viewHolder (variables defined in static class)
            viewHolder.text = (TextView) view.findViewById(R.id.todoCompletetextview);
            viewHolder.date = (TextView) view.findViewById(R.id.dateCompletetextview);

            view.setTag(viewHolder);



        } else { // The view has already been set (and saved in convertView

            view = convertView;
        }


        // Sets the items in the holder to be the actual values
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getName());
        holder.date.setText(list.get(position).getDate());

        return view;
    }

}




