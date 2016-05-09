package com.customviews.fermat.currency_selector.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.RadioButton;


import com.customviews.fermat.currency_selector.R;
import com.customviews.fermat.currency_selector.custom.util.TypefaceEnumType;

import java.util.HashMap;

/**
 * Custom EditText view with Caviar Typeface
 */
public class CaviarRadioButton
        extends RadioButton {

    /** Attributes */
    private static final HashMap<TypefaceEnumType, String> typefaceMap;
    private Context context;

    /** Attributes */
    static {
        typefaceMap = new HashMap<>();
        typefaceMap.put(TypefaceEnumType.CAVIAR_DREAMS, "fonts/caviar_dreams/CaviarDreams.ttf");
        typefaceMap.put(TypefaceEnumType.CAVIAR_DREAMS_BOLD, "fonts/caviar_dreams/CaviarDreams_Bold.ttf");
        typefaceMap.put(TypefaceEnumType.CAVIAR_DREAMS_ITALIC, "fonts/caviar_dreams/CaviarDreams_Italic.ttf");
        typefaceMap.put(TypefaceEnumType.CAVIAR_DREAMS_BOLD_ITALIC, "fonts/caviar_dreams/CaviarDreams_Bold_Italic.ttf");
    }

    public CaviarRadioButton(Context context) {
        super(context);
        if (!isInEditMode())
            setDefaultTypeface(context);
        this.context = context;
    }

    public CaviarRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            parseAttributes(context, attrs);
        this.context = context;
    }

    public CaviarRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            parseAttributes(context, attrs);
        this.context = context;
    }

    @SuppressLint("NewApi")
    public CaviarRadioButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (!isInEditMode())
            parseAttributes(context, attrs);
        this.context = context;
    }

    /**
     * Parse Caviar typeface
     *
     * @param context view context
     * @param attrs   attribute set
     */
    public void parseAttributes(Context context, AttributeSet attrs) {
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.CaviarRadioButton);
        TypefaceEnumType typefaceType = TypefaceEnumType.values()[values.getInt(R.styleable.CaviarRadioButton_typeface, TypefaceEnumType.CAVIAR_DREAMS.ordinal())];
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), typefaceMap.get(typefaceType));
        if (typeface != null)
            setTypeface(typeface);
        values.recycle();
    }

    /**
     * Set typeface
     *
     *  @param type   typeface type
     */
    public void setTypeFace(TypefaceEnumType type) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), typefaceMap.get(type));
        setTypeface(typeface);
    }

    /**
     * Set default typeface
     *
     * @param context view context
     */
    public void setDefaultTypeface(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), typefaceMap.get(TypefaceEnumType.CAVIAR_DREAMS));
        if (typeface != null)
            setTypeface(typeface);
    }

    /**
     * Change drawable top after another button is pressed
     *
     * @param toDefault     boolean that indicates if size should be scaled to a default size
     * @param width         default width
     * @param height        default height
     */
    public void changeDrawableSize(boolean toDefault, int width, int height, int selectedColor, int regularColor, int drawable) {
        if(drawable != -1) {
            if(toDefault) {
                setDrawable(drawable, width, height);
                setTextColor(ContextCompat.getColor(getContext(), regularColor));
            } else {
                setDrawable(drawable, width + 12, height + 12);
                setTextColor(ContextCompat.getColor(getContext(), selectedColor));
            }
        }
    }

    /**
     * Set drawable top
     *
     *  @param drawable    id of selected drawable (E.g: R.drawable.name), 0 for null
     */
    public void setDrawable(int drawable, int width, int height) {
        Drawable icon = ContextCompat.getDrawable(getContext(), drawable);
        icon.setBounds(0, 0, width, height);
        setCompoundDrawables(null, icon, null, null);
    }

    public Point getDrawableSize() {
        Drawable icons[] = this.getCompoundDrawables();
        Drawable top = icons[1];
        return new Point(top.getIntrinsicWidth(), top.getIntrinsicHeight());
    }
}