package com.bitdubai.fermat_api.layer.osa_android.logger_system;

/**
 * Created by ciencias on 6/25/15.
 */

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

/**
 * Plugins & Addons implement this interface on their plugin|addon root in order to allow developers control the logging
 * level at runtime.
 */

public interface LogManager extends FermatManager {

    void log(LogLevel logLevel, String minimalLogging, String moderateLogging, String aggressiveLogging);

    String getOutputMessage();

}
