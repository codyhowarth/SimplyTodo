package com.codyhowarth.simplytodo;

/**
 * Created by cody on 6/18/15.
 *
 * An ArrayAdapter class to handle populating the listview with backend data
 */
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class InteractiveArrayAdapter extends ArrayAdapter<Model> {

    private final List<Model> list;
    private final Activity context;

    public InteractiveArrayAdapter(Activity context, List<Model> list) {
        super(context, R.layout.todoitem_layout, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        String dateToCheck = list.get(position).getDate().toString();

        if (dateToCheck.equals("")) {
            return 0; // matches R.layout.todoitem_notdate_layout
        }
        else {
            return 1; // matches R.layout.todoitem_layout
        }
    }

    static class ViewHolder {
        protected TextView text, date;
        protected CheckBox checkbox;
    }

    static class ViewHolder2 {
        protected TextView text;
        protected CheckBox checkbox;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        // Resets the view to null
        View view = null;

        int itemtype = getItemViewType(position);

        // Avoids initializing the view every time (helps with smooth-scrolling)
        if (convertView == null) {

            if (itemtype == 1) {

                // Instantiates a new LayoutInflater object using the current context
                LayoutInflater inflator = context.getLayoutInflater();

                // Sets the view to the todoitem_layout
                view = inflator.inflate(R.layout.todoitem_layout, null);

                // Instantiates a new viewHolder
                final ViewHolder viewHolder = new ViewHolder();

                // Sets up the textview, checkbox elements of the viewHolder (variables defined in static class)
                viewHolder.text = (TextView) view.findViewById(R.id.todotextview);
                viewHolder.date = (TextView) view.findViewById(R.id.datetextview);
                viewHolder.checkbox = (CheckBox) view.findViewById(R.id.todocheckbox);
                viewHolder.checkbox
                        .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton buttonView,
                                                         boolean isChecked) {
                                Model element = (Model) viewHolder.checkbox
                                        .getTag();
                                element.setSelected(buttonView.isChecked());

                                if (isChecked) {
                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                    String formattedDate = df.format(c.getTime());
                                    MainActivity.tdList.tdList.get(position).setDate(formattedDate);
                                }

                            }
                        });
                view.setTag(viewHolder);
                viewHolder.checkbox.setTag(list.get(position));

            } else {

                // Instantiates a new LayoutInflater object using the current context
                LayoutInflater inflator = context.getLayoutInflater();

                // Sets the view to the todoitem_layout
                view = inflator.inflate(R.layout.todoitem_nodate_layout, null);

                // Instantiates a new viewHolder
                final ViewHolder viewHolder = new ViewHolder();

                // Sets up the textview, checkbox elements of the viewHolder (variables defined in static class)
                viewHolder.text = (TextView) view.findViewById(R.id.todotextview);
                viewHolder.checkbox = (CheckBox) view.findViewById(R.id.todocheckbox);
                viewHolder.checkbox
                        .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton buttonView,
                                                         boolean isChecked) {
                                Model element = (Model) viewHolder.checkbox
                                        .getTag();
                                element.setSelected(buttonView.isChecked());

                                if (isChecked) {
                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                    String formattedDate = df.format(c.getTime());
                                    MainActivity.tdList.tdList.get(position).setDate(formattedDate);
                                }

                            }
                        });
                view.setTag(viewHolder);
                viewHolder.checkbox.setTag(list.get(position));
            }


        } else { // The view has already been set (and saved in convertView

            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));

        }


        // Sets the items in the holder to be the actual values
        ViewHolder holder = (ViewHolder) view.getTag();

        if (itemtype == 1) {
            holder.text.setText(list.get(position).getName());
            holder.date.setText(list.get(position).getDate());
        } else {
            holder.text.setText(list.get(position).getName());
        }
       // if (holder.date.getText().equals("")) holder.date.setVisibility(view.GONE);

        holder.checkbox.setChecked(list.get(position).isSelected());

        return view;
    }

}




