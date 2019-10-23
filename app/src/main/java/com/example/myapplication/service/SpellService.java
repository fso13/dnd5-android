package com.example.myapplication.service;

import com.example.myapplication.model.ClassInfo;
import com.example.myapplication.model.Clazz;
import com.example.myapplication.model.Spell;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SpellService {
    private static final Pattern PATTERN = Pattern.compile("[А-я]", Pattern.MULTILINE);
    private static Type listSpellType = Types.newParameterizedType(List.class, Spell.class);
    private static Type mapStringSpellsType = Types.newParameterizedType(Map.class, Clazz.class, ClassInfo.class);

    public static List<Spell> getAllSpells(String json) throws IOException {
        Moshi moshi = new Moshi.Builder().build();

        JsonAdapter<List<Spell>> jsonAdapter = moshi.adapter(listSpellType);
        return jsonAdapter.fromJson(json);
    }

    public static Map<Clazz, ClassInfo> getClassSpells(String json) throws IOException {
        Moshi moshi = new Moshi.Builder().build();

        JsonAdapter<Map<Clazz, ClassInfo>> jsonAdapter = moshi.adapter(mapStringSpellsType);
        return jsonAdapter.fromJson(json);
    }

    public static List<Spell> getAllSpells(List<Spell> spells, String txt) {

        final Map<String, String> map = new HashMap<>();
        for (String s : txt.split("\n")) {
            Matcher m = PATTERN.matcher(s);
            if (m.find()) {
                map.put(s.substring(0, m.toMatchResult().start()).trim(), s.substring(m.toMatchResult().start()));
            }
        }

        for (final Spell spell : spells) {
            String s = map.get(spell.getEn().getName().toUpperCase());
            if (s != null) {
                spell.getRu().setName(s);
            } else {
                spell.getRu().setName(spell.getRu().getName().toUpperCase());
            }
        }
        return spells;
    }

}
