package ru.drudenko.dnd.model.monster;

public enum Size {
    T("Крошечный"),
    S("Маленький"),
    M("Средний"),
    L("Большой"),
    H("Огромный"),
    G("Колоссальный");

    public String text;

    Size(String text) {
        this.text = text;
    }
}
