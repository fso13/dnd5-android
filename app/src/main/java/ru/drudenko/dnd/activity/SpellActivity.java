package ru.drudenko.dnd.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import javax.inject.Inject;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.magic.Spell;

public class SpellActivity extends AppCompatActivity {
    private Spell spell;
    @Inject
    List<Spell> spells;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getComponent().inject(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_spell);
        Bundle bundle = getIntent().getExtras();

        spell = (Spell) bundle.get("SPELL");

        TextView info = findViewById(R.id.textView_spell_info);
        info.setText(spell.getText() + "\n\n" + "Источник: " + spell.getSource() + "\n\n", TextView.BufferType.SPANNABLE);
        actionBar.setTitle(spell.getName());

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favorite, menu);

        menu.getItem(0).setIcon(spell.isFavorite() ? R.drawable.stars_on : R.drawable.stars_off);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        if (item.getItemId() == R.id.action_favorite) {

            spell.setFavorite(!spell.isFavorite());
            item.setIcon(spell.isFavorite() ? R.drawable.stars_on : R.drawable.stars_off);
            final String key = spell.getName().replace(" ", "_");

            SharedPreferences preferences = getApplicationContext().getSharedPreferences("application_preferences", Context.MODE_PRIVATE);
            preferences.edit().remove(key).apply();
            preferences.edit().putBoolean(key, spell.isFavorite()).apply();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        final String key = spell.getName().replace(" ", "_");
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("application_preferences", Context.MODE_PRIVATE);
        preferences.edit().remove(key).apply();
        preferences.edit().putBoolean(key, spell.isFavorite()).apply();
    }
}
