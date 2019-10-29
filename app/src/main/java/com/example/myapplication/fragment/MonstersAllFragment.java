package com.example.myapplication.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.activity.MonsterActivity;
import com.example.myapplication.adapter.MonsterAdapter;
import com.example.myapplication.di.App;
import com.example.myapplication.model.monster.Biom;
import com.example.myapplication.model.monster.Monster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class MonstersAllFragment extends Fragment {
    public static List<String> bioms = Biom.getRu();
    public static List<String> expId;
    public static Map<String, String> exps = new HashMap<>();

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

    @Inject
    List<Monster> monsters;


    MonsterAdapter monsterAdapter;

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

        monsterAdapter = new MonsterAdapter(getContext(), monsters);

        listView.setAdapter(monsterAdapter);


        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(getContext(), MonsterActivity.class);
                Monster monster = monsterAdapter.getItem(position);

                String nameMessage = monster.getName();
                intent.putExtra("MONSTER_NAME", nameMessage);
                intent.putExtra("MONSTER_INFO1", monster.getInfo1());
                intent.putExtra("MONSTER_INFO2", monster.getInfo2());
                intent.putExtra("MONSTER_TEXT", monster.getText());
                startActivityForResult(intent, 0);
            }
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
        searchView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                          }
                                      }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SharedPreferences.Editor editor = preferences.edit();
        for (Monster monster : monsters) {
            final String key = "MONSTER_" + monster.getName().replace(" ", "_");
            editor.remove(key);
            editor.putBoolean(key, monster.isFavorite());
        }
        editor.apply();

    }
}
