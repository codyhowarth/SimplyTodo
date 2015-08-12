package com.codyhowarth.simplytodo;

import android.content.Context;
import android.util.Log;

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

    public String saveFilename;
    public ArrayList<TodoItem> tdList;

    public TodoList(String savefilename){
        this.saveFilename = savefilename;
        tdList = new ArrayList<>();
    }

    public void addItem(TodoItem item_to_add) {
        tdList.add(item_to_add);
    }

    public void deleteItem(TodoItem item_to_delete) {
        tdList.remove(item_to_delete);
            }

    /**
     * Clears the todolist
     */
    public void clearList(){ tdList.clear();}

    public void remove(int index) {
        tdList.remove(index);
    }

    @Override
    public String toString() {
        String todolistStr = "";

        // Iterate through and populate the string
        for (TodoItem item : tdList) {
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
            outputStream = context.getApplicationContext().openFileOutput(saveFilename, context.MODE_PRIVATE);

            outputStream.write(this.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * Loads a TodoItem object as a String to a file
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

            // #1 Split into items
            String[] items = todolistStr.split("\\|"); // item array

            // #2 Split each item into text, date. Create a new todoitem using the text and date
            for (String item : items ) {
                System.out.println(item);
                String[] text_and_date = item.split("\\~");
                TodoItem newItem = new TodoItem(text_and_date[0], text_and_date[1]);

                System.out.println("This is the text_and_date[1] value: " + text_and_date[1]);

                System.out.println("This is the newitem getDate()" + newItem.getDate());

                // #3 Append todoitem to todolist object.
                if (!(tdList.contains(newItem))) {
                    tdList.add(newItem);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
