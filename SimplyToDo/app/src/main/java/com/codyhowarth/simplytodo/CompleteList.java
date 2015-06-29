package com.codyhowarth.simplytodo;

import android.app.Activity;
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, AddToDo.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
