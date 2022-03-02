package com.mkrlabs.programmingheroquiz.ui.main_menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.mkrlabs.customstatusbar.CustomStatusBar;
import com.mkrlabs.programmingheroquiz.R;
import com.mkrlabs.programmingheroquiz.repository.shred_pref.SharedPref;
import com.mkrlabs.programmingheroquiz.ui.quiz.QuizActivity;

public class MainMenuActivity extends AppCompatActivity {

    private MaterialButton startButton;
    private TextView highScorePointTV;
    private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomStatusBar.transparentStatusBarWithIcon(this,false);
        setContentView(R.layout.activity_main_menu);
        initialize();
        sharedPref = new SharedPref(this);

        highScorePointTV.setText(sharedPref.getHighScore()+" Point");
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, QuizActivity.class);
                startActivity(intent);

            }
        });
    }

    private void initialize() {
        highScorePointTV = findViewById(R.id.highScorePointTV);
        startButton = findViewById(R.id.startButton);


    }
}