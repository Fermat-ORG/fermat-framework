package com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by Nerio on 29/09/15.
 */
public interface WPDDesktopModuleSubsystem {

    void start() throws CantStartSubsystemException;

    Plugin getPlugin();
}
