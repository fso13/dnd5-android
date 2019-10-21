package com.example.myapplication.activity;

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
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SpellAdapter;
import com.example.myapplication.model.InfoSpell;
import com.example.myapplication.model.Spell;
import com.example.myapplication.service.SpellService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class SpellsAllActivity extends Fragment {

    private ListView listView;
    private SpellService spellService;
    private SpellAdapter spellAdapter;
    private Spinner spinnerClass;
    private Spinner spinnerLevel;
    private static List<String> classes = Arrays.asList("Все", "Колдун", "Бард");
    private static List<String> level = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");


    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.activity_spells_all, container, false);
        listView = root.findViewById(R.id.grid_view_spells);

        StringBuilder total = new StringBuilder();

        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.spells)));
            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }
            spellService = new SpellService(android.text.Html.fromHtml(total.toString()).toString());

            List<Spell> spells = spellService.getAll();
//            spells.sort(new Comparator<Spell>() {
//                @Override
//                public int compare(Spell o1, Spell o2) {
//                    return o1.getRu().getName().compareTo(o2.getRu().getName());
//                }
//            });
            spellAdapter = new SpellAdapter(getContext(), 0, spellService.getAll());
            listView.setAdapter(spellAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(getContext(), SpellActivity.class);

                InfoSpell spell = spellAdapter.getItem(position).getRu();

                String nameMessage = spell.getName();
                intent.putExtra("SPELL_NAME", nameMessage);

                String infoMessage = spell.toString();
                intent.putExtra("SPELL_INFO", infoMessage);

                startActivityForResult(intent, 0);

            }
        };
        listView.setOnItemClickListener(itemListener);


        spinnerClass = root.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, classes);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerClass.setAdapter(adapter);


        spinnerLevel = root.findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, level);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerLevel.setAdapter(adapter2);
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
                System.out.println(query);

                spellAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println(newText);

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
