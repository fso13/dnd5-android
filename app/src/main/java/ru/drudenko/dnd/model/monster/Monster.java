package ru.drudenko.dnd.model.monster;


import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

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
    @SerializedName("int")
    private String intilect;
    private String wis;
    private String cha;

    private String passive;
    private String languages;
    private String cr;
    private String biom;
    private String sType;
    private String senses;
    private String skill;
    @JsonAdapter(AlwaysListTypeAdapterFactory.class)
    private List<Trait> trait = new LinkedList<>();
    @JsonAdapter(AlwaysListTypeAdapterFactory.class)
    private List<Trait> action = new LinkedList<>();

    private boolean isFavorite;

    private List<Biom> bioms = null;


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
        this.biom = biom;
        this.sType = sType;
        this.senses = senses;
        this.skill = skill;
        this.trait = trait;
        this.action = action;
        this.isFavorite = isFavorite;
    }

    public static String getChr(String s) {
        return s + "(" + (int) Math.floor((Double.valueOf(s) - 10) / 2) + ")";
    }

    public String getB() {
        return biom;
    }

    public String getSenses() {
        return senses != null ? senses : "";
    }

    public void setSenses(String senses) {
        this.senses = senses;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

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
