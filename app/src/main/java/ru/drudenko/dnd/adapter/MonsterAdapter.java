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

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.monster.Monster;

public class MonsterAdapter extends BaseAdapter implements Filterable {
    private final LayoutInflater mInflater;
    private final ItemFilter mFilter = new ItemFilter();
    private final Context context;
    private final App app;
    public final List<Monster> list;
    public List<Monster> filteredData;
    private String biomFilterText = "Все";
    private String levelFilterText = "Все";
    private String nameFilterText = "";
    private CharSequence constraintAdapter;

    public MonsterAdapter(Context context, List<Monster> data, App app) {
        this.context = context;
        this.list = data;
        this.filteredData = new ArrayList<>(data);
        this.app = app;
        mInflater = LayoutInflater.from(context);
    }

    public CharSequence getConstraintAdapter() {
        return constraintAdapter;
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

        final Monster monster = filteredData.get(position);

        viewHolder.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        viewHolder.textView.setTypeface(null, Typeface.BOLD);
        viewHolder.textView.setTextColor(Color.WHITE);
        viewHolder.textView.setText(monster.getName());


        viewHolder.toggleButton.setChecked(monster.isFavorite());
        viewHolder.toggleButton.setTextOff("");
        viewHolder.toggleButton.setTextOn("");

        viewHolder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context, monster.isFavorite() ? R.drawable.start_on : R.drawable.start_off));
        viewHolder.toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) {

                if (isChecked) {
                    viewHolder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.start_on));
                } else {
                    viewHolder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.start_off));
                }

                app.monsterFavoriteService.setFavorite(monster, isChecked);
                getFilter().filter(constraintAdapter);
                notifyDataSetChanged();

            }
        });

        return view;
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }

    public int getCount() {
        return filteredData.size();
    }

    public Monster getItem(int position) {
        return filteredData.get(position);
    }

    public long getItemId(int position) {
        return position;
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
            constraintAdapter = constraint;
            String[] filterString = constraint.toString().split(":");

            FilterResults results = new FilterResults();


            int count = list.size();
            final ArrayList<Monster> nlist = new ArrayList<>(count);


            if (filterString.length == 1) {
                nameFilterText = filterString[0].toLowerCase();
            } else if (filterString.length == 2) {

                if (filterString[0].equals("exp")) {
                    levelFilterText = filterString[1];
                }

                if (filterString[0].equals("biom")) {
                    biomFilterText = filterString[1];
                }

            }

            for (int i = 0; i < count; i++) {
                Monster monster = list.get(i);
                if (filter(monster)) {
                    nlist.add(monster);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private boolean filter(Monster monster) {
            return ("".equals(nameFilterText) || monster.getName().toLowerCase().contains(nameFilterText)) &&
                    (levelFilterText.equals("Все") || monster.getCr().equals(levelFilterText)) &&
                    (biomFilterText.equals("Все") || monster.getBioms().contains(biomFilterText));
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values == null) {
                filteredData = new ArrayList<>();
            } else {
                filteredData = (ArrayList<Monster>) results.values;
            }
            notifyDataSetChanged();
        }

    }
}