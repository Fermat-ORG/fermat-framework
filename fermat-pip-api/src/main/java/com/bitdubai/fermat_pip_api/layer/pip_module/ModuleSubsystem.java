package com.bitdubai.fermat_pip_api.layer.pip_module;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface ModuleSubsystem {

    void start() throws CantStartSubsystemException;

    Plugin getPlugin();

}
