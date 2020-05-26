package ru.drudenko.dnd.adapter;

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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.magic.Spell;

public class SpellAdapter extends BaseAdapter implements Filterable {
    private List<Spell> originalData;
    private List<Spell> filteredData;
    private LayoutInflater mInflater;
    private ItemFilter mFilter = new ItemFilter();
    private String classFilterText = "Все";
    private String levelFilterText = "Все";
    private String nameFilterText = "";
    private Context context;
    private App app;
    private SharedPreferences preferences;

    public SpellAdapter(Context context, List<Spell> data, App app, SharedPreferences preferences) {
        this.context = context;
        this.app = app;
        this.preferences = preferences;
        originalData = new ArrayList<>(data);
        filteredData = new ArrayList<>(data);
        mInflater = LayoutInflater.from(context);
    }

    private boolean isBelong(String filterClass, Spell spell) {
        return spell.getClasses().contains(filterClass);
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
        viewHolder.textView.setText(String.format("%s: %s", spell.getLevel(), spell.getName()));

        viewHolder.toggleButton.setChecked(spell.isFavorite());
        viewHolder.toggleButton.setTextOff("");
        viewHolder.toggleButton.setTextOn("");

        viewHolder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context, spell.isFavorite() ? R.drawable.start_on : R.drawable.start_off));
        viewHolder.toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) {
                if (isChecked) {
                    viewHolder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.start_on));
                    spell.setFavorite(true);

                    app.spellsFavorite.add(spell);
                    app.spells.get(app.spells.indexOf(spell)).setFavorite(true);

                    final String key = spell.getName().replace(" ", "_");
                    preferences.edit().putBoolean(key, true).apply();


                } else {
                    viewHolder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.start_off));
                    spell.setFavorite(false);
                    app.spellsFavorite.remove(spell);
                    app.spells.get(app.spells.indexOf(spell)).setFavorite(false);

                    final String key = spell.getName().replace(" ", "_");
                    preferences.edit().putBoolean(key, false).apply();
                }

            }
        });

        return view;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private static class ViewHolder {
        TextView textView;
        ToggleButton toggleButton;
    }

    private class ItemFilter extends Filter {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredData.clear();
            ArrayList<Spell> spells = new ArrayList<>();
            String[] filterString = constraint.toString().split(":");
            FilterResults results = new FilterResults();


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

            for (Spell spell : originalData) {

                if (filter(spell)) {
                    spells.add(spell);
                }
            }


            results.values = spells;
            results.count = spells.size();

            return results;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private boolean filter(Spell spell) {
            return ("".equals(nameFilterText) || spell.getName().toLowerCase().contains(nameFilterText)) && (levelFilterText.equals("Все") || spell.getLevel().equals(levelFilterText)) && (classFilterText.equals("Все") || isBelong(classFilterText, spell));
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values == null) {
                filteredData = new ArrayList<>();
            } else {
                filteredData = (ArrayList<Spell>) results.values;
            }
            notifyDataSetChanged();
        }
    }
}

