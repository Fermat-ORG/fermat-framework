package com.customviews.fermat.currency_selector.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.customviews.fermat.currency_selector.R;
import com.customviews.fermat.currency_selector.custom.util.TypefaceEnumType;


import java.util.HashMap;


/**
 * Custom EditText view with Roboto Typeface
 */
public class CaviarEditText extends EditText {

    /** Attributes **/
    private static final HashMap<TypefaceEnumType, String> typefaceMap;
    Context context;

    static {
        typefaceMap = new HashMap<>();
        typefaceMap.put(TypefaceEnumType.CAVIAR_DREAMS, "fonts/caviar_dreams/CaviarDreams.ttf");
        typefaceMap.put(TypefaceEnumType.CAVIAR_DREAMS_BOLD, "fonts/caviar_dreams/CaviarDreams_Bold.ttf");
        typefaceMap.put(TypefaceEnumType.CAVIAR_DREAMS_ITALIC, "fonts/caviar_dreams/CaviarDreams_Italic.ttf");
        typefaceMap.put(TypefaceEnumType.CAVIAR_DREAMS_BOLD_ITALIC, "fonts/caviar_dreams/CaviarDreams_Bold_Italic.ttf");
    }

    public CaviarEditText(Context context) {
        super(context);
        if (!isInEditMode())
            setDefaultTypeface(context);
        this.context = context;
    }

    public CaviarEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            parseAttributes(context, attrs);
        this.context = context;
    }

    public CaviarEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            parseAttributes(context, attrs);
        this.context = context;
    }

    @SuppressLint("NewApi")
    public CaviarEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.CaviarTextView);
        TypefaceEnumType typefaceType = TypefaceEnumType.values()[values.getInt(R.styleable.CaviarEditText_typeface, TypefaceEnumType.CAVIAR_DREAMS.ordinal())];
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
}
