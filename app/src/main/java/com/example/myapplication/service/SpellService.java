package com.example.myapplication.service;

import com.example.myapplication.model.ClassInfo;
import com.example.myapplication.model.Clazz;
import com.example.myapplication.model.Spell;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


public class SpellService {
    private static Type listSpellType = Types.newParameterizedType(List.class, Spell.class);
    private static Type mapStrinfSpellsType = Types.newParameterizedType(Map.class, Clazz.class, ClassInfo.class);

    public static List<Spell> getAllSpells(String json) throws IOException {
        Moshi moshi = new Moshi.Builder().build();

        JsonAdapter<List<Spell>> jsonAdapter = moshi.adapter(listSpellType);
        return jsonAdapter.fromJson(json);
    }

    public static Map<Clazz, ClassInfo> getClassSpells(String json) throws IOException {
        Moshi moshi = new Moshi.Builder().build();

        JsonAdapter<Map<Clazz, ClassInfo>> jsonAdapter = moshi.adapter(mapStrinfSpellsType);
        return jsonAdapter.fromJson(json);
    }
}
