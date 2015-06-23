package com.codyhowarth.simplytodo;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.codyhowarth.simplytodo.TodoList;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public static TodoList tdList = new TodoList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        tdList.loadList(this);


        // Set the 'up' button on the action bar
        //getActionBar().setDisplayHomeAsUpEnabled(true);


        // Initialize the array adapter
//        ArrayAdapter<Model> adapter = new InteractiveArrayAdapter(this,
//                getModel());
//        setListAdapter(adapter);

        ListView lv = (ListView) findViewById(R.id.todolistview);
        lv.setAdapter(new InteractiveArrayAdapter(this, getModel()));
    }

    @Override
    protected void onDestroy() {
        tdList.saveList(this);
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



    private List<Model> getModel() {
        List<Model> list = new ArrayList<Model>();

        for (TodoItem item : tdList.tdList) {
            Model tempModel = new Model(item.getText());
            list.add(tempModel);
        }

        // Initially select one of the items
        //list.get(1).setSelected(true);
        return list;
    }
    // Override the back button so it doesn't go back to adding a todoitem
    @Override
    public void onBackPressed() {
    }
}
