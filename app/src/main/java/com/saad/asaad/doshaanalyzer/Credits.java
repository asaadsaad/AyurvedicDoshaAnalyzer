package com.saad.asaad.doshaanalyzer;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by asaad on 12/8/2015.
 */
public class Credits extends AppCompatActivity {

    Locale myLocale;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits);

        TextView t = (TextView) findViewById(R.id.credits_text);
        t.setText(Html.fromHtml("<h3>Images Credit goes to:</h3>" +
                "<p>https://bucatarieindiana.files.wordpress.com/   </p>\n" +
                "<p>https://cuadernodeayurveda.files.wordpress.com</p>\n" +
                "<p>http://www.ayurvedicliving.org</p>\n" +
                "<p>http://www.centerpoint.coop</p>\n" +
                "<p>Thank you ;)</p>"));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_credits:
                //Toast.makeText(this, "Credits selected", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),Credits.class);
                startActivity(intent);
                break;
            case R.id.action_arabic:
                setLocale("ar");
                break;

            case R.id.action_english:
                setLocale("en");
                break;

            default:
                break;
        }

        return true;
    }

    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }
}
