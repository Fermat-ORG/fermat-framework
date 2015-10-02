package com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by Nerio on 29/09/15.
 */
public interface WPDSubAppModuleSubsystem {

    void start() throws CantStartSubsystemException;

    Plugin getPlugin();
}
