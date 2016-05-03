package CountryStateSelector.MaterialSpinner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.customviews.clelia.countrystateselector.R;

/**
 * Created by Clelia López on 2/27/16
 */
public class TriangleShape
        extends View {

    Paint mPaint;
    Path mPath;
    Direction direction;

    public enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    public TriangleShape(Context context) {
        super(context);
        setDrawingCacheEnabled(true);
        direction = TriangleShape.Direction.SOUTH;
        initialize();
    }

    public TriangleShape(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDrawingCacheEnabled(true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TriangleShape);
        String value = typedArray.getString(R.styleable.TriangleShape_direction);
        typedArray.recycle();

        direction = Direction.SOUTH;
        if(value != null)
            switch (Integer.parseInt(value)) {
                case 0: direction = Direction.NORTH; break;
                case 1: direction = Direction.SOUTH; break;
                case 2: direction = Direction.EAST; break;
                case 3: direction = Direction.WEST; break;
            }

        initialize();
    }

    private void initialize() {
        int color = ContextCompat.getColor(getContext(), android.R.color.transparent);
        Drawable background = super.getBackground();
        if (background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();

        mPaint = new Paint();
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);

        setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));

        invalidate();
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath = calculate(direction);
        canvas.drawPath(mPath, mPaint);
    }

    private Path calculate(Direction direction) {
        Point p1 = new Point();
        p1.x = 0;
        p1.y = 0;

        Point p2 = null, p3 = null;

        int width = getWidth();
        int height = getHeight();

        switch (direction) {
            case NORTH:
                p2 = new Point(p1.x, p1.y + height);
                p3 = new Point(p1.x + width, p1.y + height);
                p1 = new Point(width/2, 0);
                break;
            case SOUTH:
                p2 = new Point(p1.x + width, p1.y);
                p3 = new Point(p1.x + (width / 2), p1.y + height);
                break;
            case EAST:
                p2 = new Point(p1.x + width, p1.y + height);
                p3 = new Point(p1.x + width, p1.y);
                p1 = new Point(0, height/2);
                break;
            case WEST:
                p2 = new Point(p1.x, p1.y + height);
                p3 = new Point(p1.x + width, p1.y + (height / 2));
                break;
        }

        Path path = new Path();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);

        return path;
    }
}