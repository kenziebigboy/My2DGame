package org.tilemanger.panels;

public class ComboItem {

    private int id;
    private String name;

    public ComboItem(int id, String label) {
        this.id = id;
        this.name = label;
    }

    public int getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }
}
