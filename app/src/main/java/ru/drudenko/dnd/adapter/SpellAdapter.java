package ru.drudenko.dnd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
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
    private final List<Spell> list;
    private final LayoutInflater mInflater;
    private final ItemFilter mFilter = new ItemFilter();
    private final Context context;
    private final App app;
    private List<Spell> filteredData;
    private String classFilterText = "Все";
    private String levelFilterText = "Все";
    private String schoolFilterText = "Все";
    private String favoriteFilterText = "false";
    private String nameFilterText = "";
    private CharSequence constraintAdapter;


    public SpellAdapter(Context context, List<Spell> data, App app) {
        this.context = context;
        this.app = app;
        list = data;
        filteredData = new ArrayList<>();
        filteredData.addAll(data);
        mInflater = LayoutInflater.from(context);
    }

    public CharSequence getConstraintAdapter() {
        return constraintAdapter;
    }

    private boolean isBelongClass(String filterClass, Spell spell) {
        return spell.getClasses().contains(filterClass);
    }

    private boolean isBelongSchool(String schoolFilterText, Spell spell) {
        return spell.getSchool() != null && spell.getSchool().toUpperCase().contains(schoolFilterText.toUpperCase());
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
        View view;
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
                } else {
                    viewHolder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.start_off));
                }

                app.spellFavoriteService.setFavorite(spell, isChecked);
                getFilter().filter(constraintAdapter);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    public Filter getFilter() {
        return mFilter;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private static class ViewHolder {
        TextView textView;
        ToggleButton toggleButton;
    }

    private class ItemFilter extends Filter {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraintAdapter = constraint;
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

                if (filterString[0].equals("school")) {
                    schoolFilterText = filterString[1];
                }

                if (filterString[0].equals("favorite")) {
                    favoriteFilterText = filterString[1];
                }
            }

            List<Spell> nlist = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Spell originalDatum = list.get(i);
                if (filter(originalDatum)) {
                    nlist.add(originalDatum);
                }
            }

            results.values = nlist;
            results.count = nlist.size();


            return results;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private boolean filter(Spell spell) {
            return ("".equals(nameFilterText) || spell.getName().toLowerCase().contains(nameFilterText)) &&
                    (levelFilterText.equalsIgnoreCase("Все") || spell.getLevel().equals(levelFilterText)) &&
                    (schoolFilterText.equalsIgnoreCase("Все") || isBelongSchool(schoolFilterText, spell)) &&
                    (classFilterText.equalsIgnoreCase("Все") || isBelongClass(classFilterText, spell)) &&
                    ((spell.isFavorite() && "true".equals(favoriteFilterText) || "false".equals(favoriteFilterText)));
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

