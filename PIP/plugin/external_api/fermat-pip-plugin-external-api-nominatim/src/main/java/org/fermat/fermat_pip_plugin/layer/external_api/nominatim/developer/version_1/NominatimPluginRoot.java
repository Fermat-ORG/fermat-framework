package org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.structure.NominatimPluginManager;

@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM, maintainerMail = "darkpriestrelative@gmail.com", createdBy = "darkestpriest", layer = Layers.EXTERNAL_API, platform = Platforms.PLUG_INS_PLATFORM, plugin = Plugins.NOMINATIM)
public class NominatimPluginRoot extends AbstractPlugin {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    /**
     * Represents the plugin manager.
     */
    NominatimPluginManager nominatimPluginManager;

    /**
     * Constructor with parameters.
     */
    public NominatimPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * This method returns the plugin manager.
     * @return
     */
    public FermatManager getManager() {
        return this.nominatimPluginManager;
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            this.nominatimPluginManager = new NominatimPluginManager(
                    this,
                    pluginFileSystem,
                    this.pluginId);
            //Test Methods
        } catch (Exception e) {
            reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    e, "Cant start API Nominatim plugin.",
                    null);
        }
        /**
         * nothing left to do.
         */
        this.serviceStatus = ServiceStatus.STARTED;
    }

}