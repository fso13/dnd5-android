package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.model.Spell;

import java.util.ArrayList;
import java.util.List;

public class SpellAdapter extends ArrayAdapter<Spell> {

    private final Object mLock = new Object();
    private ArrayList<Spell> mOriginalValues;
    private SpellFilter mFilter = new SpellFilter();

    public SpellAdapter(@NonNull Context context, int resource, @NonNull List<Spell> objects) {
        super(context, resource, objects);
        mFilter.mObjects = objects;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        TextView textView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            textView = new TextView(getContext());
            textView.setPadding(8, 8, 8, 8);
        } else {
            textView = (TextView) convertView;
        }

        Spell spell = getItem(position);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(spell.getRu().getName());


        return textView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return mFilter;
    }

    class SpellFilter extends Filter {
        private List<Spell> mObjects;


        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            final FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<>(mObjects);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                final ArrayList<Spell> list;
                synchronized (mLock) {
                    list = new ArrayList<>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString;
                String propertiesFilter = null;
                String[] arg = prefix.toString().split(":");
                if (arg.length == 2) {
                    propertiesFilter = arg[0];
                    prefixString = arg[1];
                } else {
                    prefixString = prefix.toString().toLowerCase();
                }
                final ArrayList<Spell> values;
                synchronized (mLock) {
                    values = new ArrayList<>(mOriginalValues);
                }

                final int count = values.size();
                final ArrayList<Spell> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final Spell value = values.get(i);

                    String valueText = null;
                    if (propertiesFilter == null) {
                        valueText = value.toString().toLowerCase();
                    } else {
                        if (propertiesFilter.equals("level")) {
                            try {

                                valueText = value.getEn().getLevel().toLowerCase();
                            } catch (Exception e) {
                                valueText = " ";
                                System.out.println(e);
                            }
                        }
                    }

                    // First match against the whole, non-splitted value
                    if (valueText.startsWith(prefixString)) {
                        newValues.add(value);
                    } else {
                        final String[] words = valueText.split(" ");
                        for (String word : words) {
                            if (word.startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mObjects = (List<Spell>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }


}