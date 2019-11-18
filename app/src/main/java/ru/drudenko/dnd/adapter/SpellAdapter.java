package ru.drudenko.dnd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.model.magic.ClassInfo;
import ru.drudenko.dnd.model.magic.Clazz;
import ru.drudenko.dnd.model.magic.Spell;

public class SpellAdapter extends BaseExpandableListAdapter implements Filterable {
    private Map<Integer, ArrayList<Spell>> originalData = new TreeMap<Integer, ArrayList<Spell>>() {
    };
    private Map<Integer, ArrayList<Spell>> filteredData = new TreeMap<>();
    private Map<Clazz, ClassInfo> map;
    private LayoutInflater mInflater;
    private ItemFilter mFilter = new ItemFilter();
    private String classFilterText = "Все";
    private String levelFilterText = "Все";
    private String nameFilterText = "";
    private Context context;

    public SpellAdapter(Context context, List<Spell> data, Map<Clazz, ClassInfo> map) {
        this.context = context;
        originalData.put(0, new ArrayList<>());
        originalData.put(1, new ArrayList<>());
        originalData.put(2, new ArrayList<>());
        originalData.put(3, new ArrayList<>());
        originalData.put(4, new ArrayList<>());
        originalData.put(5, new ArrayList<>());
        originalData.put(6, new ArrayList<>());
        originalData.put(7, new ArrayList<>());
        originalData.put(8, new ArrayList<>());
        originalData.put(9, new ArrayList<>());

        filteredData.put(0, new ArrayList<>());
        filteredData.put(1, new ArrayList<>());
        filteredData.put(2, new ArrayList<>());
        filteredData.put(3, new ArrayList<>());
        filteredData.put(4, new ArrayList<>());
        filteredData.put(5, new ArrayList<>());
        filteredData.put(6, new ArrayList<>());
        filteredData.put(7, new ArrayList<>());
        filteredData.put(8, new ArrayList<>());
        filteredData.put(9, new ArrayList<>());

        for (Spell spell : data) {
            originalData.get(Integer.parseInt(spell.getEn().getLevel())).add(spell);
            filteredData.get(Integer.parseInt(spell.getEn().getLevel())).add(spell);
        }

        this.map = map;
        mInflater = LayoutInflater.from(context);
    }

    private boolean isBelong(String filterClass, Spell spell) {
        return map.get(Clazz.fromRu(filterClass)).getSpells().contains(spell.getEn().getName());
    }

    @Override
    public int getGroupCount() {
        return originalData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return filteredData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return filteredData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return filteredData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spell_list_view_item_layout_level, null);
        }

        if (isExpanded) {
            //Изменяем что-нибудь, если текущая Group раскрыта
        } else {
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        TextView textGroup = convertView.findViewById(R.id.textView2);
        textGroup.setText("Уровень: " + groupPosition);
        textGroup.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textGroup.setTypeface(null, Typeface.BOLD);
        textGroup.setTextColor(Color.WHITE);


        TextView textGroup2 = convertView.findViewById(R.id.textView3);
        textGroup2.setText("Кол-во: " + getChildrenCount(groupPosition));
        textGroup2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textGroup2.setTypeface(null, Typeface.BOLD);
        textGroup2.setTextColor(Color.WHITE);

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
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

        final Spell spell = filteredData.get(groupPosition).get(childPosition);

        viewHolder.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        viewHolder.textView.setTypeface(null, Typeface.BOLD);
        viewHolder.textView.setTextColor(Color.WHITE);
        viewHolder.textView.setText(spell.getRu().getName());


        viewHolder.toggleButton.setChecked(spell.isFavorite());
        viewHolder.toggleButton.setTextOff("");
        viewHolder.toggleButton.setTextOn("");

        viewHolder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context, spell.isFavorite() ? R.drawable.start_on : R.drawable.start_off));
        viewHolder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        viewHolder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.start_on));
                        spell.setFavorite(true);

                    } else {
                        viewHolder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.start_off));
                        spell.setFavorite(false);
                    }

                }
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public int getCount() {
        return filteredData.size();
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ViewHolder {
        TextView textView;
        ToggleButton toggleButton;
    }

    private class ItemFilter extends Filter {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String[] filterString = constraint.toString().split(":");
            FilterResults results = new FilterResults();
            final Map<Integer, ArrayList<Spell>> list = originalData;
            int count = list.size();
            final Map<Integer, ArrayList<Spell>> nlist = new TreeMap<>();
            nlist.put(0, new ArrayList<>());
            nlist.put(1, new ArrayList<>());
            nlist.put(2, new ArrayList<>());
            nlist.put(3, new ArrayList<>());
            nlist.put(4, new ArrayList<>());
            nlist.put(5, new ArrayList<>());
            nlist.put(6, new ArrayList<>());
            nlist.put(7, new ArrayList<>());
            nlist.put(8, new ArrayList<>());
            nlist.put(9, new ArrayList<>());

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

            for (int i = 0; i < count; i++) {
                for (Spell spell : list.get(i)) {

                    if (filter(spell)) {
                        nlist.get(Integer.parseInt(spell.getEn().getLevel())).add(spell);
                    }
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
            filteredData = (Map<Integer, ArrayList<Spell>>) results.values;
            notifyDataSetChanged();
        }

    }


}

//in your Activity or Fragment where of Adapter is instantiated :
