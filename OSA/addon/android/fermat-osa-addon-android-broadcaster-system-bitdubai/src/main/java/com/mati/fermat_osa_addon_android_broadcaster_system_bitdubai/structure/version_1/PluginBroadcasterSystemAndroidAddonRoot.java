package com.mati.fermat_osa_addon_android_broadcaster_system_bitdubai.structure.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.AndroidCoreUtils;
import com.mati.fermat_osa_addon_android_broadcaster_system_bitdubai.structure.version_1.structure.AndroidPluginBroadcaster;


/**
 * This addon handles a layer of file representation.
 * Encapsulates all the necessary functions to manage files.
 * For interfaces PluginFile the modules need to authenticate with their plugin ids (ownerId).
 * <p/>
 * Created by lnacosta (laion.cj91@gmail.com) on 27/10/2015.
 */

public class PluginBroadcasterSystemAndroidAddonRoot extends AbstractAddon {

    private final AndroidCoreUtils androidCoreUtils;
    private FermatManager fermatManager;

    public PluginBroadcasterSystemAndroidAddonRoot(AndroidCoreUtils androidCoreUtils) {
        super(new AddonVersionReference(new Version()));
        this.androidCoreUtils = androidCoreUtils;
    }

    @Override
    public final void start() throws CantStartPluginException {

        try {

            fermatManager = new AndroidPluginBroadcaster(androidCoreUtils);

            super.start();

        } catch (final Exception e) {

            throw new CantStartPluginException(e, "Plugin File System Manager starting.", "Unhandled Exception trying to start the Plugin File System manager.");
        }
    }

    public final FermatManager getManager() {
        return fermatManager;
    }

}
