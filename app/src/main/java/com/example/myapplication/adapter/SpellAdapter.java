package com.example.myapplication.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.model.ClassInfo;
import com.example.myapplication.model.Clazz;
import com.example.myapplication.model.Spell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SpellAdapter extends BaseAdapter implements Filterable {
    private List<Spell> originalData;
    private List<Spell> filteredData;
    private Map<Clazz, ClassInfo> map;
    private LayoutInflater mInflater;
    private ItemFilter mFilter = new ItemFilter();
    private String classFilterText = "Все";
    private String levelFilterText = "Все";
    private String nameFilterText = "";
    private Context context;
    private SharedPreferences preferences;

    public SpellAdapter(Context context, List<Spell> data, Map<Clazz, ClassInfo> map, SharedPreferences preferences) {
        this.context = context;
        this.preferences = preferences;
        Collections.sort(data, new Comparator<Spell>() {
            @Override
            public int compare(Spell o1, Spell o2) {
                int c = o1.getEn().getLevel().compareTo(o2.getEn().getLevel());
                return c == 0 ? o1.getRu().getName().compareTo(o2.getRu().getName()) : c;
            }
        });
        this.filteredData = data;
        this.originalData = data;
        this.map = map;

        mInflater = LayoutInflater.from(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean isBelong(String filterClass, Spell spell) {
        return map.get(Clazz.fromRu(filterClass)).getSpells().contains(spell.getEn().getName());
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View view;
        final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.spell_list_view_item_layout, parent, false);
            viewHolder.textView = convertView.findViewById(R.id.textView2);
            viewHolder.toggleButton = convertView.findViewById(R.id.toggleButton);
            convertView.setTag(viewHolder);
            view = convertView;

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        final Spell spell = filteredData.get(position);

        viewHolder.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        viewHolder.textView.setTypeface(null, Typeface.BOLD);
        viewHolder.textView.setTextColor(Color.WHITE);
        viewHolder.textView.setText(spell.getRu().getName());


        viewHolder.toggleButton.setChecked(spell.isFavorite());
        viewHolder.toggleButton.setTextOff("");
        viewHolder.toggleButton.setTextOn("");

        final String key = spell.getRu().getName().replace(" ", "_");
        spell.setFavorite(preferences.getBoolean(key, spell.isFavorite()));
        viewHolder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context, spell.isFavorite() ? R.drawable.start_on : R.drawable.start_off));
        viewHolder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        viewHolder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.start_on));
                        spell.setFavorite(true);
                        if (preferences.contains(key)) {
                            preferences.edit().remove(key).apply();
                        }
                        preferences.edit().putBoolean(key, true).apply();
                    } else {
                        viewHolder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.start_off));
                        spell.setFavorite(false);
                        if (preferences.contains(key)) {
                            preferences.edit().remove(key).apply();
                        }
                        preferences.edit().putBoolean(key, false).apply();
                    }

                    if (preferences.contains(key)) {
                        preferences.edit().remove(key).apply();
                    }
                    preferences.edit().putBoolean(key, spell.isFavorite()).apply();
                }
            }
        });

        return view;
    }

    private class ViewHolder {
        TextView textView;
        ToggleButton toggleButton;
    }


    public int getCount() {
        return filteredData.size();
    }

    public Spell getItem(int position) {
        return filteredData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public Filter getFilter() {
        return mFilter;
    }


    private class ItemFilter extends Filter {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {


            String[] filterString = constraint.toString().split(":");

            FilterResults results = new FilterResults();

            final List<Spell> list = originalData;

            int count = list.size();
            final ArrayList<Spell> nlist = new ArrayList<>(count);

            Spell spell;

            for (int i = 0; i < count; i++) {
                spell = list.get(i);

                if (filterString.length == 1) {
                    nameFilterText = filterString[0].toLowerCase();
                } else if (filterString.length == 2) {

                    if (filterString[0].equals("level")) {
                        levelFilterText = filterString[1];
                    }

                    if (filterString[0].equals("class")) {
                        classFilterText = filterString[1];
                    }


                }

                if (filter(spell)) {
                    nlist.add(spell);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private boolean filter(Spell spell) {
            return ("".equals(nameFilterText) || spell.getRu().getName().toLowerCase().contains(nameFilterText)) && (levelFilterText.equals("Все") || spell.getEn().getLevel().equals(levelFilterText)) && (classFilterText.equals("Все") || isBelong(classFilterText, spell));
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<Spell>) results.values;
            notifyDataSetChanged();
        }

    }
}

//in your Activity or Fragment where of Adapter is instantiated :
