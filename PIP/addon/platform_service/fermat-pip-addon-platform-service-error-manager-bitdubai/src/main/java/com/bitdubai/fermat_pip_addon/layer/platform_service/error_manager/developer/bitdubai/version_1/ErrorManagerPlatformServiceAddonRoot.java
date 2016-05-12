package com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.hardware.HardwareManager;
import com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.structure.ErrorManagerPlatformServiceManager;

/**
 * Throw this addon you can report an unexpected error in the platform.
 *
 * For now, the only functionality of the addon is report in log all problems, but in the near future, the idea is to save all the errors and send to a bitDubai server to be properly controlled.
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */
public final class ErrorManagerPlatformServiceAddonRoot extends AbstractAddon {

    private FermatManager errorManager;
   // @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.HARDWARE)
    protected HardwareManager hardwareManager;

    public ErrorManagerPlatformServiceAddonRoot() {
        super(new AddonVersionReference(new Version()));
    }

    @Override
    public final void start() throws CantStartPluginException {

        try {

            errorManager = new ErrorManagerPlatformServiceManager(hardwareManager);

            super.start();

        } catch (final Exception e) {

            throw new CantStartPluginException(e, "Error Manager starting.", "Unhandled Exception trying to start the error manager.");
        }
    }

    @Override
    public final FermatManager getManager() {
        return errorManager;
    }
}
