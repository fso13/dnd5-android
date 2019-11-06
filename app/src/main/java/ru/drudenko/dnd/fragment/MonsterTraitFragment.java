package ru.drudenko.dnd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.model.monster.Monster;
import ru.drudenko.dnd.model.monster.Trait;


public class MonsterTraitFragment extends Fragment {

    private final Monster monster;

    public MonsterTraitFragment(Monster monster) {
        this.monster = monster;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_monster_trait, container, false);

        TextView trait = root.findViewById(R.id.textView_trait);
        StringBuilder r = new StringBuilder();
        for (Trait t : monster.getTrait()) {
            r.append(t.toString()).append("\n");
        }
        trait.setText(r.toString());


        return root;
    }
}
