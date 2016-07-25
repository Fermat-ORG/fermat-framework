package com.bitdubai.fermat_osa_addon.layer.linux.file_system.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_osa_addon.layer.linux.file_system.developer.bitdubai.version_1.structure.LinuxPluginFileSystem;

/**
 * This addon handles a layer of file representation.
 * Encapsulates all the necessary functions to manage files.
 * For interfaces PluginFile the modules need to authenticate with their plugin ids (ownerId).
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/12/2015.
 */

public class PluginFileSystemLinuxAddonRoot extends AbstractAddon {

    private FermatManager fermatManager;

    public PluginFileSystemLinuxAddonRoot() {
        super(new AddonVersionReference(new Version()));
    }

    @Override
    public final void start() throws CantStartPluginException {

        System.out.println("PluginFileSystemLinuxAddonRoot - start()");

        try {

            fermatManager = new LinuxPluginFileSystem();

            super.start();

        } catch (final Exception e) {

            throw new CantStartPluginException(e, "Plugin File System Manager starting.", "Unhandled Exception trying to start the Plugin File System manager.");
        }
    }

    public final FermatManager getManager() {
        return fermatManager;
    }

}
