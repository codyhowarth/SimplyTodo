package com.codyhowarth.simplytodo;

/**
 * Created by cody on 6/23/15.
 *
 * The class that represents an individual todolist item
 */
public class TodoItem {

    private String text, date;

    public TodoItem (String text, String date){

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
    public boolean equals(Object other){
        boolean result = false;

        if (other instanceof TodoItem) {
            TodoItem that = (TodoItem) other;
            result = (this.getText().equals(that.getText()) && this.getDate().equals(that.getDate()));
        }

        return result;

    }

    @Override
    public String toString() { return text + ":" + date; }
}