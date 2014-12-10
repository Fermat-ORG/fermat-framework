package com.bitdubai.smartwallet.walletframework;

import android.app.Application;
import android.graphics.Typeface;



public class MyApplication extends Application {

    public static Typeface mDefaultTypeface;

    public static Typeface getDefaultTypeface() {
        return mDefaultTypeface;
    }

    public static void setDefaultTypeface(Typeface DefaultTypeface) {
        mDefaultTypeface = DefaultTypeface;
    }
}