package com.bitdubai.fermat_core.layer.dmp_module;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.dmp_module.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.dmp_module.ModuleSubsystem;
import com.bitdubai.fermat_core.layer.dmp_module.notification.NotificationSubSystem;
import com.bitdubai.fermat_core.layer.dmp_module.wallet_runtime.WalletRuntimeSubsystem;

/**
 * Created by ciencias on 03.01.15.
 * Updated by Francisco on 08.27.15.
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 28/08/2015.
 */
public class ModuleLayer implements PlatformLayer {

    Plugin mWalletRuntime;

    Plugin mNotification;

    @Override
    public void start() throws CantStartLayerException {

        mWalletRuntime = getPlugin(new WalletRuntimeSubsystem());

        mNotification = getPlugin(new NotificationSubSystem());

    }

    private Plugin getPlugin(ModuleSubsystem moduleSubsystem) throws CantStartLayerException {
        try {
            moduleSubsystem.start();
            return moduleSubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getWalletRuntime() {
        return mWalletRuntime;
    }

    public Plugin getNotification() {
        return mNotification;
    }
}
