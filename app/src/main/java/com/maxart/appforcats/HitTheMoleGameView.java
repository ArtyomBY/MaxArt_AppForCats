package com.maxart.appforcats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ViewConstructor")
public class HitTheMoleGameView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;
    private final int speed, volume;

    public HitTheMoleGameView(Context context, int speed, int volume) {
        super(context);
        this.speed = speed;
        this.volume = volume;
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        drawThread = new DrawThread(getHolder(), speed, volume);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        exit();
    }

    public void exit() {
        drawThread.running = false;
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException ignored) { }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            drawThread.checkClick((int)event.getX(), (int) event.getY());
            return true;
        }

        return false;
    }

    private class DrawThread extends Thread {

        private final SurfaceHolder surfaceHolder;

        private final Paint paint = new Paint();
        private Bitmap background, mole;
        private final int moleWidth, moleHeight;

        int moleX, moleY;
        int speed;
        private static final double SPEED_COEFFICIENT = 1000;
        int timer = 0;
        private final double timerMax;
        private final List<Hole> holes = new ArrayList<>();

        private final MediaPlayer soundPlayer = MediaPlayer.create(getContext(), R.raw.squeak);

        private volatile boolean running = true;

        public DrawThread(SurfaceHolder surfaceHolder, int speed, int volume) {
            this.surfaceHolder = surfaceHolder;
            this.speed = speed;

            int canvasWidth = getWidth();
            int canvasHeight = getHeight();

            mole = BitmapFactory.decodeResource(getResources(), R.drawable.mole_icon);
            mole = Bitmap.createScaledBitmap(mole, 200, 150, false);
            moleWidth = mole.getWidth();
            moleHeight = mole.getHeight();

            background = BitmapFactory.decodeResource(getResources(), R.drawable.game_forest_background);
            background = CatchTheMouseGameView.scaleCenterCrop(background, canvasWidth, canvasHeight);

            holes.add(new Hole(canvasWidth / 2, canvasHeight / 2));
            holes.add(new Hole(canvasWidth / 4, canvasHeight / 4));
            holes.add(new Hole(canvasWidth / 4, canvasHeight * 3 / 4));
            holes.add(new Hole((int)(canvasWidth / 2 * 1.17), (int)(canvasHeight / 2.5)));

            timerMax = SPEED_COEFFICIENT / speed;

            soundPlayer.setVolume(volume, volume);
        }

        public void checkClick(int x, int y) {
            int moleCenterX = moleX + moleWidth / 2;
            int moleCenterY = moleY + moleHeight / 2;
            int r = Math.min(moleWidth, moleHeight) / 2;
            if (Math.pow(x - moleCenterX, 2) + Math.pow(y - moleCenterY, 2) < r * r) {
                timer = (int)timerMax;
                soundPlayer.start();
            }
        }

        @Override
        public void run() {
            moveMole();

            while (running) {
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    try {
                        if (timer >= timerMax) {
                            moveMole();
                            timer = 0;
                        }
                        else {
                            timer++;
                        }

                        draw(canvas);
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }

        void moveMole() {
            int newX, newY;
            do {
                int n = (int) (Math.random() * holes.size());
                newX = holes.get(n).x;
                newY = holes.get(n).y;
            } while (newX == moleX && newY == moleY);
            moleX = newX;
            moleY = newY;
        }

        private void draw(Canvas canvas) {
            canvas.drawBitmap(background, 0, 0, null);

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#D5CD3E"));

            for (Hole hole : holes) {
                hole.draw(canvas);
            }

            canvas.drawBitmap(mole, moleX, moleY, null);
        }

        private class Hole {
            static final int WIDTH = 50, HEIGHT = 150;
            int x, y;

            Hole(int x, int y) {
                this.x = x;
                this.y = y;
            }

            void draw(Canvas canvas) {
                RectF rect = new RectF(x, y, x + WIDTH, y + HEIGHT);
                canvas.drawOval(rect, paint);
            }
        }
    }
}
