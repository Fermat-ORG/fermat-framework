package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Leon Acosta (lnacosta) - (laion.cj91@gmail.com) on 16/01/2015.
 */

public class MatchingEngineMiddlewarePluginRoot extends AbstractPlugin {

    @NeededAddonReference (platform = Platforms.PLUG_INS_PLATFORM     , layer = Layers.PLATFORM_SERVICE, addon  = Addons .ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference (platform = Platforms.PLUG_INS_PLATFORM     , layer = Layers.PLATFORM_SERVICE, addon  = Addons .EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference (platform = Platforms.OPERATIVE_SYSTEM_API  , layer = Layers.SYSTEM          , addon  = Addons .PLUGIN_FILE_SYSTEM    )
    protected PluginFileSystem pluginFileSystem        ;

    @NeededAddonReference (platform = Platforms.OPERATIVE_SYSTEM_API  , layer = Layers.SYSTEM          , addon  = Addons .PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;


    public MatchingEngineMiddlewarePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public FermatManager getManager() {
        return null;
    }

}
