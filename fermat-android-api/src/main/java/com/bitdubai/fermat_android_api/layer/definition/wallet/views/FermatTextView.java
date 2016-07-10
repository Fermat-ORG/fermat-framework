package com.bitdubai.fermat_android_api.layer.definition.wallet.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bitdubai.android_api.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.FontType;


/**
 * Custom TextView
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class FermatTextView extends TextView {

    public FermatTextView(Context context) {
        super(context);
    }

    public FermatTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            parseAttributes(context, attrs);
    }

    public FermatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            parseAttributes(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FermatTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.FermatTextView);
        FontType typefaceType = FontType.values()
                [values.getInt(R.styleable.FermatTextView_typeface, FontType.ROBOTO_REGULAR.ordinal())];
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


}
