package com.bitdubai.fermat_api.layer.all_definition.developer;

/**
 * Created by ciencias on 6/25/15.
 */


import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;

/**
 * Plugins & Addons implement this interface on their plugin|addon root in order to allow developers control the logging
 * level at runtime.
 */

public interface LogManagerForDevelopers {

    LogLevel getLoggingLevel();

    void changeLoggingLevel(LogLevel newLoggingLevel);

}
