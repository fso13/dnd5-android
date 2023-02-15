package ru.drudenko.dnd.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.di.App;
import ru.drudenko.dnd.model.CustomItem;

public class HelpActivity extends AppCompatActivity {

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_singl);
        Bundle bundle = getIntent().getExtras();

        Object o = bundle.get("CustomItems");
        String title = (String) bundle.get("title");
        actionBar.setTitle(title);
        StringBuilder text = new StringBuilder();

        if (o instanceof ArrayList<?>) {
            for (Object obj : ((ArrayList<?>) o)) {
                if (obj instanceof CustomItem) {
                    CustomItem item = (CustomItem) obj;
                    text.append("<b>").append(item.getName()).append("</b><br>");
                    text.append(item.getText()).append("<br><br>");
                }
            }
        }


        try {
            JSONObject props = new JSONObject();

            props.put("Android Version", String.format(Locale.getDefault(), "Версия Android: %s (%d)", Build.VERSION.RELEASE, Build.VERSION.SDK_INT));
            ((App) getApplication()).mixpanel.track("Help activity", props);

        } catch (Exception ignored) {
        }
        WebView info = findViewById(R.id.textView);
        info.getSettings().setDefaultFontSize(16);
        info.loadDataWithBaseURL(null, "    <style>\n" +
                "        table {\n" +
                "            border-collapse: collapse;\n" +
                "        }\n" +
                "\n" +
                "        table, th, td {\n" +
                "            border: 1px solid red;\n" +
                "        }\n" +
                "\n" +
                "        span.cls_012, h3, h2, h4 {\n" +
                "            font-family: Times, serif;\n" +
                "            color: red;\n" +
                "            font-weight: bold;\n" +
                "            font-style: normal;\n" +
                "            text-decoration: none\n" +
                "        }\n" +
                "\n" +
                "        div.cls_012 {\n" +
                "            font-family: Times, serif;\n" +
                "            color: rgb(255, 255, 255);\n" +
                "            font-weight: bold;\n" +
                "            font-style: normal;\n" +
                "            text-decoration: none\n" +
                "        }\n" +
                "\n" +
                "        span.cls_013 {\n" +
                "            font-family: Times, serif;\n" +
                "            color: rgb(255, 255, 255);\n" +
                "            font-weight: normal;\n" +
                "            font-style: normal;\n" +
                "            text-decoration: none\n" +
                "        }\n" +
                "\n" +
                "        div.cls_013 {\n" +
                "            font-family: Times, serif;\n" +
                "            color: rgb(255, 255, 255);\n" +
                "            font-weight: normal;\n" +
                "            font-style: normal;\n" +
                "            text-decoration: none\n" +
                "        }\n" +
                "    </style><body style=\"background-color:black; color: white\">" + text + "<br><br></body>", "text/html", "utf-8", null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
