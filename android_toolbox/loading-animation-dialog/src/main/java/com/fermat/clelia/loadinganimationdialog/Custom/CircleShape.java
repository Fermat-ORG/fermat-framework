package com.fermat.clelia.loadinganimationdialog.Custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Clelia López on 3/8/16
 */
public class CircleShape
        extends View {

    private final Paint drawPaint;

    public CircleShape(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        int color = ContextCompat.getColor(getContext(), android.R.color.transparent);
        Drawable background = super.getBackground();
        if (background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();

        drawPaint = new Paint();
        drawPaint.setColor(color);
        drawPaint.setAntiAlias(true);

        setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
    }

    public void setColor(int color) {
        drawPaint.setColor(color);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        float size = super.getWidth()/2;
        canvas.drawCircle(size, size, size, drawPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = getMeasuredHeight();
        setMeasuredDimension(height, height);
    }
}

