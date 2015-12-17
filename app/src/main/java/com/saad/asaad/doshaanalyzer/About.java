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
public class About extends AppCompatActivity {

    Locale myLocale;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        TextView t = (TextView) findViewById(R.id.about_text);
        t.setText(Html.fromHtml("<h3>What Does Your Dosha Say About You?</h3><p>Diagnose your Dosha to better understand your physical, mental and emotional make-up!</p>\n" +
                "<p>Understanding your own Dosha, or energy type, will make you more sensitive to your client Dosha, allowing you to be specific in the way you treat, speak and care for a client.</p>\n" +
                "<p>Dosha comes from the study of Ayurveda. Ayurvedic science shows us not only how to cure diseases but also how to prevent their occurrence. Ayurveda classifies people into three categories according to their constitution, or prakruti, which is a combination of the three doshas: vata, pitta and kapha. It is this combination of the three doshas that is responsible for our physical, mental and emotional make-up that makes each of us a unique individual.</p>\n" +
                "<ul>\n" +
                "<li>Vata: cold, dry, light and mobile by nature</li>\n" +
                "<li>Pitta: warm, oily and intense by nature</li>\n" +
                "<li>Kapha: damp and slow by nature</li>\n" +
                "</ul>\n" +
                "<p>The condition of our prakruti changes due to incorrect diet, emotional imbalances, too much stress, and incorrect exercise. With these disturbances the balance of the doshas in our body and mind system become imbalanced. This altered state of our health or imbalance in our body and mind is called our vikruti. The difference between the prakruti (balance) and vikruti (imbalance) gives a direction for healing. According to Ayurveda, before one can heal oneself and others, there must be a clear understanding of the three doshas.</p>"));


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
