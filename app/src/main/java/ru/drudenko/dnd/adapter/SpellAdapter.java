package ru.drudenko.dnd.adapter;

import android.content.Context;
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
import java.util.stream.Collectors;

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
    private String schoolFilterText = "Все";
    private String nameFilterText = "";
    private Context context;
    private App app;


    public SpellAdapter(Context context, List<Spell> data, App app) {
        this.context = context;
        this.app = app;
        originalData = new ArrayList<>(data);
        filteredData = new ArrayList<>(data);
        mInflater = LayoutInflater.from(context);
    }

    private boolean isBelongClass(String filterClass, Spell spell) {
        return spell.getClasses().contains(filterClass);
    }

    private boolean isBelongSchool(String schoolFilterText, Spell spell) {
        String school = spell.getSchool() == null ? spell.getText().substring(0, spell.getText().indexOf('\n')) : spell.getSchool();
        return school != null && school.toUpperCase().contains(schoolFilterText.toUpperCase());
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
            System.out.println("convertView == null, pos:" + position);
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.spell_list_view_item_layout, parent, false);
            viewHolder.textView = convertView.findViewById(R.id.textView2);
            viewHolder.toggleButton = convertView.findViewById(R.id.toggleButton);
            convertView.setTag(viewHolder);
            view = convertView;

        } else {
            System.out.println("convertView != null, pos:" + position);

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

                } else {
                    viewHolder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.start_off));
                }

                app.spellFavoriteService.setFavorite(spell, isChecked);

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

    private class ViewHolder {
        TextView textView;
        ToggleButton toggleButton;
    }

    private class ItemFilter extends Filter {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredData.clear();

            String[] filterString = constraint.toString().split(":");
            FilterResults results = new FilterResults();

            System.out.println("filters:" + constraint);


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
            }

            List<Spell> spells = originalData.stream().filter(this::filter).collect(Collectors.toList());

            results.values = spells;
            results.count = spells.size();

            return results;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private boolean filter(Spell spell) {
            return ("".equals(nameFilterText) || spell.getName().toLowerCase().contains(nameFilterText)) &&
                    (levelFilterText.equalsIgnoreCase("Все") || spell.getLevel().equals(levelFilterText)) &&
                    (schoolFilterText.equalsIgnoreCase("Все") || isBelongSchool(schoolFilterText, spell)) &&
                    (classFilterText.equalsIgnoreCase("Все") || isBelongClass(classFilterText, spell));
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

