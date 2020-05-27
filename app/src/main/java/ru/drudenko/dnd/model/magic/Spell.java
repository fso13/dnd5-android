package ru.drudenko.dnd.model.magic;

import java.io.Serializable;
import java.util.List;

public class Spell implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String ritual;
    private String school;
    private String level;
    private String castingTime;
    private String range;
    private String components;
    private String duration;
    private String text;
    private String source;
    private List<String> classes;

    private boolean isFavorite;


    public Spell() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRitual() {
        return ritual;
    }

    public void setRitual(String ritual) {
        this.ritual = ritual;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCastingTime() {
        return castingTime;
    }

    public void setCastingTime(String castingTime) {
        this.castingTime = castingTime;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
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

