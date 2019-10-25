package com.example.myapplication.model.magic;

import java.util.ArrayList;
import java.util.List;

public enum Clazz {
    All("Все"),
    Bard("Бард"),
    Cleric("Жрец"),
    Druid("Друид"),
    Paladin("Паладин"),
    Ranger("Рейнджер"),
    Sorcerer("Чародей"),
    Warlock("Колдун"),
    Wizard("Волшебник"),

    ;

    private String ru;

    Clazz(String ru) {
        this.ru = ru;
    }

    public static Clazz fromRu(String ru) {
        for (Clazz clazz : Clazz.values()) {
            if (clazz.ru.equals(ru)) {
                return clazz;
            }
        }
        return null;
    }

    public static List<String> getRu() {
        List<String> list = new ArrayList<>();
        for (Clazz clazz : Clazz.values()) {
            list.add(clazz.ru);
        }
        return list;
    }

}
