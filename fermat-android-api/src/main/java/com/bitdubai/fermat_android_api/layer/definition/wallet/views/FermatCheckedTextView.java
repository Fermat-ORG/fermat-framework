package com.bitdubai.fermat_android_api.layer.definition.wallet.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CheckedTextView;

import com.bitdubai.android_api.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.FontType;

/**
 * Created by Yordin Alayn on 31.05.16.
 */
public class FermatCheckedTextView extends CheckedTextView {


    public FermatCheckedTextView(Context context) {
        super(context);
    }

    public FermatCheckedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            parseAttributes(context, attrs);
        }
    }

    public FermatCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            parseAttributes(context, attrs);
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FermatCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (!isInEditMode()) {
            parseAttributes(context, attrs);
        }
    }

    /**
     * Parse custom typeface attribute
     *
     * @param context view context
     * @param attrs   attribute set
     */
    public void parseAttributes(Context context, AttributeSet attrs) {
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.FermatCheckedTextView);
        FontType typefaceType = FontType.values()
                [values.getInt(R.styleable.FermatCheckedTextView_typeface, FontType.CAVIAR_DREAMS.ordinal())];
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
