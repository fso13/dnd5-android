package ru.drudenko.dnd.model.magic;

import java.io.Serializable;

public class Spell implements Serializable {
    private InfoSpell en;
    private InfoSpell ru;
    private boolean isFavorite;

    public Spell() {
    }

    public Spell(InfoSpell en, InfoSpell ru, boolean isFavorite) {
        this.en = en;
        this.ru = ru;
        this.isFavorite = isFavorite;
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return ru.toString();
    }
}

