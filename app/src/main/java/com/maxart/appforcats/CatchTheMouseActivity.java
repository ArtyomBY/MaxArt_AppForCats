package com.maxart.appforcats;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CatchTheMouseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_catch_the_mouse);

//        Drawable background = BitmapDrawable.createFromResourceStream()
//        findViewById(android.R.id.content).getRootView().setBackground();

        findViewById(R.id.start_button).setOnClickListener(v -> {
            setContentView(new CatchTheMouseGameView(this));
            startGame();
        });
    }

    private void startGame() {

    }
}
