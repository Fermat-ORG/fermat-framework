package com.bitdubai.fermat_pip_addon.layer.platform_service.event_manager.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_pip_addon.layer.platform_service.event_manager.developer.bitdubai.version_1.structure.EventManagerPlatformServiceManager;

/**
 * This addon is the responsible to manage all the events in fermat platform.
 * <p/>
 * Created by by Leon Acosta (laion.cj91@gmail.com) on 22-08-2015.
 */
public final class EventManagerPlatformServiceAddonRoot extends AbstractAddon {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;


    private FermatManager eventManager;

    public EventManagerPlatformServiceAddonRoot() {
        super(new AddonVersionReference(new Version()));
    }

    @Override
    public final void start() throws CantStartPluginException {

        try {

            eventManager = new EventManagerPlatformServiceManager(errorManager);

            super.start();

        } catch (final Exception e) {

            throw new CantStartPluginException(e, "Event Manager starting.", "Unhandled Exception trying to start the event manager.");
        }
    }

    @Override
    public final FermatManager getManager() {
        return eventManager;
    }

}
