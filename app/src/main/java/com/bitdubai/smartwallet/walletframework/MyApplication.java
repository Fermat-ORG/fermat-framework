package com.bitdubai.smartwallet.walletframework;

import android.app.Application;
import android.graphics.Typeface;



public class MyApplication extends Application {

    public static Typeface mDefaultTypeface;
    public  static int mWalletId;


    public static int  getWalletId() {
        return mWalletId;
    }

    public  static void setWalletId(int WalletId) {
        mWalletId = WalletId;
    }


    public static Typeface getDefaultTypeface() {
        return mDefaultTypeface;
    }

    public static void setDefaultTypeface(Typeface DefaultTypeface) {
        mDefaultTypeface = DefaultTypeface;
    }



}