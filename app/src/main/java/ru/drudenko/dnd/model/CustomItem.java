package ru.drudenko.dnd.model;

import java.io.Serializable;

public class CustomItem implements Serializable {
    private String name;
    private String text;

    public CustomItem(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
