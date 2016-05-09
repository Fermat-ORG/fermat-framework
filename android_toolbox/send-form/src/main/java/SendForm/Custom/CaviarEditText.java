package SendForm.Custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.EditText;

import com.customviews.clelia.sendform.R;

import java.util.HashMap;

import SendForm.Util.TypefaceEnumType;


/**
 * Custom EditText view with Roboto Typeface
 */
public class CaviarEditText extends EditText {

    /** Attributes **/
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
        TypefaceEnumType typefaceType = TypefaceEnumType.values()[values.getInt(R.styleable.CaviarEditText_typeface, TypefaceEnumType.ROBOTO_REGULAR.ordinal())];
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
     * Method used to customize the default error icon
     *
     * @param error     error message
     */
    @Override
    public void setError(CharSequence error) {
        Drawable icon;
        if (error == null) {
            setCompoundDrawables(null, null, null, null);
            icon = ContextCompat.getDrawable(getContext(), R.drawable.ic_circle_check_green);
            icon.setBounds(0, 0, icon.getIntrinsicWidth() - 5 , icon.getIntrinsicHeight() - 5);
            setCompoundDrawables(null, null, icon, null);
            setError(null, icon);
        } else {
            icon = ContextCompat.getDrawable(getContext(), R.drawable.ic_circle_cancel_red);
            icon.setBounds(0, 0, icon.getIntrinsicWidth() - 5, icon.getIntrinsicHeight() - 5);
            setCompoundDrawables(null, null, icon, null);
            setError(error, icon);
        }
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
