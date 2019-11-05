package ru.drudenko.dnd.model.monster;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import ru.drudenko.dnd.fragment.MonstersAllFragment;

public class Monster implements Serializable {
    static final long serialVersionUID = -8686024160072043140L;

    private String name;
    private String fiction = "";
    private String size;
    private String type;
    private String alignment;
    private String ac;
    private String hp;
    private String speed;

    private String str;
    private String dex;
    private String con;
    private String intilect;
    private String wis;
    private String cha;

    private String passive;
    private String languages;
    private String cr;
    private String biom;
    private String sType;

    private List<Trait> trait;
    private List<Trait> action;

    private boolean isFavorite;

    private List<Biom> bioms = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFiction() {
        return fiction;
    }

    public void setFiction(String fiction) {
        this.fiction = fiction;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getDex() {
        return dex;
    }

    public void setDex(String dex) {
        this.dex = dex;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getIntilect() {
        return intilect;
    }

    public void setIntilect(String intilect) {
        this.intilect = intilect;
    }

    public String getWis() {
        return wis;
    }

    public void setWis(String wis) {
        this.wis = wis;
    }

    public String getCha() {
        return cha;
    }

    public void setCha(String cha) {
        this.cha = cha;
    }

    public String getPassive() {
        return passive;
    }

    public void setPassive(String passive) {
        this.passive = passive;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getCr() {
        return cr;
    }

    public void setCr(String cr) {
        this.cr = cr;
    }

    public List<Biom> getBiom() {
        if (bioms != null) {
            return bioms;
        }

        bioms = new LinkedList<>();
        String[] bb = biom == null ? new String[]{"All"} : biom.split(",");
        for (int i = 0; i < bb.length; i++) {
            bioms.add(i, Biom.valueOf(bb[i].trim()));
        }
        return bioms;
    }

    public void setBiom(String biom) {
        this.biom = biom;
    }

    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }

    public List<Trait> getTrait() {
        return trait;
    }

    public void setTrait(List<Trait> trait) {
        this.trait = trait;
    }

    public List<Trait> getAction() {
        return action;
    }

    public void setAction(List<Trait> action) {
        this.action = action;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public static String getChr(String s) {
        return s + "(" + (int) Math.floor((Double.valueOf(s) - 10) / 2) + ")";
    }

    public String getInfo1() {
        return "Класс защиты:" + ac + '\n' +
                "ХП:" + hp + '\n' +
                "Сложность: " + cr + " (" + MonstersAllFragment.exps.get(cr) + ")\n" +
                "Скорость:" + speed;
    }

    public String getInfo2() {
        return "Сила:" + getChr(str) + '\t' +
                "Ловкость:" + getChr(dex) + '\t' +
                "Телосложение:" + getChr(con) + '\n' +
                "Интеллект:" + getChr(intilect) + '\t' +
                "Мудрость:" + getChr(wis) + '\t' +
                "Харизма:" + getChr(cha);
    }

    public String getText() {
        StringBuilder r = new StringBuilder(fiction + '\n' +
                "Размер: " + size + '\n' +
                "Тип: " + type + '(' + sType + ")\n" +
                "Мировозрение: " + alignment + '\n' +
                "Пассивное восприятие: " + passive + '\n' +
                "Языки: " + languages + '\n');
        if (trait != null) {
            r.append("Черты:").append('\n');
            for (Trait t : trait) {
                r.append(t.toString());
            }
        }

        if (action != null) {
            r.append("Действия:").append('\n');
            for (Trait a : action) {
                r.append(a.toString());
            }
        }

        return r.toString().trim();
    }
}
