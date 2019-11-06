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


public class MonsterActionFragment extends Fragment {

    private final Monster monster;

    public MonsterActionFragment(Monster monster) {
        this.monster = monster;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_monster_action, container, false);

        TextView action = root.findViewById(R.id.textView_action);
        StringBuilder r = new StringBuilder();
        for (Trait t : monster.getAction()) {
            r.append(t.toString()).append("\n\n");
        }
        action.setText(r.toString());


        return root;
    }
}
