package com.bitdubai.fermat_api.layer.osa_android.logger_system;

/**
 * Created by ciencias on 6/25/15.
 */
public enum LogLevel {

    NOT_LOGGING ("NOT_LOGGING", "Logging not defined"),
    MINIMAL_LOGGING   ("MINIMAL_LOGGING", "Logging with minimum information"),
    MODERATE_LOGGING   ("MODERATE_LOGGING","Logging with medium information"),
    AGGRESSIVE_LOGGING ("AGGRESSIVE_LOGGING","Logging with maximum information");


    private final String mCode;
    private final String mDisplayName;

    private LogLevel (String Code, String DisplayName) {
        this.mCode = Code;
        this.mDisplayName = DisplayName;
    }

    public String getCode()   { return mCode; }
    public String getDisplayName() { return mDisplayName; }



}
