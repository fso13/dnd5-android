package com.example.myapplication.model.magic;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClassInfo implements Serializable {
    private Title title;
    private List<String> spells;

    public ClassInfo() {
    }

    public ClassInfo(Title title, List<String> spells) {
        this.title = title;
        this.spells = new StringList(spells);
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public List<String> getSpells() {
        return spells;
    }

    public void setSpells(List<String> spells) {
        this.spells = new StringList(spells);
    }

    class StringList extends ArrayList<String> {

        public StringList(@NonNull Collection<? extends String> c) {
            super(c);
        }

        @Override
        public boolean contains(Object o) {
            String paramStr = (String) o;
            for (String s : this) {
                if (paramStr.equalsIgnoreCase(s)) return true;
            }
            return false;
        }
    }
}
