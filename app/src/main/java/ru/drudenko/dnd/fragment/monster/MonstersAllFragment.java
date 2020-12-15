package ru.drudenko.dnd.fragment.monster;

import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.activity.MainActivity;
import ru.drudenko.dnd.activity.MonsterActivity;
import ru.drudenko.dnd.adapter.MonsterAdapter;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.monster.Biom;
import ru.drudenko.dnd.model.monster.Monster;

public class MonstersAllFragment extends Fragment implements AbsListView.OnScrollListener{
    public static Map<String, String> exps = new HashMap<>();
    static List<String> bioms = Biom.getRu();
    static List<String> expId;

    static {
        exps.put("Все", "Все");
        exps.put("0", "0 - 10");
        exps.put("1/8", "25");
        exps.put("1/4", "50");
        exps.put("1/2", "100");
        exps.put("1", "200");
        exps.put("2", "450");
        exps.put("3", "700");
        exps.put("4", "1100");
        exps.put("5", "1800");
        exps.put("6", "2300");
        exps.put("7", "2900");
        exps.put("8", "3900");
        exps.put("9", "5000");
        exps.put("10", "5900");
        exps.put("11", "7200");
        exps.put("12", "8400");
        exps.put("13", "10000");
        exps.put("14", "11500");
        exps.put("15", "13000");
        exps.put("16", "15000");
        exps.put("17", "18000");
        exps.put("18", "20000");
        exps.put("19", "22000");
        exps.put("20", "25000");
        exps.put("21", "33000");
        exps.put("22", "41000");
        exps.put("23", "50000");
        exps.put("24", "62000");
        exps.put("25", "75000");
        exps.put("26", "90000");
        exps.put("27", "105000");
        exps.put("28", "120000");
        exps.put("29", "135000");
        exps.put("30", "155000");


        expId = new ArrayList<>();
        expId.add("Все");
        expId.add("0");
        expId.add("1/8");
        expId.add("1/4");
        expId.add("1/2");
        expId.add("1");
        expId.add("2");
        expId.add("3");
        expId.add("4");
        expId.add("5");
        expId.add("6");
        expId.add("7");
        expId.add("8");
        expId.add("9");
        expId.add("10");
        expId.add("11");
        expId.add("12");
        expId.add("13");
        expId.add("14");
        expId.add("15");
        expId.add("16");
        expId.add("17");
        expId.add("18");
        expId.add("19");
        expId.add("20");
        expId.add("21");
        expId.add("22");
        expId.add("23");
        expId.add("24");
        expId.add("25");
        expId.add("26");
        expId.add("27");
        expId.add("28");
        expId.add("29");
        expId.add("30");
    }

    @Inject
    SharedPreferences preferences;
    private MonsterAdapter monsterAdapter;
    private Monster monster;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getComponent().inject(this);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_monsters_all, container, false);
        ListView listView = root.findViewById(R.id.grid_view_monsters);

        monsterAdapter = new MonsterAdapter(getContext(), ((App) getActivity().getApplication()).monsters, ((App) getActivity().getApplication()), preferences);

        listView.setAdapter(monsterAdapter);
        listView.setOnScrollListener(this);


        AdapterView.OnItemClickListener itemListener = (parent, v, position, id) -> {
            Intent intent = new Intent(getContext(), MonsterActivity.class);
            monster = monsterAdapter.getItem(position);
            intent.putExtra("MONSTER", monster);
            startActivityForResult(intent, 0);
        };
        listView.setOnItemClickListener(itemListener);


        Spinner spinnerClass = root.findViewById(R.id.spinner_level);

        ArrayAdapter<String> adapterClasses = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_dropdown_item, expId);
        adapterClasses.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerClass.setAdapter(adapterClasses);
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monsterAdapter.getFilter().filter("exp:" + expId.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerBiom = root.findViewById(R.id.spinner_biom);
        ArrayAdapter<String> adapterLevel = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_dropdown_item, bioms);
        adapterLevel.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerBiom.setAdapter(adapterLevel);
        spinnerBiom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monsterAdapter.getFilter().filter("biom:" + bioms.get(position));
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
                monsterAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                monsterAdapter.getFilter().filter(newText);
                return false;
            }
        });
        searchView.setOnClickListener(v -> {
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SharedPreferences.Editor editor = preferences.edit();
        for (Monster monster : ((App) getActivity().getApplication()).monsters) {
            final String key = "MONSTER_" + monster.getName().replace(" ", "_");
            editor.remove(key);
            editor.putBoolean(key, monster.isFavorite());
        }
        editor.apply();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final String key = "MONSTER_" + monster.getName().replace(" ", "_");
        monster.setFavorite(preferences.getBoolean(key, monster.isFavorite()));
        monsterAdapter.notifyDataSetChanged();


        if (monster.isFavorite()) {
            ((App) getActivity().getApplication()).monstersFavorite.add(monster);
            ((App) getActivity().getApplication()).monsters.get(((App) getActivity().getApplication()).monsters.indexOf(monster)).setFavorite(true);
        } else {
            ((App) getActivity().getApplication()).monstersFavorite.remove(monster);
            ((App) getActivity().getApplication()).monsters.get(((App) getActivity().getApplication()).monsters.indexOf(monster)).setFavorite(false);
        }
    }
}
