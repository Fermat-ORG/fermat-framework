package com.bitdubai.reference_wallet.crypto_broker_wallet.util;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Pattern;

/**
 * Created by Alejandro Bicelis on 12/16/2015.
 */
public class NumberInputFilter implements InputFilter {
    private Pattern mPattern;

    public NumberInputFilter(int precision, int scale) {
        String pattern = new StringBuilder().append("^\\-?(\\d{0,").append(precision - scale).append("}|\\d{0,").append(precision - scale).append("}\\.\\d{0,").append(scale).append("})$").toString();
        this.mPattern = Pattern.compile(pattern);

    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned destination, int destinationStart, int destinationEnd) {
        if (end > start) {
            // adding: filter
            // build the resulting text
            String destinationString = destination.toString();
            String resultingTxt = destinationString.substring(0, destinationStart) + source.subSequence(start, end) + destinationString.substring(destinationEnd);
            // return null to accept the input or empty to reject it
            return resultingTxt.matches(this.mPattern.toString()) ? null : "";
        }
        // removing: always accept
        return null;
    }

}