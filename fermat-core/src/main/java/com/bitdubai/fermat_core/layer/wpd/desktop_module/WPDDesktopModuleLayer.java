package com.bitdubai.fermat_core.layer.wpd.desktop_module;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;

import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.CantStartSubsystemException;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.WPDDesktopModuleSubsystem;

import com.bitdubai.fermat_core.layer.wpd.desktop_module.wallet_manager.WalletManagerSubsystem;

/**
 * Created by Nerio on 02/10/15.
 */
public class WPDDesktopModuleLayer implements PlatformLayer {

    Plugin mWalletManager;

    /**
     * Each layer is started and by that time has the chance to initialize its services.
     */
    @Override
    public void start() throws CantStartLayerException {

        mWalletManager = getPlugin(new WalletManagerSubsystem());
    }

    private Plugin getPlugin(WPDDesktopModuleSubsystem wpdDesktopModuleSubsystem) throws CantStartLayerException {
        try {
            wpdDesktopModuleSubsystem.start();
            return wpdDesktopModuleSubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getWalletManager() {
        return mWalletManager;
    }

}
