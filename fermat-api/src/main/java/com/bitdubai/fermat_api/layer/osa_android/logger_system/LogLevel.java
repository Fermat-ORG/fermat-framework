package com.bitdubai.fermat_api.layer.osa_android.logger_system;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;


/**
 * Created by ciencias on 6/25/15.
 */
public enum LogLevel {

    NOT_LOGGING("NOTLOG", "Logging not defined"),
    MINIMAL_LOGGING("MINLOG", "Logging with minimum information"),
    MODERATE_LOGGING("MODLOG", "Logging with medium information"),
    AGGRESSIVE_LOGGING("AGGLOG", "Logging with maximum information");


    private String code;
    private final String mDisplayName;

    LogLevel(String code, String DisplayName) {
        this.code = code;
        this.mDisplayName = DisplayName;
    }

    public String getCode() {
        return code;
    }

    public static LogLevel getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "NOTLOG":
                return LogLevel.NOT_LOGGING;
            case "MINLOG":
                return LogLevel.MINIMAL_LOGGING;
            case "MODLOG":
                return LogLevel.MODERATE_LOGGING;
            case "AGGLOG":
                return LogLevel.AGGRESSIVE_LOGGING;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the logLevel enum");


        }

    }

    public String getDisplayName() {
        return mDisplayName;
    }


}
