package com.bitdubai.sub_app.crypto_customer_identity.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.util.Locale;

/**
 * Created by nelson on 12/10/15.
 */
public class UtilsFuncs {

    /**
     * Return a rounded bitmap version of the given bitmap
     *
     * @param res       the context resources
     * @param srcBitmap the bitmap with the image
     * @return the RoundedBitmapDrawable with the rounded bitmap
     */
    public static RoundedBitmapDrawable getRoundedBitmap(Resources res, Bitmap srcBitmap) {
        RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(res, srcBitmap);

        int radius = Math.min(srcBitmap.getWidth(), srcBitmap.getHeight());
        bitmapDrawable.setCornerRadius(radius);
        bitmapDrawable.setAntiAlias(true);

        return bitmapDrawable;
    }

    /**
     * Return a rounded bitmap version of the image contained in the drawable resource
     *
     * @param res        the context resources
     * @param drawableId the id of the drawable resource
     * @return the RoundedBitmapDrawable with the rounded bitmap
     */
    public static RoundedBitmapDrawable getRoundedBitmap(Resources res, int drawableId) {
        Bitmap srcBitmap = BitmapFactory.decodeResource(res, drawableId);
        return getRoundedBitmap(res, srcBitmap);
    }

    /**
     * Return a rounded bitmap version of the image contained in the byte array
     *
     * @param res        the context resources
     * @param imgInBytes the byte array with the image
     * @return the RoundedBitmapDrawable with the rounded bitmap
     */
    public static RoundedBitmapDrawable getRoundedBitmap(Resources res, byte[] imgInBytes) {
        Bitmap srcBitmap = BitmapFactory.decodeByteArray(imgInBytes, 0, imgInBytes.length);
        return getRoundedBitmap(res, srcBitmap);
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
