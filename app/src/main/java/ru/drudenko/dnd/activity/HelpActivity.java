package ru.drudenko.dnd.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ru.drudenko.dnd.R;
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

        ArrayList<CustomItem> customItems = (ArrayList<CustomItem>) bundle.get("CustomItems");

        StringBuilder text = new StringBuilder();

        for (CustomItem item : customItems) {
            text.append("<b>").append(item.getName()).append("</b><br>");
            text.append(item.getText()).append("<br><br>");
        }

        WebView info = findViewById(R.id.textView);

        info.loadDataWithBaseURL(null, "    <style>\n" +
                "        table {\n" +
                "            border-collapse: collapse;\n" +
                "        }\n" +
                "\n" +
                "        table, th, td {\n" +
                "            border: 1px solid red;\n" +
                "        }\n" +
                "\n" +
                "        span.cls_012 {\n" +
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
