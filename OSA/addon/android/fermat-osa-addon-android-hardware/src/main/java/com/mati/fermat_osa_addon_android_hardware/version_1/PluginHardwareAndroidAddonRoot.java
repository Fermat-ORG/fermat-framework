package com.mati.fermat_osa_addon_android_hardware.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.mati.fermat_osa_addon_android_hardware.structure.Hardware;

/**
 * This addon handles a layer of database representation.
 * Encapsulates all the necessary functions to manage the database , its tables and records.
 * For interfaces PluginDatabase the modules need to authenticate with their plugin ids
 * <p/>
 * Created by nattyco
 * Modified by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */

public final class PluginHardwareAndroidAddonRoot extends AbstractAddon {

//    @NeededOsContext
//    private Context context;

    private Hardware hardware;

    /**
     * Constructor without parameters.
     */
    public PluginHardwareAndroidAddonRoot() {
        super(new AddonVersionReference(new Version()));
    }

    @Override
    public final void start() throws CantStartPluginException {

        try {

            hardware = new Hardware();

            super.start();

        } catch (final Exception e) {

            throw new CantStartPluginException(e, "Plugin Database System Manager starting.", "Unhandled Exception trying to start the Plugin Database System manager.");
        }
    }

    @Override
    public final FermatManager getManager() {
        return hardware;
    }
}
