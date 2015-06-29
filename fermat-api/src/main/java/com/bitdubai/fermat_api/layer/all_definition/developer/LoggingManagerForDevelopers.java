package com.bitdubai.fermat_api.layer.all_definition.developer;

/**
 * Created by ciencias on 6/25/15.
 */

import java.util.UUID;

/**
 * Plugins & Addons implement this interface on their plugin|addon root in order to allow developers control the logging
 * level at runtime.
 */

public interface LoggingManagerForDevelopers {

    public LogLevel getLoggingLevel();

    public void changeLoggingLevel(LogLevel newLogLevel);

    public UUID getPluginId();

    public String getOutputMessage();

}