package com.inovex.bikroyik.UI.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.data.local.SharedPreference;

import java.util.Locale;

public class ChangeLanguageActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextView selectLanguage,toolbar_title,languageText;
    private RadioGroup radioGroup;
    private RadioButton radioButton,rbEnglish,rbBangla;
    private SharedPreference sharedPreference;
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_change_language);

        sharedPreference = SharedPreference.getInstance(getApplicationContext());
        selectLanguage = findViewById(R.id.changeLanguageId);
        languageText = findViewById(R.id.selectLanguageId);
        rbEnglish = findViewById(R.id.radioEnglish);
        rbBangla = findViewById(R.id.radioBangla);
        toolbar_title = findViewById(R.id.toolbar_title);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupId);
        if (sharedPreference.getLanguage().equals("Bangla"))
        {
            radioGroup.check(R.id.radioBangla);
            toolbar_title.setText("ভাষা পরিবর্তন");
            rbBangla.setText("বাংলা");
            languageText.setText("ভাষা নির্বাচন");

        }
        else{
            radioGroup.check(R.id.radioEnglish);
        }
        saveButton = findViewById(R.id.save_button);
        backButton = (ImageView) findViewById(R.id.btn_imageBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                if (!TextUtils.isEmpty(radioButton.getText().toString())
                        && radioButton.getText().toString().equals("English")){
                    sharedPreference.setLanguage("English");
                }else {
                    sharedPreference.setLanguage("Bangla");
                }

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });




        /*rgLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId)
                    {
                        case R.id.radioEnglish:
                            String language = "en";
                            setLocale(language);
                            break;
                        case R.id.radioBangla:
                             language = "bn";
                            setLocale(language);
                            break;

                    }
            }
        });*/



    }

  /*  private void setLocale(String language) {

        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(language);
        resources.updateConfiguration(configuration,metrics);
        onConfigurationChanged(configuration);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        selectLanguage.setText(R.string.find_language);
        rbEnglish.setText(R.string.english);
        rbBangla.setText(R.string.bangla);

    }*/


}