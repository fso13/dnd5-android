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


public class MonsterInfoFragment extends Fragment {

    private final Monster monster;

    public MonsterInfoFragment(Monster monster) {
        this.monster = monster;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_monster_info, container, false);

        TextView info = root.findViewById(R.id.textView_info);
        info.setText(Html.fromHtml(monster.getFiction().trim() + "<br><br>"), TextView.BufferType.SPANNABLE);

        return root;
    }
}
