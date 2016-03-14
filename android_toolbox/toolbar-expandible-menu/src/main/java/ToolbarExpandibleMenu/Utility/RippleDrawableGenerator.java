package ToolbarExpandibleMenu.Utility;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * Created by Clelia López on 3/10/16
 */
public class RippleDrawableGenerator {


    public static Drawable generate(Context context, int normalColor, int pressedColor) {
        int mNormalColor = ContextCompat.getColor(context, pressedColor);
        int mPressedColor = ContextCompat.getColor(context, normalColor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return new RippleDrawable(ColorStateList.valueOf(mNormalColor), null, getRippleMask(mPressedColor));
        else
            return getStateListDrawable(mNormalColor, mPressedColor);
    }

    private static Drawable getRippleMask(int color) {
        RectShape rectangle = new RectShape();
        ShapeDrawable shapeDrawable = new ShapeDrawable(rectangle);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }

    public static StateListDrawable getStateListDrawable(int normalColor, int pressedColor) {
        StateListDrawable states = new StateListDrawable();
        states.addState(
                new int[] {android.R.attr.state_pressed},
                new ColorDrawable(pressedColor)
        );
        states.addState(
                new int[]{android.R.attr.state_focused},
                new ColorDrawable(pressedColor)
        );
        states.addState(
                new int[]{android.R.attr.state_activated},
                new ColorDrawable(pressedColor)
        );
        states.addState(
                new int[]{},
                new ColorDrawable(normalColor)
        );
        return states;
    }
}
