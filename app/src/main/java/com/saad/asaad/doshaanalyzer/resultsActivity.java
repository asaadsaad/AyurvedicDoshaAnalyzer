package com.saad.asaad.doshaanalyzer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import java.util.Locale;

@SuppressLint("SetJavaScriptEnabled")
public class resultsActivity extends AppCompatActivity {
    Locale myLocale;
    WebView webView;
    private int vata;
    private int pitta;
    private int kapha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity);

        SharedPreferences prefs = getSharedPreferences("DoshaData", MODE_PRIVATE);

        vata = prefs.getInt("vata", 0);
        pitta = prefs.getInt("pitta", 0);
        kapha = prefs.getInt("kapha", 0);
        // if no saved results (first use), forward to Question
        if(vata == 0 && pitta == 0 && kapha == 0){
            Intent intent=new Intent(getApplicationContext(),Question.class);
            startActivity(intent);
            // finish and destroy current activity
            finish();
        }

        webView = (WebView)findViewById(R.id.webview);
        webView.addJavascriptInterface(new WebAppInterface(), "Android");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/chart.html");
    }

    public class WebAppInterface {

        @JavascriptInterface
        public int getVata() {
            return vata;
        }

        @JavascriptInterface
        public int getPitta() {
            return pitta;
        }

        @JavascriptInterface
        public int getKapha() {
            return kapha;
        }

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