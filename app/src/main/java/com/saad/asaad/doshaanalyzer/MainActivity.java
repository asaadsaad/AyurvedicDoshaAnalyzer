package com.saad.asaad.doshaanalyzer;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button startQuestionnaireButton;
    Button aboutButton;
    Button vpkButton;
    Button reportButton;
    TextView output;

    Locale myLocale;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startQuestionnaireButton = (Button) findViewById(R.id.start_questionnaire_button);
        startQuestionnaireButton.setOnClickListener(startQuestionnaireButtonClick);

        aboutButton = (Button) findViewById(R.id.about_button);
        aboutButton.setOnClickListener(aboutButtonClick);

        vpkButton = (Button) findViewById(R.id.vpk_button);
        vpkButton.setOnClickListener(vpkButtonClick);

        reportButton = (Button) findViewById(R.id.report_button);
        reportButton.setOnClickListener(reportButtonClick);
    }

    private View.OnClickListener startQuestionnaireButtonClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent=new Intent(getApplicationContext(),Question.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener aboutButtonClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent=new Intent(getApplicationContext(),About.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener vpkButtonClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent=new Intent(getApplicationContext(),VPK.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener reportButtonClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent=new Intent(getApplicationContext(),resultsActivity.class);
            startActivity(intent);
            //Toast.makeText(getApplicationContext(), "Coming soon!", Toast.LENGTH_SHORT).show();
        }
    };




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
