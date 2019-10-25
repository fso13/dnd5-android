package com.example.myapplication.model.monster;

import com.example.myapplication.model.SingleToArray;

import java.util.List;

public class Action {
    private String name;
    @SingleToArray
    private List<String> text;
    @SingleToArray
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
}
