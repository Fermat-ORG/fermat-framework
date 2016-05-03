package com.bitdubai.fermat_api.layer.all_definition.developer;

/**
 * Created by ciencias on 6/25/15.
 */


import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;

import java.util.List;
import java.util.Map;

/**
 * Plugins & Addons implement this interface on their plugin|addon root in order to allow developers control the logging
 * level at runtime.
 */

public interface LogManagerForDevelopers {

    //todo remove if new implementation works
    List<String> getClassesFullPath();

    void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel);

    LogLevel DEFAULT_LOG_LEVEL = LogLevel.MINIMAL_LOGGING;

}
