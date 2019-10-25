package com.example.myapplication.model.magic;

public class Title {
    private String en;
    private String ru;

    public Title() {
    }

    public Title(String en, String ru) {
        this.en = en;
        this.ru = ru;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getRu() {
        return ru;
    }

    public void setRu(String ru) {
        this.ru = ru;
    }
}
