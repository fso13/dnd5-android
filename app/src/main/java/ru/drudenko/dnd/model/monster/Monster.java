package ru.drudenko.dnd.model.monster;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Monster implements Serializable {

    private final String name;
    private final String size;
    private final String type;
    private final String alignment;
    private final String ac;
    private final String hp;
    private final String speed;
    private final String str;
    private final String dex;
    private final String con;
    private final String intilect;
    private final String wis;
    private final String cha;
    private final String passive;
    private final String languages;
    private final String cr;
    private final List<String> bioms;
    private final String senses;
    private final String skill;
    private String fiction;
    private List<Trait> trait;
    private List<Trait> action;
    private boolean isFavorite;

    public Monster(String name, String fiction, String size, String type, String alignment, String ac, String hp, String speed, String str, String dex, String con, String intilect, String wis, String cha, String passive, String languages, String cr, String biom, String sType, String senses, String skill, List<Trait> trait, boolean isFavorite, List<Trait> action) {
        this.name = name;
        this.fiction = fiction;
        this.size = size;
        this.type = type;
        this.alignment = alignment;
        this.ac = ac;
        this.hp = hp;
        this.speed = speed;
        this.str = str;
        this.dex = dex;
        this.con = con;
        this.intilect = intilect;
        this.wis = wis;
        this.cha = cha;
        this.passive = passive;
        this.languages = languages;
        this.cr = cr;
        this.bioms = new ArrayList<>(Collections.singletonList("Все"));
        if (biom != null) {
            bioms.addAll(Arrays.asList(biom.split(",")));
        }
        this.senses = senses;
        this.skill = skill;
        this.trait = trait;
        this.action = action;
        this.isFavorite = isFavorite;
    }

    public static String getChr(String s) {
        return s + "(" + (int) Math.floor((Double.parseDouble(s) - 10) / 2) + ")";
    }


    public String getSenses() {
        return senses != null ? senses : "";
    }


    public String getSkill() {
        return skill;
    }


    public String getName() {
        return name;
    }


    public String getFiction() {
        if (fiction == null) {
            fiction = "";
        }
        return fiction;
    }


    public String getSize() {
        return size;

    }

    public List<String> getBioms() {
        return bioms;
    }

    public String getType() {
        return type;
    }

    public String getAlignment() {
        return alignment;
    }

    public String getAc() {
        return ac;
    }

    public String getHp() {
        return hp;
    }

    public String getSpeed() {
        return speed;
    }

    public String getStr() {
        return str;
    }

    public String getDex() {
        return dex;
    }

    public String getCon() {
        return con;
    }

    public String getIntilect() {
        return intilect;
    }

    public String getWis() {
        return wis;
    }

    public String getCha() {
        return cha;
    }

    public String getPassive() {
        return passive;
    }

    public String getLanguages() {
        return languages;
    }

    public String getCr() {
        return cr;
    }

    public List<Trait> getTrait() {
        if (trait == null) {
            trait = new ArrayList<>();
        }
        return trait;
    }

    public List<Trait> getAction() {
        if (action == null) {
            action = new ArrayList<>();
        }
        return action;
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

        Monster monster = (Monster) o;

        return name.equals(monster.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
