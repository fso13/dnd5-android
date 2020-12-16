package ru.drudenko.dnd.fragment.spell;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.List;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.activity.MainActivity;
import ru.drudenko.dnd.activity.SpellActivity;
import ru.drudenko.dnd.adapter.SpellAdapter;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.magic.Clazz;
import ru.drudenko.dnd.model.magic.Spell;

public class SpellsFavoriteFragment extends Fragment implements AbsListView.OnScrollListener {

    private static final List<String> classes = Clazz.getRu();
    private static final List<String> level = Arrays.asList("Все", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    private static final List<String> schools = Arrays.asList("Все",
            "Прорицание",
            "Магия Пустоты",
            "Некромантия",
            "Иллюзия",
            "Очарование",
            "Воплощение",
            "Вызов",
            "Ритуал",
            "Пустота",
            "Вызов",
            "Преобразование",
            "Ограждение",
            "Воплощение");
    private SpellAdapter spellAdapter;
    private Spell spell;

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
        ListView listView = root.findViewById(R.id.grid_view_spells);


        spellAdapter = new SpellAdapter(getContext(), ((App) getActivity().getApplication()).spellsFavorite, ((App) getActivity().getApplication()));
        listView.setAdapter(spellAdapter);

        AdapterView.OnItemClickListener itemListener = (parent, v, position, id) -> {
            Intent intent = new Intent(getContext(), SpellActivity.class);
            spell = spellAdapter.getItem(position);
            if (spell != null) {
                intent.putExtra("SPELL", spell);
                startActivityForResult(intent, 0);
            }
        };
        listView.setOnItemClickListener(itemListener);
        listView.setOnScrollListener(this);


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

        Spinner spinnerSchools = root.findViewById(R.id.spinner_schools);
        ArrayAdapter<String> adapterSchools = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_dropdown_item, schools);
        adapterSchools.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerSchools.setAdapter(adapterSchools);
        spinnerSchools.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spellAdapter.getFilter().filter("school:" + schools.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return root;
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
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
        searchView.setOnClickListener(v -> {
                }
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((App) getActivity().getApplication()).spellFavoriteService.setFavorite(spell, spell.isFavorite());
        spellAdapter.notifyDataSetChanged();
    }
}
