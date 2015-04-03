package com.bitdubai.fermat_api.layer._3_platform_service.error_manager;

import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;

/**
 * Created by ciencias on 05.02.15.
 */
public interface ErrorManager {
    
    public void reportUnexpectedPluginException(Plugins exceptionSource, UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity, Exception exception);
    
}
