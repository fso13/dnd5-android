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
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SpellAdapter;
import com.example.myapplication.model.InfoSpell;
import com.example.myapplication.model.Spell;
import com.example.myapplication.service.SpellService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;

public class SpellsAllActivity extends Fragment {

    private ListView listView;
private SpellService spellService;
private SpellAdapter spellAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

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
            spells.sort(new Comparator<Spell>() {
                @Override
                public int compare(Spell o1, Spell o2) {
                    return o1.getRu().getName().compareTo(o2.getRu().getName());
                }
            });
            listView.setAdapter(new SpellAdapter(getContext(),0, spellService.getAll()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(getContext(), SpellActivity.class);

                InfoSpell spell = spellService.getById(position).getRu();

                String nameMessage = spell.getName();
                intent.putExtra("SPELL_NAME", nameMessage);

                String infoMessage = spell.toString();
                intent.putExtra("SPELL_INFO", infoMessage);

                startActivityForResult(intent,0);

            }
        };
        listView.setOnItemClickListener(itemListener);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println(newText);

                spellAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
////        Toolbar myToolbar = findViewById(R.id.toolbar);
////        setSupportActionBar(myToolbar);
////        getSupportActionBar().setDisplayShowTitleEnabled(false);
//
//
//        setContentView(R.layout.activity_spells_all);
//        listView = findViewById(R.id.grid_view_spells);
//
//
//        StringBuilder total = new StringBuilder();
//
//        try {
//            BufferedReader r = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.spells)));
//            for (String line; (line = r.readLine()) != null; ) {
//                total.append(line).append('\n');
//            }
//            spellService = new SpellService(android.text.Html.fromHtml(total.toString()).toString());
//            listView.setAdapter(new SpellAdapter(this, spellService.getAll()));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                Intent intent = new Intent(getApplicationContext(), SpellActivity.class);
//
//                InfoSpell spell = spellService.getById(position).getRu();
//
//                String nameMessage = spell.getName();
//                intent.putExtra("SPELL_NAME", nameMessage);
//
//                String infoMessage = spell.toString();
//                intent.putExtra("SPELL_INFO", infoMessage);
//
//                startActivity(intent);
//
//            }
//        };
//        listView.setOnItemClickListener(itemListener);
//    }
}
