package com.bitdubai.fermat_android_api.ui.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;


public class BadgeDrawable extends Drawable {

    public enum Position {
        TOP_RIGHT, CENTER, TOP_LEFT
    }

    private float mTextSize;
    private Paint mBadgePaint;
    private Paint mTextPaint;
    private Rect mTxtRect = new Rect();

    private String mCount = "";
    private boolean mWillDraw = false;
    private int color = 0;
    private Position position = Position.CENTER;

    public BadgeDrawable(Context context) {
        mTextSize = 96;//context.getResources().getDimension(R.dimen.badge_text_size);

        mBadgePaint = new Paint();
        mBadgePaint.setColor(Color.RED);
        mBadgePaint.setAntiAlias(true);
        mBadgePaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void draw(Canvas canvas) {
        if (!mWillDraw) {
            return;
        }
        if (color != 0) {
            mBadgePaint.setColor(color);
        }
        if (mTextSize != 0) {
            mTextPaint.setTextSize(mTextSize);
        }

        Rect bounds = getBounds();
        float width = bounds.right - bounds.left;
        float height = bounds.bottom - bounds.top;

        float radius = 0;
        float centerX = 0;
        float centerY = 0;
        switch (position) {
            case TOP_RIGHT:
                // Position the badge in the top-right quadrant of the icon.
                radius = ((Math.min(width, height) / 2) - 1) / 2;
                centerX = width - radius - 1;
                centerY = radius + 1;
                break;
            case CENTER:
                radius = ((Math.min(width, height) / 2) - 1) / 2;
                centerX = width / 2 - 1;
                centerY = height / 2;
                break;
        }
        // Draw badge circle.
        canvas.drawCircle(centerX, centerY, radius, mBadgePaint);

        // Draw badge count text inside the circle.
        mTextPaint.getTextBounds(mCount, 0, mCount.length(), mTxtRect);
        float textHeight = mTxtRect.bottom - mTxtRect.top;
        float textY = centerY + (textHeight / 2f);
        canvas.drawText(mCount, centerX, textY, mTextPaint);
    }

    /*
    Sets the count (i.e notifications) to display.
     */
    public void setCount(int count) {
        mCount = Integer.toString(count);

        // Only draw a badge if there are notifications.
        mWillDraw = count > 0;
        invalidateSelf();
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public void setAlpha(int alpha) {
        // do nothing
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        // do nothing
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
    }


    public static class BadgeDrawableBuilder {

        private final Context context;
        private int count = 0;
        private int color;
        private int alpha;
        private int textSize = 0;
        private Position position = Position.CENTER;

        public BadgeDrawableBuilder(Context context) {
            this.context = context;
        }

        public BadgeDrawableBuilder setCount(int count) {
            this.count = count;
            return this;
        }

        public BadgeDrawableBuilder setColor(int color) {
            this.color = color;
            return this;
        }

        public BadgeDrawableBuilder setAlpha(int alpha) {
            this.alpha = alpha;
            return this;
        }

        public BadgeDrawableBuilder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public BadgeDrawableBuilder setPosition(Position position) {
            this.position = position;
            return this;
        }

        public BadgeDrawable build() {
            BadgeDrawable badgeDrawable = new BadgeDrawable(context);
            badgeDrawable.setCount(count);
            if (color != 0) {
                badgeDrawable.setColor(color);
            }
            if (textSize != 0) {
                badgeDrawable.setTextSize(textSize);
            }
            badgeDrawable.setPosition(position);
            return badgeDrawable;
        }


    }

    public static class Utils {

        public static void setBadgeCount(Context context, LayerDrawable icon, int count, int resId) {

            BadgeDrawable badge;

            // Reuse drawable if possible
            Drawable reuse = icon.findDrawableByLayerId(resId);
            if (reuse != null && reuse instanceof BadgeDrawable) {
                badge = (BadgeDrawable) reuse;
            } else {
                badge = new BadgeDrawable(context);
            }

            badge.setCount(count);
            icon.mutate();
            icon.setDrawableByLayerId(resId, badge);
        }
    }
}