package com.bitdubai.fermat_android_api.layer.definition.wallet.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import com.bitdubai.android_api.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.FontType;


/**
 * Custom Button
 *
 * @author Francisco Vásquez
 * @author Matias Furszyfer
 * @version 1.0
 */
public class FermatButton extends Button {

    public FermatButton(Context context) {
        super(context);
    }

    public FermatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            parseAttributes(context, attrs);
    }

    public FermatButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            parseAttributes(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FermatButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (!isInEditMode())
            parseAttributes(context, attrs);
    }

    /**
     * Parse custom typeface attribute
     *
     * @param context view context
     * @param attrs   attribute set
     */
    public void parseAttributes(Context context, AttributeSet attrs) {
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.FermatButton);
        FontType typefaceType = FontType.values()
                [values.getInt(R.styleable.FermatButton_typeface, FontType.ROBOTO_REGULAR.ordinal())];
        setFont(typefaceType);
        values.recycle();
    }

    /**
     * Set custom typeface from FontType enumerable
     *
     * @param fontType FontType enumerable to search in assets folder
     */
    public void setFont(FontType fontType) {
        try {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                    fontType.getPath());
            setTypeface(tf);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Set the button selector
     *
     * @param pressed_image
     * @param normal_image
     * @param focussed_iamge
     */

    public void selector(int pressed_image, int normal_image, int focussed_iamge) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            StateListDrawable states = new StateListDrawable();
            states.addState(new int[]{android.R.attr.state_pressed},
                    getResources().getDrawable(pressed_image));
            states.addState(new int[]{android.R.attr.state_focused},
                    getResources().getDrawable(focussed_iamge));
            states.addState(new int[]{},
                    getResources().getDrawable(normal_image));
            setBackground(states);
        }
    }
}
