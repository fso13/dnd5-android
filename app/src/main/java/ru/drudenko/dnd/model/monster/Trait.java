package ru.drudenko.dnd.model.monster;

import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.util.List;

public class Trait implements Serializable {
    private String name;
    @JsonAdapter(AlwaysListTypeAdapterFactory.class)
    private List<String> text;
    @JsonAdapter(AlwaysListTypeAdapterFactory.class)
    private List<String> attack;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public List<String> getAttack() {
        return attack;
    }

    public void setAttack(List<String> attack) {
        this.attack = attack;
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        if (name != null) {
            r.append(name).append('\n');
        }

        if (text != null) {
            for (String a : text) {
                r.append(a).append('\n');
            }
        }

        if (attack != null) {
            for (String a : attack) {
                r.append(a).append('\n');
            }
        }

        r.append('\n');

        return r.toString();
    }
}
