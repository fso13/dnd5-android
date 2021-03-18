package ru.drudenko.dnd.model;

import java.util.ArrayList;
import java.util.List;

import ru.drudenko.dnd.model.magic.Spell;
import ru.drudenko.dnd.model.monster.Monster;

public class Profile {
    private String name;
    private boolean isCurrent;
    private List<Monster> monsters = new ArrayList<>();
    private List<Spell> spells = new ArrayList<>();

    public Profile(String name, boolean isCurrent) {
        this.name = name;
        this.isCurrent = isCurrent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public void setSpells(List<Spell> spells) {
        this.spells = spells;
    }
}
