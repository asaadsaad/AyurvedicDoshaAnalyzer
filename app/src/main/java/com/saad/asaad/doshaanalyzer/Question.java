package com.saad.asaad.doshaanalyzer;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Question extends AppCompatActivity {

    Locale myLocale;

    private String currentQuestionID;
    private int vata;
    private int pitta;
    private int kapha;

    ProgressBar progressbar;
    TextView progressText;
    TextView question;
    RadioGroup questionsRadioGroup;
    RadioButton radio1;
    RadioButton radio2;
    RadioButton radio3;
    Button submit_button;
    TextView error;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

        SharedPreferences prefs = getSharedPreferences("DoshaData", MODE_PRIVATE);
        currentQuestionID = prefs.getString("CurrentQuestionID", "1");
        vata = prefs.getInt("vata", 0);
        pitta = prefs.getInt("pitta", 0);
        kapha = prefs.getInt("kapha", 0);

        String id = "question" + currentQuestionID;
        int i = getResources().getIdentifier(id, "array", getPackageName());
        String[] currentQuestion = getResources().getStringArray(i);

        question = (TextView) findViewById(R.id.question);
        question.setText(currentQuestion[1]);

        questionsRadioGroup = (RadioGroup) findViewById(R.id.QuestionsRadioGroup);
        questionsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio1) {
                    Toast.makeText(getApplicationContext(), R.string.vata, Toast.LENGTH_SHORT).show();
                } else if(checkedId == R.id.radio2) {
                    Toast.makeText(getApplicationContext(), R.string.pitta, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.kapha, Toast.LENGTH_SHORT).show();
                }
                error.setText("");
            }
        });


        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio1.setText(currentQuestion[2]);

        radio2 = (RadioButton) findViewById(R.id.radio2);
        radio2.setText(currentQuestion[3]);

        radio3 = (RadioButton) findViewById(R.id.radio3);
        radio3.setText(currentQuestion[4]);

        error = (TextView) findViewById(R.id.error);

        submit_button = (Button)findViewById(R.id.submit_button);
        submit_button.setOnClickListener(submitButtonClick);
        updateProgress();
    }

    protected void updateProgress(){
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        int numberOfQuestions = Integer.valueOf(getResources().getString(R.string.number_of_questions));
        progressbar.setMax(100);
        int progressPercentage = Integer.valueOf((Integer.valueOf(currentQuestionID) * 100) / numberOfQuestions);
        progressbar.setProgress(progressPercentage);
        progressText = (TextView) findViewById(R.id.progresstext);
        String finalText = getResources().getString(R.string.question_label_progress) + " " + currentQuestionID + "/" + String.valueOf(numberOfQuestions);
        progressText.setText(finalText);
    }

    protected void nextQuestion(){
        error = (TextView) findViewById(R.id.error);
        error.setText("");

        int numberOfQuestions = Integer.valueOf(getResources().getString(R.string.number_of_questions));
        int Qid = Integer.valueOf(currentQuestionID);
        //Log.e("numberOfQuestions", String.valueOf(numberOfQuestions));
        //Log.e("currentQuestionID", String.valueOf(Qid));

        if(Qid < numberOfQuestions) {
            currentQuestionID = String.valueOf(Qid + 1);
            String id = "question" + currentQuestionID;
            int i = getResources().getIdentifier(id, "array", getPackageName());
            String[] currentQuestion = getResources().getStringArray(i);

            question = (TextView) findViewById(R.id.question);
            question.setText(currentQuestion[1]);

            questionsRadioGroup = (RadioGroup) findViewById(R.id.QuestionsRadioGroup);
            questionsRadioGroup.clearCheck();

            radio1 = (RadioButton) findViewById(R.id.radio1);
            radio1.setChecked(false);
            radio1.setText(currentQuestion[2]);

            radio2 = (RadioButton) findViewById(R.id.radio2);
            radio2.setChecked(false);
            radio2.setText(currentQuestion[3]);

            radio3 = (RadioButton) findViewById(R.id.radio3);
            radio3.setChecked(false);
            radio3.setText(currentQuestion[4]);

            // submit_button = (Button) findViewById(R.id.submit_button);
            // submit_button.setOnClickListener(submitButtonClick);
            updateProgress();
            error.setText("Question" + Qid +"/" + numberOfQuestions  +" Vata: " + vata + " - Pitta: "+ pitta + " - Kapha: " + kapha);

        }else{
            // save results in preferences
            SharedPreferences prefs = getSharedPreferences("DoshaData", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("vata", vata);
            editor.putInt("pitta", pitta);
            editor.putInt("kapha", kapha);
            editor.commit();
            // show results
            showResults();
        }
    }

    private void showResults(){
        Intent intent=new Intent(getApplicationContext(),resultsActivity.class);
        startActivity(intent);
        // finish and destroy current activity
        finish();
    }

    private View.OnClickListener submitButtonClick = new View.OnClickListener() {
        public void onClick(View v) {
            int selectedId = questionsRadioGroup.getCheckedRadioButtonId();
            if (selectedId == radio1.getId()) {
                vata++;
                //output.setText("You chose first option");
                //Toast.makeText(getApplicationContext(), R.string.vata, Toast.LENGTH_SHORT).show();
                nextQuestion();
            } else if (selectedId == radio2.getId()) {
                pitta++;
                //output.setText("You chose second option");
                //Toast.makeText(getApplicationContext(), R.string.pitta, Toast.LENGTH_SHORT).show();
                nextQuestion();
            } else if (selectedId == radio3.getId()){
                kapha++;
                //output.setText("You chose third option");
                //Toast.makeText(getApplicationContext(), R.string.kapha, Toast.LENGTH_SHORT).show();
                nextQuestion();
            }else{
                error.setText(R.string.question_mandatory_error);
            }

        }
    };

    @Override
    protected void onPause()
    {
        SharedPreferences prefs = getSharedPreferences("DoshaData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("vata", vata);
        editor.putInt("pitta", pitta);
        editor.putInt("kapha", kapha);
        editor.commit();

        super.onPause();
    }
    @Override
    protected void onResume()
    {
        SharedPreferences prefs = getSharedPreferences("DoshaData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("vata", vata);
        editor.putInt("pitta", pitta);
        editor.putInt("kapha", kapha);
        editor.commit();
        super.onResume();
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
