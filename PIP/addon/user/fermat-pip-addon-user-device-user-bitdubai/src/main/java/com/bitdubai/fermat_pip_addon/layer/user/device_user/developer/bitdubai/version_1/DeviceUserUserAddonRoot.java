package com.bitdubai.fermat_pip_addon.layer.user.device_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_pip_addon.layer.user.device_user.developer.bitdubai.version_1.structure.DeviceUserUserManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

/**
 * The User Manager knows the users managed by the current device.
 * <p/>
 * It is responsible for login in users to the current device.
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 01/07/2015.
 */

public class DeviceUserUserAddonRoot extends AbstractAddon {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER       )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER       )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM          , addon = Addons.PLATFORM_FILE_SYSTEM)
    private PlatformFileSystem platformFileSystem;


    private FermatManager deviceUserManager;

    /**
     * Constructor without parameters.
     */
    public DeviceUserUserAddonRoot() {
        super(new AddonVersionReference(new Version()));
    }

    @Override
    public final void start() throws CantStartPluginException {

        try {

            deviceUserManager = new DeviceUserUserManager(
                    this.getAddonVersionReference(),
                    errorManager,
                    eventManager,
                    platformFileSystem
            );

            super.start();

        } catch (final Exception e) {

            throw new CantStartPluginException(e, "Device User Manager starting.", "Unhandled Exception trying to start the device user manager.");
        }
    }

    @Override
    public final FermatManager getManager() {
        return deviceUserManager;
    }

}
