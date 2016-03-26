package TransactionsFragment.Custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.customviews.clelia.transactionsfragment.R;

import java.util.HashMap;

import TransactionsFragment.Utility.TypefaceEnumType;

/**
 * Custom TextView view with Roboto Typeface
 */

public class CaviarTextView
        extends TextView {


    /** Attributes */
    private static final HashMap<TypefaceEnumType, String> typefaceMap;
    Context context;

    static {
        typefaceMap = new HashMap<>();
        typefaceMap.put(TypefaceEnumType.ROBOTO_REGULAR, "fonts/roboto/Roboto_Regular.ttf");
        typefaceMap.put(TypefaceEnumType.ROBOTO_BOLD, "fonts/roboto/Roboto_Bold.ttf");
        typefaceMap.put(TypefaceEnumType.ROBOTO_ITALIC, "fonts/roboto/Roboto_Italic.ttf");
        typefaceMap.put(TypefaceEnumType.ROBOTO_BOLD_ITALIC, "fonts/roboto/Roboto_Bold_Italic.ttf");
        typefaceMap.put(TypefaceEnumType.CAVIAR_DREAMS, "fonts/caviar_dreams/CaviarDreams.ttf");
        typefaceMap.put(TypefaceEnumType.CAVIAR_DREAMS_BOLD, "fonts/caviar_dreams/CaviarDreams_Bold.ttf");
        typefaceMap.put(TypefaceEnumType.CAVIAR_DREAMS_ITALIC, "fonts/caviar_dreams/CaviarDreams_Italic.ttf");
        typefaceMap.put(TypefaceEnumType.CAVIAR_DREAMS_BOLD_ITALIC, "fonts/caviar_dreams/CaviarDreams_Bold_Italic.ttf");
    }

    public CaviarTextView(Context context) {
        super(context);
        if (!isInEditMode())
            setDefaultTypeface(context);
        this.context = context;
    }

    public CaviarTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            parseAttributes(context, attrs);
        this.context = context;
    }

    public CaviarTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            parseAttributes(context, attrs);
        this.context = context;
    }

    @SuppressLint("NewApi")
    public CaviarTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        TypefaceEnumType typefaceType = TypefaceEnumType.values()[values.getInt(R.styleable.CaviarTextView_typeface, TypefaceEnumType.ROBOTO_REGULAR.ordinal())];
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


