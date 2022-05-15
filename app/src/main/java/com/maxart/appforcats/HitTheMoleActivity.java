package com.maxart.appforcats;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

public class HitTheMoleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_hit_the_mole);


        findViewById(R.id.start_button).setOnClickListener(v -> {
            setContentView(new HitTheMoleGameView(this));
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            startGame();
        });
    }

    private void startGame() {

    }
}