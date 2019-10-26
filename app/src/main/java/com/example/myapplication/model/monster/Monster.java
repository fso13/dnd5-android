package com.example.myapplication.model.monster;

import com.example.myapplication.model.SingleToArray;
import com.squareup.moshi.Json;

import java.util.List;

public class Monster {
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
    @Json(name = "int")
    private String intilect;
    private String wis;
    private String cha;

    private String passive;
    private String languages;
    private String cr;
    private String biom;
    private String sType;

    @SingleToArray
    private List<Trait> trait;
    @SingleToArray
    private List<Trait> action;

    private boolean isFavorite;

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

    public String getBiom() {
        return biom;
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

    public String getInfo1() {
        return "Класс защиты:" + ac + '\n' +
                "ХП:" + hp + '\n' +
                "Скорость:" + speed;
    }

    public String getInfo2() {
        return "\nСила:" + str + '\t' +
                "Ловкость:" + dex + '\t' +
                "Телосложение:" + con + '\n' +
                "Интеллект:" + intilect + '\t' +
                "Мудрость:" + wis + '\t' +
                "Харизма:" + cha;
    }

    public String getText() {
        StringBuilder r = new StringBuilder(fiction + '\n' +
                "Размер: " + size + '\n' +
                "Тип: " + type + '(' + sType + ")\n" +
                "Мировозрение: " + alignment + '\n' +
                "Пассивное восприятие: " + passive + '\n' +
                "Языки: " + languages + '\n' +
                "Сложность: " + cr + '\n');
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

        return r.toString();
    }
}
