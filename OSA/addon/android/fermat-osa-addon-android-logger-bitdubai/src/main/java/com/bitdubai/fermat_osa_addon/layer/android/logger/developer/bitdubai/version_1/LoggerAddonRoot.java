package com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogLevel;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

/**
 * Created by rodrigo on 2015.06.25..
 */
public class LoggerAddonRoot implements Addon, LogManagerForDevelopers, Service {

    /**
     * Default loggin level is minimal
     */
    LogLevel logLevel = LogLevel.MINIMAL_LOGGING;

    @Override
    public LogLevel getLoggingLevel() {
        return logLevel;
    }

    @Override
    public void changeLoggingLevel(LogLevel newLoggingLevel) {
        this.logLevel = newLoggingLevel;
    }

    /**
     * Service Interface implementation
     * @throws CantStartPluginException
     */
    @Override
    public void start() throws CantStartPluginException {

    }

    /**
     * Service Interface implementation
     */
    @Override
    public void pause() {

    }

    /**
     * Service Interface implementation
     */
    @Override
    public void resume() {

    }

    /**
     * Service Interface implementation
     */
    @Override
    public void stop() {

    }

    /**
     * Service Interface implementation
     * @return
     */
    @Override
    public ServiceStatus getStatus() {
        return null;
    }
}
