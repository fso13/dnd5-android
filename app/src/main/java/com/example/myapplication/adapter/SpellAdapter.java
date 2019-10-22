package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.myapplication.model.Spell;

import java.util.ArrayList;
import java.util.List;

public class SpellAdapter extends BaseAdapter implements Filterable {
    private List<Spell> originalData = null;
    private List<Spell> filteredData = null;
    private LayoutInflater mInflater;
    private ItemFilter mFilter = new ItemFilter();

    public SpellAdapter(Context context, List<Spell> data) {
        this.filteredData = data;
        this.originalData = data;
        mInflater = LayoutInflater.from(context);
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
                    if (spell.getRu().getName().toLowerCase().contains(filterString[0].toLowerCase())) {
                        nlist.add(spell);
                    }
                } else if (filterString.length == 2) {

                    if (filterString[0].equals("level") && (filterString[1].equals("Все") || spell.getEn().getLevel().equals(filterString[1]))) {
                        nlist.add(spell);
                    }
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
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
