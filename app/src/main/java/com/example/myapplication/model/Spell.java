package com.example.myapplication.model;

public class Spell {
    private InfoSpell en;
    private InfoSpell ru;

    public Spell() {
    }

    public Spell(InfoSpell en, InfoSpell ru) {
        this.en = en;
        this.ru = ru;
    }

    public InfoSpell getEn() {
        return en;
    }

    public void setEn(InfoSpell en) {
        this.en = en;
    }

    public InfoSpell getRu() {
        return ru;
    }

    public void setRu(InfoSpell ru) {
        this.ru = ru;
    }

    @Override
    public String toString() {
        return ru.toString();
    }
}

