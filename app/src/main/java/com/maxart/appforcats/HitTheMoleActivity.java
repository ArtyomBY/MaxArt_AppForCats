package com.maxart.appforcats;

import android.os.Bundle;
import android.view.Window;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HitTheMoleActivity extends AppCompatActivity {

    static final int MAX_SPEED = 50;
    HitTheMoleGameView gameView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_hit_the_mole);

        SeekBar speedBar = findViewById(R.id.mole_speed);
        SeekBar volumeBar = findViewById(R.id.volume);
        speedBar.setMax(MAX_SPEED);

        findViewById(R.id.start_button).setOnClickListener(v -> {
            setContentView(gameView = new HitTheMoleGameView(
                    this,
                    Math.max(1, speedBar.getProgress()),
                    volumeBar.getProgress()
            ));
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            startGame();
        });
    }

    private void startGame() {

    }

    @Override
    public void onBackPressed() {
        gameView.exit();
        super.onBackPressed();
    }
}
