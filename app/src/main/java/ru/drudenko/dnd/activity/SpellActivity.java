package ru.drudenko.dnd.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.magic.Spell;

public class SpellActivity extends AppCompatActivity {
    private Spell spell;

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
        info.setText(String.format("Уровень: %s\n\n%s\n\nИсточник: %s\n\n", spell.getLevel(), spell.getDescription(), spell.getSource()), TextView.BufferType.SPANNABLE);
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

            ((App) getApplication()).spellFavoriteService.setFavorite(spell, !spell.isFavorite());
            item.setIcon(spell.isFavorite() ? R.drawable.stars_on : R.drawable.stars_off);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((App) getApplication()).spellFavoriteService.setFavorite(spell, spell.isFavorite());

    }
}
