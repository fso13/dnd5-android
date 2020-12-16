package ru.drudenko.dnd.model.magic;

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

    private final String ru;

    Clazz(String ru) {
        this.ru = ru;
    }

    public static List<String> getRu() {
        List<String> list = new ArrayList<>();
        for (Clazz clazz : Clazz.values()) {
            list.add(clazz.ru);
        }
        return list;
    }

}
