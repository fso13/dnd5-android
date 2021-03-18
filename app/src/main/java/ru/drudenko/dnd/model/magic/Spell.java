package ru.drudenko.dnd.model.magic;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Spell implements Serializable, Cloneable {

    private final String name;
    private final boolean ritual;
    private final String school;
    private final String level;
    private final String castingTime;
    private final String range;
    private final String components;
    private final String duration;
    private final String text;
    private final String source;
    private List<String> classes;

    private boolean isFavorite;

    public Spell(String name, String ritual, String school, String level, String castingTime, String range, String components, String duration, String text, String source, List<String> classes, boolean isFavorite) {
        this.name = name;
        this.ritual = Boolean.getBoolean(ritual);
        this.school = school;
        this.level = level;
        this.castingTime = castingTime;
        this.range = range;
        this.components = components;
        this.duration = duration;
        this.text = text;
        this.source = source;
        this.classes = classes;
        this.isFavorite = isFavorite;
    }

    public String getName() {
        return name;
    }

    public boolean getRitual() {
        return ritual;
    }

    public List<String> getClasses() {
        if (classes == null) {
            classes = new ArrayList<>();
        }
        return classes;
    }

    public String getSchool() {
        return school;
    }

    public String getLevel() {
        return level;
    }

    public String getCastingTime() {
        return castingTime;
    }

    public String getRange() {
        return range;
    }

    public String getComponents() {
        return components;
    }


    public String getDuration() {
        return duration;
    }


    public String getText() {
        return text;
    }


    public String getSource() {
        return source;
    }


    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Spell spell = (Spell) o;

        return name.equals(spell.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getDescription() {

        return text.contains("Классы") ? text : text + "\n\n" + classes;
    }
}

