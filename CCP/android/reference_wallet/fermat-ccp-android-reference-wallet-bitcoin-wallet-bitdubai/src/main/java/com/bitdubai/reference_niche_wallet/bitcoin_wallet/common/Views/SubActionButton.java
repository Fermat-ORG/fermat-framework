package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.utils.FermatScreenCalculator;

/**
 * Modified by
 * @author Clelia López
 */
public class SubActionButton
        extends LinearLayout {

    FrameLayout frameLayout;
    TextView textView;
    public static final int THEME_LIGHT = 0;
    public static final int THEME_DARK = 1;
    public static final int THEME_LIGHTER = 2;
    public static final int THEME_DARKER = 3;


    public SubActionButton(Context context) {
        super(context);
    }

    public SubActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SubActionButton(Context context, int theme, Drawable backgroundDrawable, int size,
        int paddingLeft, int paddingRight, int paddingTop, int paddingBottom, String text,
        int textColor, int textBackgroundColor, Drawable textBackgroundDrawable) {
        super(context);

        // TextView set up
        textView = new TextView(context);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(textColor);
        if(textBackgroundColor != -1)
            textView.setBackgroundColor(textBackgroundColor);
        if(textBackgroundDrawable != null)
            textView.setBackground(textBackgroundDrawable);
        int left = FermatScreenCalculator.getPx(context, 5);
        int down = FermatScreenCalculator.getPx(context, 2);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setPadding(left, 0, left, down);
        textView.setLayoutParams(textViewParams);

        // Button set up
        if(backgroundDrawable == null) {
            switch (theme) {
                case THEME_LIGHT:
                    backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.button_sub_action_selector);
                    break;
                case THEME_DARK:
                    backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.button_sub_action_dark_selector);
                    break;
                case THEME_LIGHTER:
                    backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.button_action_selector);
                    break;
                case THEME_DARKER:
                    backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.button_action_dark_selector);
                    break;
                default:
                    throw new RuntimeException("Unknown SubActionButton theme: " + theme);
            }
        } else
            backgroundDrawable = backgroundDrawable.mutate().getConstantState().newDrawable();

        FrameLayout.LayoutParams params;
        if(size != -1) {
            size = FermatScreenCalculator.getPx(context, size);
            params = new FrameLayout.LayoutParams(size, size);
        } else
            params = new FrameLayout.LayoutParams(60, 60);
        frameLayout = new FrameLayout(context);
        frameLayout.setBackground(backgroundDrawable);
        frameLayout.setLayoutParams(params);

        // View set up
        LinearLayout.LayoutParams containerParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(containerParams);
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);

        setOrientation(HORIZONTAL);
        addView(textView);
        addView(frameLayout);
        setClickable(true);
    }

    /**
     * Sets a content view with custom LayoutParams that will be displayed inside this SubActionButton.
     * @param contentView -
     * @param params -
     */
    public void setContentView(View contentView, LayoutParams params) {
        if(params == null) {
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            final int margin = getResources().getDimensionPixelSize(R.dimen.sub_action_button_content_margin);
            params.setMargins(margin, margin, margin, margin);
        }

        contentView.setClickable(false);
        this.addView(contentView, params);
    }

    /**
     * Sets a content view with default LayoutParams
     * @param contentView -
     */
    public void setContentView(View contentView) {
        setContentView(contentView, null);
    }

    /**
     * A builder for {@link SubActionButton} in conventional Java Builder format
     */
    public static class Builder {

        private Context context;
        private int theme;
        private Drawable backgroundDrawable;
        private String text;
        private int textColor;
        private int textBackgroundColor = -1;
        private Drawable textBackgroundDrawable = null;
        private int size = -1;
        private int paddingLeft = 0;
        private int paddingRight = 0;
        private int paddingTop = 0;
        private int paddingBottom = 0;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTheme(int theme) {
            this.theme = theme;
            return this;
        }

        public Builder setBackgroundDrawable(Drawable backgroundDrawable) {
            this.backgroundDrawable = backgroundDrawable;
            return this;
        }

        public Builder setText(String text){
            this.text = text;
            return this;
        }

        public Builder setTextColor(int color) {
            this.textColor = color;
            return this;
        }

        public Builder setTextBackgroundColor(int color) {
            this.textBackgroundColor = color;
            return this;
        }

        public Builder setTextBackgroundDrawable(Drawable drawable) {
            this.textBackgroundDrawable = drawable;
            return this;
        }

        /**
         * Set size dimension (width == height)
         * @param size - size in DP
         */
        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        /**
         * Set padding in pixels
         */
        public Builder setPadding(int left, int top, int right, int bottom) {
            this.paddingLeft = left;
            this.paddingTop = top;
            this.paddingRight = right;
            this.paddingBottom = bottom;
            return this;
        }

        public SubActionButton build() {
            return new SubActionButton(context, theme, backgroundDrawable, size, paddingLeft,
                    paddingRight, paddingTop, paddingBottom, text, textColor, textBackgroundColor,
                    textBackgroundDrawable);
        }
    }
}