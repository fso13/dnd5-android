package ru.drudenko.dnd.fragment.monster;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.model.monster.Monster;
import ru.drudenko.dnd.model.monster.Trait;


public class MonsterActionFragment extends Fragment {

    public MonsterActionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_monster_action, container, false);
        Monster monster = (Monster) getArguments().getSerializable("monster");

        TextView action = root.findViewById(R.id.textView_action);
        StringBuilder r = new StringBuilder();
        for (Trait t : monster.getAction()) {
            r.append(t.toString());
        }

        action.setText(Html.fromHtml(r.toString()), TextView.BufferType.SPANNABLE);


        return root;
    }
}
