package ru.drudenko.dnd.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ru.drudenko.dnd.R;


public class MonsterActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();

        setContentView(R.layout.activity_monster);
        Bundle bundle = getIntent().getExtras();

        TextView info1 = findViewById(R.id.textView_info);
        info1.setText(bundle.getString("MONSTER_INFO1"));

        TextView info2 = findViewById(R.id.textView_info2);
        info2.setText(bundle.getString("MONSTER_INFO2"));

        TextView text = findViewById(R.id.textView_text);
        text.setText(bundle.getString("MONSTER_TEXT"));

        actionBar.setTitle(bundle.getString("MONSTER_NAME"));

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
