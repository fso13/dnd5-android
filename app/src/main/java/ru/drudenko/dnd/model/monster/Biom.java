package ru.drudenko.dnd.model.monster;

import java.util.ArrayList;
import java.util.List;

public enum Biom {
    All("Все"),
    ARCTIC("Заполярье"),
    COASTAL("Побережье"),
    DESERT("Пустыня"),
    FOREST("Лес"),
    GRASSLAND("Равнина"),
    HILL("Холмы"),
    MOUNTAIN("Горы"),
    SWAMP("Болота"),
    UNDERDARK("Подземье"),
    URBAN("Город"),

    ;

    public final String ru;

    Biom(String ru) {
        this.ru = ru;
    }

    public static Biom fromRu(String ru) {
        for (Biom clazz : Biom.values()) {
            if (clazz.ru.equals(ru)) {
                return clazz;
            }
        }
        return null;
    }

    public static List<String> getRu() {
        List<String> list = new ArrayList<>();
        for (Biom clazz : Biom.values()) {
            list.add(clazz.ru);
        }
        return list;
    }


}
