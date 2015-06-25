package com.codyhowarth.simplytodo;


public class Model {

    private String name;
    private boolean selected;
    private boolean toDelete;

    public Model(String name) {
        this.name = name;
        selected = false;
        toDelete = false;
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