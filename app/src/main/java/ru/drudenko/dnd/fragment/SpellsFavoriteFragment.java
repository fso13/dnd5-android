package ru.drudenko.dnd.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.activity.MainActivity;
import ru.drudenko.dnd.activity.SpellActivity;
import ru.drudenko.dnd.adapter.SpellAdapter;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.magic.ClassInfo;
import ru.drudenko.dnd.model.magic.Clazz;
import ru.drudenko.dnd.model.magic.InfoSpell;
import ru.drudenko.dnd.model.magic.Spell;

public class SpellsFavoriteFragment extends Fragment {

    private static List<String> classes = Clazz.getRu();
    private static List<String> level = Arrays.asList("Все", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    @Inject
    List<Spell> spells;
    @Inject
    Map<Clazz, ClassInfo> clazzMap;
    private SpellAdapter spellAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getComponent().inject(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.activity_favorite, container, false);
        ExpandableListView listView = root.findViewById(R.id.grid_view_spells);


        List<Spell> spells3 = new ArrayList<>();
        for (Spell s : spells) {
            if (s.isFavorite()) {
                spells3.add(s);
            }
        }

        spellAdapter = new SpellAdapter(getContext(), spells3, clazzMap);
        listView.setAdapter(spellAdapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getContext(), SpellActivity.class);
                Object item = spellAdapter.getChild(groupPosition, childPosition);
                if (item instanceof Spell) {
                    InfoSpell spell = ((Spell) item).getRu();
                    String nameMessage = spell.getName();
                    intent.putExtra("SPELL_NAME", nameMessage);
                    String infoMessage = spell.toString();
                    intent.putExtra("SPELL_INFO", infoMessage);
                    startActivityForResult(intent, 0);
                }
                return true;
            }
        });


        Spinner spinnerClass = root.findViewById(R.id.spinner_classes);
        ArrayAdapter<String> adapterClasses = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_dropdown_item, classes);
        adapterClasses.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerClass.setAdapter(adapterClasses);
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spellAdapter.getFilter().filter("class:" + classes.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerLevel = root.findViewById(R.id.spinner_level);
        ArrayAdapter<String> adapterLevel = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_dropdown_item, level);
        adapterLevel.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerLevel.setAdapter(adapterLevel);
        spinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spellAdapter.getFilter().filter("level:" + level.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(((MainActivity) getContext()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                spellAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                spellAdapter.getFilter().filter(newText);
                return false;
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                          }
                                      }
        );
    }


}
