package com.maxart.appforcats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

@SuppressLint("ViewConstructor")
public class CatchTheMouseGameView extends SurfaceView implements SurfaceHolder.Callback {

    int mouseSpeed;
    float soundVolume;

    GameThread gameThread;

    public CatchTheMouseGameView(Context context, int mouseSpeed, float soundVolume) {
        super(context);

        this.mouseSpeed = mouseSpeed;
        this.soundVolume = soundVolume;

        getHolder().addCallback(this);
    }

    static Bitmap scaleCenterCrop(Bitmap source, int newWidth, int newHeight) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        // Compute the scaling factors to fit the new height and width, respectively.
        // To cover the final image, the final scaling will be the bigger
        // of these two.
        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.max(xScale, yScale);

        // Now get the size of the source bitmap when scaled
        int scaledWidth = (int)(scale * sourceWidth);
        int scaledHeight = (int)(scale * sourceHeight);
        Bitmap scaled = Bitmap.createScaledBitmap(source, scaledWidth, scaledHeight, true);

        // Let's find out the upper left coordinates if the scaled bitmap
        // should be centered in the new size give by the parameters
        int left = (newWidth - scaledWidth) / 2;
        int top = (newHeight - scaledHeight) / 2;
        return Bitmap.createBitmap(scaled, -left, -top, newWidth, newHeight);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameThread = new GameThread(holder, mouseSpeed, soundVolume);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        gameThread.running = false;
        boolean retry = true;
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException ignored) { }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            performContextClick(event.getX(), event.getY());
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean performContextClick(float x, float y) {
        gameThread.click((int)x, (int)y);
        return super.performContextClick(x, y);
    }

    class GameThread extends Thread {
        private static final double SPEED_COEFFICIENT = 1;
        private static final double MOUSE_SIZE_COEFFICIENT = 0.35;

        int startX, startY;
        int targetX, targetY;
        double progress;
        int speed;

        SurfaceHolder surfaceHolder;
        Bitmap mouse, background;
        int mouseWidth, mouseHeight;
        MediaPlayer soundPlayer;

        volatile boolean running = true;

        public GameThread(SurfaceHolder surfaceHolder, int mouseSpeed, float volume) {
            this.surfaceHolder = surfaceHolder;

            mouse = BitmapFactory.decodeResource(getResources(), R.drawable.mouse);
            mouse = Bitmap.createScaledBitmap(
                    mouse,
                    (int)(mouse.getWidth() * MOUSE_SIZE_COEFFICIENT),
                    (int)(mouse.getHeight() * MOUSE_SIZE_COEFFICIENT),
                    true
            );
            mouseWidth = (int)(mouse.getWidth() * 0.5);
            mouseHeight = (int)(mouse.getHeight() * 0.5);
            background = BitmapFactory.decodeResource(getResources(), R.drawable.cheese_background);
            background = scaleCenterCrop(background, getWidth(), getHeight());

            this.speed = mouseSpeed;
            startX = targetX = surfaceHolder.getSurfaceFrame().width() / 2;
            startY = targetY = surfaceHolder.getSurfaceFrame().height() / 2;
            progress = 1;

            soundPlayer = MediaPlayer.create(getContext(), R.raw.squeak);
            soundPlayer.setVolume(volume, volume);
        }

        @Override
        public void run() {
            while (running) {
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    try {
                        if (progress >= calculateLength()) {
                            startX = targetX;
                            startY = targetY;
                            targetX = (int)(Math.random() * canvas.getWidth());
                            targetY =  (int)(Math.random() * canvas.getHeight());
                            progress = 0;
                        }
                        else {
                            progress += speed * SPEED_COEFFICIENT;
                        }

                        canvas.drawBitmap(background, 0, 0, null);

                        float angle = calculateAngle();
                        if (targetY - startY < 0) {
                            angle = (Math.abs(angle) + 180) * Math.signum(angle);
                        }
                        canvas.rotate(-angle, startX, startY);
                        float length = startY + (float)progress;
                        canvas.drawBitmap(
                                mouse,
                                startX - (float)mouse.getWidth() / 2,
                                length - (float)mouse.getHeight() / 2,
                                null
                        );
                        canvas.rotate(angle, startX, startY);
                    }
                    finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }

        double calculateLength() {
            return Math.sqrt(Math.pow(targetX - startX, 2) + Math.pow(targetY - startY, 2));
        }

        float calculateAngle() {
            double dx = targetX - startX;
            int dy = targetY - startY;
            return (float)(Math.atan(dx / dy) / Math.PI * 180);
        }

        void click(int x, int y) {
            int dx = targetX - startX;
            int dy = targetY - startY;
            int mouseX = startX + (int)(progress / calculateLength() * dx);
            int mouseY = startY + (int)(progress / calculateLength() * dy);
            int r = Math.min(mouseWidth, mouseHeight);
            if (Math.pow(x - mouseX, 2) + Math.pow(y - mouseY, 2) < r * r) {
                soundPlayer.start();
            }
        }
    }
}
