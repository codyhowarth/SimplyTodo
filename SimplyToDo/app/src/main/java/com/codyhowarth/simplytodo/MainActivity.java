package com.codyhowarth.simplytodo;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.AbsListView;
import android.widget.AdapterView;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public static TodoList tdList = new TodoList();
    private ListView listView;
    private ActionMode mActionMode;
    private List<Model> model_list = new ArrayList<Model>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        tdList.loadList(this);


        listView = (ListView) findViewById(R.id.todolistview);
        listView.setAdapter(new InteractiveArrayAdapter(this, getModel(model_list)));

//        final ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
//
//            // Called when the action mode is created; startActionMode() was called
//            @Override
//            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//                // Inflate a menu resource providing context menu items
//                MenuInflater inflater = mode.getMenuInflater();
//                inflater.inflate(R.menu.context_main, menu);
//                return true;
//            }
//
//            // Called each time the action mode is shown. Always called after onCreateActionMode, but
//            // may be called multiple times if the mode is invalidated.
//            @Override
//            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//                return false; // Return false if nothing is done
//            }
//
//            // Called when the user selects a contextual menu item
//            @Override
//            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.menu_delete:
//                        System.out.println("DELETE ITEM METHOD CALL");
//                        mode.finish(); // Action picked, so close the CAB
//                        return true;
//                    default:
//                        return false;
//                }
//            }
//
//            // Called when the user exits the action mode
//            @Override
//            public void onDestroyActionMode(ActionMode mode) {
//                mActionMode = null;
//            }
//        };

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                // Here you can do something when items are selected/de-selected,
                // such as update the title in the CAB
                model_list.get(position).setToDelete(checked);


                System.out.println("This is the int position value passed in : " + position + "\n");
                System.out.println("This is the item of tdlist at that postion" + tdList.tdList.get(position).toString() + "\n");


                System.out.println("This is the long id value: " + id + "\n");
                System.out.println("This is the boolean checked value: " + checked + "\n");
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

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                System.out.println("Click WORKED!");
//
////                if (mActionMode != null) {
////                    return false;
////                }
//
//                // Start the CAB using the ActionMode.Callback defined above
//                mActionMode = MainActivity.this.startActionMode(mActionModeCallback);
//                view.setSelected(true);
//                //return true;
//
//            }
//        });
    }

    // Method called from the CAB to delete todolist items
    private void deleteSelectedItems() {

        System.out.println("deleteSelectedItems method has been called");

        List<Model> model_list_removals = new ArrayList<Model>();
        List<TodoItem> tdlist_removals = new ArrayList<TodoItem>();

        int i = 0;

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
    public void onBackPressed() {
    }


}
