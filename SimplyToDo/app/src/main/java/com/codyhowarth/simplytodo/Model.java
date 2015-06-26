package com.codyhowarth.simplytodo;


public class Model {

    private String name, date;
    private boolean selected;
    private boolean toDelete;

    public Model(String name, String date) {
        if (date.equals("null")) {
            this.date = "";
        } else {
            this.date = date;
        }

        this.name = name;
        selected = false;
        toDelete = false;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete){
        this.toDelete = toDelete;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}