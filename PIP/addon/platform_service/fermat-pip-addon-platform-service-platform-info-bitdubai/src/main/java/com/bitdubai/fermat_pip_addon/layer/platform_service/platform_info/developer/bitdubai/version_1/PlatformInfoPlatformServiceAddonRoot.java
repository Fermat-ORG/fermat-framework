package com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1.structure.PlatformInfoPlatformServiceManager;

/**
 * Created by natalia on 29/07/15.
 * Modified by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */
public class PlatformInfoPlatformServiceAddonRoot extends AbstractAddon {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLATFORM_FILE_SYSTEM)
    private PlatformFileSystem platformFileSystem;

    private FermatManager platformInfoManager;

    /**
     * Constructor without parameters.
     */
    public PlatformInfoPlatformServiceAddonRoot() {
        super(new AddonVersionReference(new Version()));
    }

    @Override
    public final void start() throws CantStartPluginException {

        try {

            platformInfoManager = new PlatformInfoPlatformServiceManager(
                    this.getAddonVersionReference(),
                    errorManager,
                    platformFileSystem
            );

            super.start();

        } catch (final Exception e) {

            throw new CantStartPluginException(e, "Platform Info Manager starting.", "Unhandled Exception trying to start the Platform info manager.");
        }
    }

    @Override
    public final FermatManager getManager() {
        return platformInfoManager;
    }

}