package com.codyhowarth.simplytodo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class CompleteList extends Activity {

    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private ActionMode mActionMode;
    private List<Model> complete_model_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_list);


        listView = (ListView) findViewById(R.id.completedlistView);
        arrayAdapter = new CompletedArrayAdapter(this, getModel(complete_model_list));
        listView.setAdapter(arrayAdapter);
    }

    protected void onResume() {
        super.onResume();
        arrayAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_complete_list, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_clear:
                generateClearAlert();
                return true;
//            case R.id.action_settings:
//                //openSettings();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearItems(){
        MainActivity.complete_list.clearList();
        MainActivity.complete_list.saveList(this);
        this.recreate();
    }

    private void generateClearAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                clearItems();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Set other dialog properties
        builder.setIcon(R.drawable.ic_delete_black_48dp);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(R.string.dialog_message);



        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    // Returns a model built from the todolist object
    private List<Model> getModel(List<Model> model_list) {


        for (TodoItem item : MainActivity.complete_list.tdList) {
            Model tempModel = new Model(item.getText(), item.getDate());
            model_list.add(tempModel);
        }

        // Initially select one of the items
        //list.get(1).setSelected(true);
        return model_list;
    }

}
