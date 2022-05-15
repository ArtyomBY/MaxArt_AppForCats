package com.maxart.appforcats;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class HitTheMoleGameView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;

    public HitTheMoleGameView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        drawThread = new DrawThread(getContext(), getHolder());
        drawThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        drawThread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        drawThread.setTowardPoint((int)event.getX(), (int) event.getY());
        return false;
    }




    private class DrawThread extends Thread {

        private SurfaceHolder surfaceHolder;

        private volatile boolean running = true;
        private Paint paint = new Paint();
        private Bitmap background, mole;
        private int towardPointX, towardPointY;
        private int canvasWidth, canvasHeight;

        public DrawThread(Context context, SurfaceHolder surfaceHolder) {

            canvasWidth = getWidth();
            canvasHeight = getHeight();

            mole = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole_icon);
            mole = Bitmap.createScaledBitmap(mole, 200, 150, false);
            background = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_forest_background);
            background = Bitmap.createScaledBitmap(background, getWidth(), getHeight(), false);
            this.surfaceHolder = surfaceHolder;

        }

        public void setTowardPoint(int x, int y) {
            towardPointX = x;
            towardPointY = y;
        }

        public void requestStop(){
            running = false;
        }

        @Override
        public void run() {

            int hole1_x = canvasWidth / 2;
            int hole1_y = canvasHeight / 2;

            int hole2_x = canvasWidth / 4;
            int hole2_y = canvasHeight / 4;

            int moleX = hole1_x;
            int moleY = hole1_y;

            while (running) {
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    try {
                        canvas.drawBitmap(background, 0, 0, null);

                        paint.setStyle(Paint.Style.FILL);
                        int pColor = Color.parseColor("#D5CD3E");
                        paint.setColor(pColor);
                        RectF oval1 = new RectF(hole1_x, hole1_y, hole1_x + 50,hole1_y + 150);
                        canvas.drawOval(oval1, paint);
                        RectF oval2 = new RectF(hole2_x,hole2_y, hole2_x + 50,hole2_y + 150);
                        canvas.drawOval(oval2, paint);

                        //canvas.drawBitmap(mole, moleX, moleY, null);

                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }




    }

}
