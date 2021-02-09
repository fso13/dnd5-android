package ru.drudenko.dnd.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.Locale;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.magic.Spell;

public class SpellActivity extends AppCompatActivity {
    private Spell spell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_spell);
        Bundle bundle = getIntent().getExtras();

        spell = ((App) getApplication()).spells.get((Integer) bundle.get("SPELLID"));

        TextView level = findViewById(R.id.spell_level);
        level.setText(spell.getLevel(), TextView.BufferType.SPANNABLE);

        TextView school = findViewById(R.id.spell_school);
        school.setText(spell.getSchool(), TextView.BufferType.SPANNABLE);
        if (spell.getRitual()) {
            school.setText(MessageFormat.format("{0}, {1}", school.getText(), "Ритуал"), TextView.BufferType.SPANNABLE);
        }

        TextView castingTime = findViewById(R.id.spell_castingTime);
        castingTime.setText(spell.getCastingTime(), TextView.BufferType.SPANNABLE);

        TextView range = findViewById(R.id.spell_range);
        range.setText(spell.getRange(), TextView.BufferType.SPANNABLE);

        TextView components = findViewById(R.id.spell_components);
        components.setText(spell.getComponents(), TextView.BufferType.SPANNABLE);

        TextView duration = findViewById(R.id.spell_duration);
        duration.setText(spell.getDuration(), TextView.BufferType.SPANNABLE);

        TextView classes = findViewById(R.id.spell_classes);
        classes.setText(TextUtils.join(", ", spell.getClasses()), TextView.BufferType.SPANNABLE);

        TextView source = findViewById(R.id.spell_source);
        source.setText(spell.getSource(), TextView.BufferType.SPANNABLE);

        TextView descrioption = findViewById(R.id.spell_descrioption);
        descrioption.setText(spell.getText(), TextView.BufferType.SPANNABLE);

//        info.setText(String.format("Уровень: %s\n\n%s\n\nИсточник: %s\n\n", spell.getLevel(), spell.getDescription(), spell.getSource()), TextView.BufferType.SPANNABLE);
        actionBar.setTitle(spell.getName());

        try {
            JSONObject props = new JSONObject();

            props.put("Android Version", String.format(Locale.getDefault(), "Версия Android: %s (%d)", Build.VERSION.RELEASE, Build.VERSION.SDK_INT));
            props.put("monster", spell.getName());
            ((App) getApplication()).mixpanel.track("Spell activity", props);

        } catch (Exception ignored) {
        }
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
