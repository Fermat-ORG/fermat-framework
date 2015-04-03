package com.bitdubai.fermat_core.layer._3_platform_service.error_manager.developer.version_1.interfaces;

import com.bitdubai.fermat_core.layer._3_platform_service.error_manager.developer.version_1.exceptions.CantStartAgentException;

/**
 * Created by ciencias on 4/3/15.
 */
public interface ErrorAgent {

    public void start () throws CantStartAgentException;

    public void stop();

}
