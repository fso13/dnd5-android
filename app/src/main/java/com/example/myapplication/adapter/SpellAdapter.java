package com.example.myapplication.adapter;

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

import androidx.annotation.RequiresApi;

import com.example.myapplication.model.ClassInfo;
import com.example.myapplication.model.Clazz;
import com.example.myapplication.model.Spell;

import java.util.ArrayList;
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

    public SpellAdapter(Context context, List<Spell> data, Map<Clazz, ClassInfo> map) {
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

        TextView textView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            textView = new TextView(mInflater.getContext());
            textView.setPadding(8, 8, 8, 8);
        } else {
            textView = (TextView) convertView;
        }

        Spell spell = filteredData.get(position);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextColor(Color.WHITE);
        textView.setText(spell.getRu().getName());


        return textView;
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
