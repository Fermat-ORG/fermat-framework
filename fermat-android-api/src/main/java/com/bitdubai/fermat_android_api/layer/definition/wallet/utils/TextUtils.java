package com.bitdubai.fermat_android_api.layer.definition.wallet.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Toast;

import java.util.Locale;


/**
 * Class with utility static methods to work with text
 *
 * @author Matias Furszyfer
 */
public class TextUtils {

    /**
     * copy text to clipboard
     */
    public static void copyToClipboard(Activity activity, String text) {

        ClipboardManager myClipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(activity.getApplicationContext(), "Text Copied",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Return a spanned string based on a substring (the substring is spanned)
     *
     * @param res       the context resources
     * @param text      the string with all the text
     * @param substring te substring that is going to be spanned
     * @return the spanned string
     */
    public static SpannableString getSpannedText(Resources res, int colorResourceId, String text, String substring) {
        final SpannableString textToShow = new SpannableString(text);

        if (substring != null && !substring.isEmpty()) {
            substring = substring.toLowerCase(Locale.getDefault());
            text = text.toLowerCase(Locale.getDefault());

            final int start = text.indexOf(substring);
            final int end = start + substring.length();

            final int color = res.getColor(colorResourceId);
            final ForegroundColorSpan span = new ForegroundColorSpan(color);

            textToShow.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return textToShow;
    }
}
