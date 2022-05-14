package com.maxart.appforcats;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicBoolean;

public class CatchTheMouseActivity extends AppCompatActivity {

    Bitmap backgroundImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_catch_the_mouse);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.cheese_background);
        View treeRoot = getWindow().getDecorView().getRootView();
        AtomicBoolean hasBeenDrawn = new AtomicBoolean(false);
        treeRoot.getViewTreeObserver().addOnGlobalLayoutListener(
                () -> {
                    if (hasBeenDrawn.get())
                        return;
                    hasBeenDrawn.set(true);
                    View rootView = findViewById(R.id.root);
                    Bitmap scaledBackground = CatchTheMouseGameView.scaleCenterCrop(
                            backgroundImage,
                            rootView.getWidth(),
                            rootView.getHeight()
                    );
                    Drawable background = new BitmapDrawable(getResources(), scaledBackground);
                    rootView.setBackground(background);
                }
        );

        findViewById(R.id.start_button).setOnClickListener(v -> {
            setContentView(new CatchTheMouseGameView(this));
            startGame();
        });
    }

    private void startGame() {

    }
}
