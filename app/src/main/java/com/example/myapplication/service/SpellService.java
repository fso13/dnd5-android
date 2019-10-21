package com.example.myapplication.service;

import com.example.myapplication.model.Spell;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;


public class SpellService {
private List<Spell> spells;

    public SpellService(String json) throws IOException {
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Spell.class);
        JsonAdapter<List<Spell>> jsonAdapter = moshi.adapter(type);
        spells =  jsonAdapter.fromJson(json);
    }

    public List<Spell> getAll() {
        return spells;
    }

    public Spell getById(int i) {
        return spells.get(i);
    }
}
