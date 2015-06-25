package com.codyhowarth.simplytodo;

/**
 * Created by cody on 6/18/15.
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

import java.util.List;

public class InteractiveArrayAdapter extends ArrayAdapter<Model> {

    private final List<Model> list;
    private final Activity context;

    public InteractiveArrayAdapter(Activity context, List<Model> list) {
        super(context, R.layout.todoitem_layout, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected TextView text;
        protected CheckBox checkbox;
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
            view = inflator.inflate(R.layout.todoitem_layout, null);

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

                        }
                    });
            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(list.get(position));



        } else { // The view has already been set (and saved in convertView

            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
        }


        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getName());
        holder.checkbox.setChecked(list.get(position).isSelected());

        return view;
    }

}




