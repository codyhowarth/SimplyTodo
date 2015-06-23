package com.codyhowarth.simplytodo;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by cody on 6/22/15.
 *
 * This class handles the saving, loading, searching of the TodoList under-the-hood
 *
 * The todolist itself will be stored as if it were a stack (FILO).
 * A searching method could be written.
 * A sorting method could also be written.
 *
 */

public class TodoList {

    public static final String saveFilename = "ToDoListItems";
    private ArrayList<todoItem> tdList;


    public TodoList(){
        tdList = new ArrayList<todoItem>();
    }


    // Small class to handle individual todolist items
    public class todoItem {

        String text, date;

        public todoItem (String text, String date){

            if (date.equals("")) {
                this.date = null;
            } else {
                this.date = date;
            }

            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "todoItem{" +
                    "text='" + text + '\'' +
                    ", date='" + date + '\'' +
                    '}';
        }
    }


    public void addItem(todoItem item_to_add) {
        tdList.add(item_to_add);
    }

//    public todoItem search(String search_text) {
//        // Turn search_text string into an array and search through the TodoList to see if any
//        // of the terms match items in the todolist
//    }


    @Override
    public String toString() {
        String todolistStr = "";

        // Iterate through and populate the string
        for (todoItem item : tdList) {
            todolistStr = todolistStr + item.toString() + "|";
        }

        return todolistStr;
    }

    /*
    * Saves a TodoItem object as a String to a file
    * */
    public void saveList(Context context) {


        FileOutputStream outputStream;

        try {
            outputStream = context.getApplicationContext().openFileOutput(saveFilename,
                    context.MODE_APPEND | context.MODE_PRIVATE);

            outputStream.write(this.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * Saves a TodoItem object as a String to a file
    * */
    public void loadList(Context context) {
        // Load TodoItem object from file

        FileInputStream inputStream;
        String todolistStr;


        try {
            inputStream = context.getApplicationContext().openFileInput(saveFilename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder out = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                out.append(line);
            }

            todolistStr = out.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO parse the loaded string into todoitems and populate the todolist object


    }
}
