package com.codyhowarth.simplytodo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    public static TodoList tdList = new TodoList();
    public static TodoList complete_list = new TodoList();
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private ActionMode mActionMode;
    private List<Model> model_list = new ArrayList<>();

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        tdList.loadList(this);


        listView = (ListView) findViewById(R.id.todolistview);
        arrayAdapter = new InteractiveArrayAdapter(this, getModel(model_list));
        listView.setAdapter(arrayAdapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                // Here you can do something when items are selected/de-selected,
                // such as update the title in the CAB
                model_list.get(position).setToDelete(checked);
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // Respond to clicks on the actions in the CAB
                switch (item.getItemId()) {
                    case R.id.menu_delete:
                        deleteSelectedItems();
                        mode.finish(); // Action picked, so close the CAB
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate the menu for the CAB
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_main, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Here you can make any necessary updates to the activity when
                // the CAB is removed. By default, selected items are deselected/unchecked.
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // Here you can perform updates to the CAB due to
                // an invalidate() request
                return false;
            }
        });

    }

    protected void onResume() {
        super.onResume();
        arrayAdapter.notifyDataSetChanged();
    }

    // Method called from the CAB to delete todolist items
    private void deleteSelectedItems() {

        // Make copies of the todolist and the modellist so that they can be modified accordingly
        List<Model> model_list_removals = new ArrayList<>();
        List<TodoItem> tdlist_removals = new ArrayList<>();

        int i = 0;

        // Iterate through and create removal lists based on user's selection(s)
        for (Model item: model_list) {
            if (model_list.get(i).isToDelete()) {
                model_list_removals.add(item);
                tdlist_removals.add(tdList.tdList.get(i));
            }
            i++;
        }

        model_list.removeAll(model_list_removals);
        tdList.tdList.removeAll(tdlist_removals);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tdList.saveList(this); // Save list here if something happens.
    }

    // Checks if any boxes are checked, if yes; remove those items from the model current
    // tdlist but not before saving them completed_list
    protected void onPause() {
        super.onPause();
        // Make copies of the todolist and the modellist so that they can be modified accordingly
        List<Model> model_list_removals = new ArrayList<>();
        ArrayList<TodoItem> tdlist_removals = new ArrayList<>();

        int i = 0;

        // Iterate through and create removal lists based on user's selection(s)
        for (Model item: model_list) {
            if (model_list.get(i).isSelected()) {
                model_list_removals.add(item);
                tdlist_removals.add(tdList.tdList.get(i));
            }
            i++;
        }

        complete_list.tdList = tdlist_removals;
        model_list.removeAll(model_list_removals);
        tdList.tdList.removeAll(tdlist_removals);

        tdList.saveList(this); // Save list here if something happens.
    }

    // Checks if any boxes are checked, if yes; remove those items from the model current
    // tdlist but not before saving them completed_list
//    protected void onStop() {
//        super.onStop();
//        // Make copies of the todolist and the modellist so that they can be modified accordingly
//        List<Model> model_list_removals = new ArrayList<Model>();
//        ArrayList<TodoItem> tdlist_removals = new ArrayList<TodoItem>();
//
//        int i = 0;
//
//        // Iterate through and create removal lists based on user's selection(s)
//        for (Model item: model_list) {
//            if (model_list.get(i).isSelected()) {
//                model_list_removals.add(item);
//                tdlist_removals.add(tdList.tdList.get(i));
//            }
//            i++;
//        }
//
//        complete_list.tdList = tdlist_removals;
//        model_list.removeAll(model_list_removals);
//        tdList.tdList.removeAll(tdlist_removals);
//
//        tdList.saveList(this); // Save list here if something happens.
//    }

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


        for (TodoItem item : tdList.tdList) {
            Model tempModel = new Model(item.getText());
            model_list.add(tempModel);
        }

        // Initially select one of the items
        //list.get(1).setSelected(true);
        return model_list;
    }

    // Override the back button so it doesn't go back to adding a todoitem
    @Override
    public void onBackPressed() {}


}
