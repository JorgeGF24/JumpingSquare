package com.jorgegallego.framework.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

import com.jorgegallego.jumpingsquare.main.GameMainActivity;

/**
 * Created by Jorge y Juan on 11/07/2016.
 */
public class Painter {
    final float Y_CONSTANT = GameMainActivity.GAME_HEIGHT/1280f;

    private Canvas canvas;
    private Paint paint;
    private Rect srcRect;
    private Rect dstRect;
    private RectF dstRectF;

    public Painter(Canvas canvas) {
        this.canvas = canvas;
        paint = new Paint();
        srcRect = new Rect();
        dstRect = new Rect();
        dstRectF = new RectF();
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setFont(Typeface typeface, float textSize) {
        paint.setStyle(Paint.Style.FILL);
        paint.setTypeface(typeface);
        paint.setTextSize(textSize);
    }

    public void drawString(String str, int x, int y) {
        canvas.drawText(str, x, y, paint);
    }

    public int measureText(String string, int fontSize) {
        float densityMultiplier = GameMainActivity.sGame.getContext().getResources().getDisplayMetrics().density;
        final float scaledPx = fontSize * densityMultiplier;
        paint.setTextSize(scaledPx);
        final float size = paint.measureText(string);
        return (int) size;
    }

    public void fillRect (int x, int y, int width, int height) {
        dstRect.set(x, y, x + width, y + height);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(dstRect, paint);
    }

    public void fillRoundedRect (int x, int y, int width, int height, int roundAmount) {
        paint.setStyle(Paint.Style.FILL);
        dstRect.set(x, y + (int) (roundAmount * Y_CONSTANT), x + width, y + height - (int) (roundAmount * Y_CONSTANT));
        canvas.drawRect(dstRect, paint);
        dstRect.set(x + (int) (roundAmount * Y_CONSTANT), y, x + width - (int) (roundAmount * Y_CONSTANT), y + height);
        canvas.drawRect(dstRect, paint);

        dstRectF.set(x, y, x + 2 * roundAmount * Y_CONSTANT, y + 2 * roundAmount * Y_CONSTANT);
        canvas.drawOval(dstRectF, paint);
        dstRectF.set(x + width, y, x + 2 * roundAmount * Y_CONSTANT, y + 2 * roundAmount * Y_CONSTANT);
        canvas.drawOval(dstRectF, paint);
        dstRectF.set(x + width, y + height, x + 2 * roundAmount * Y_CONSTANT, y + 2 * roundAmount * Y_CONSTANT);
        canvas.drawOval(dstRectF, paint);
        dstRectF.set(x, y + height,  x + 2 * roundAmount * Y_CONSTANT, y + 2 * roundAmount * Y_CONSTANT);
        canvas.drawOval(dstRectF, paint);
    }

    public void drawImage(Bitmap bitmap, int x, int y) {
        canvas.drawBitmap(bitmap, x, y, paint);
    }

    public void drawImage(Bitmap bitmap, int x, int y, int width, int height) {
        srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        dstRect.set(x, y, x + width, y + height);
        canvas.drawBitmap(bitmap, srcRect, dstRect, paint);
    }

    public void fillOval(int x, int y, int width, int height) {
        paint.setStyle(Paint.Style.FILL);
        dstRectF.set(x, y, x + width, y + height);
        canvas.drawOval(dstRectF, paint);
    }

    public void drawOval(int x, int y, int width, int height, int thickness) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        dstRectF.set(x, y, x + width, y + height);
        canvas.drawOval(dstRectF, paint);
    }

    public void fillBackground(int r, int g, int b) {
        canvas.drawRGB(r, g, b);
    }

    public void drawRect(int x, int y, int width, int height, int border){
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(border);
        canvas.drawRect(x, y, x + width, y + height, paint);
        paint.setColor(Color.WHITE);
        paint.setAlpha(100);

        fillRect(x + border - 4, y + border - 4, width - border * 2 + 8, height - border * 2 + 8);

        paint.setAlpha(255);
        paint.setColor(Color.BLACK);
    }
}
