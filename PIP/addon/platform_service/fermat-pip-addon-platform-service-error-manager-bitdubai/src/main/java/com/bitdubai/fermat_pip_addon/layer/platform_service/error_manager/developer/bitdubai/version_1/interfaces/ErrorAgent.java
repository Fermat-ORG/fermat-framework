package com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.exceptions.CantStartAgentException;

/**
 * Created by ciencias on 4/3/15.
 */
public interface ErrorAgent {

    public void start () throws CantStartAgentException;

    public void stop();

}
