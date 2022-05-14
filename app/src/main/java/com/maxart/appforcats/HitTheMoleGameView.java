package com.maxart.appforcats;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

public class HitTheMoleGameView extends View {

    Bitmap background;
    Point offset;

    private int points = 0;

    public HitTheMoleGameView(Context context) {
        super(context);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.game_forest_background);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        scaleCenterCrop(background, getWidth(), getHeight());
        canvas.drawBitmap(background, offset.x, offset.y, null);
    }


    private void scaleCenterCrop(Bitmap source, int newWidth, int newHeight) {
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
        background = Bitmap.createScaledBitmap(source, scaledWidth, scaledHeight, true);

        // Let's find out the upper left coordinates if the scaled bitmap
        // should be centered in the new size give by the parameters
        int left = (newWidth - scaledWidth) / 2;
        int top = (newHeight - scaledHeight) / 2;
        offset = new Point(left, top);
    }

}
