package ru.drudenko.dnd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.model.monster.Biom;
import ru.drudenko.dnd.model.monster.Monster;


public class MonsterAbilityFragment extends Fragment {

    private final Monster monster;

    public MonsterAbilityFragment(Monster monster) {
        this.monster = monster;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_monster_ability, container, false);

        TextView con = root.findViewById(R.id.textView_con);
        con.setText(Monster.getChr(monster.getCon()));

        TextView str = root.findViewById(R.id.textView_str);
        str.setText(Monster.getChr(monster.getStr()));

        TextView dex = root.findViewById(R.id.textView_dex);
        dex.setText(Monster.getChr(monster.getDex()));

        TextView cha = root.findViewById(R.id.textView_cha);
        cha.setText(Monster.getChr(monster.getCha()));

        TextView intilect = root.findViewById(R.id.textView_int);
        intilect.setText(Monster.getChr(monster.getIntilect()));

        TextView wis = root.findViewById(R.id.textView_wis);
        wis.setText(Monster.getChr(monster.getWis()));

        TextView navik = root.findViewById(R.id.textView_navik);
        navik.setText(monster.getSkill());

        TextView size = root.findViewById(R.id.textView_size);
        size.setText(monster.getSize());

        TextView chuvstva = root.findViewById(R.id.textView_chuvstva);
        String pv = "Пассивное восприятие: " + monster.getPassive();
        if (monster.getSenses() != null) {
            pv = pv + "," + monster.getSenses();
        }
        chuvstva.setText(pv);

        TextView lang = root.findViewById(R.id.textView_lang);
        lang.setText(monster.getLanguages());

        TextView biom = root.findViewById(R.id.textView_biom);

        String biomString = "";
        List<Biom> monsterBiom = monster.getBiom();
        for (int i = 0; i < monsterBiom.size(); i++) {
            Biom biom1 = monsterBiom.get(i);
            if (i == 0) {
                biomString = biom1.ru;
            } else {
                biomString = biomString + "," + biom1.ru;

            }
        }
        biom.setText(biomString);

        return root;
    }
}
