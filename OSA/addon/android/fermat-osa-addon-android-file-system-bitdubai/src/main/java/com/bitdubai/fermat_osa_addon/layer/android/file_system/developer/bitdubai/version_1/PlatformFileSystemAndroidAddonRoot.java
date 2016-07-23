package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1;

import android.content.Context;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededOsContext;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPlatformFileSystem;

/**
 * This addon handles a layer of file representation.
 * Encapsulates all the necessary functions to manage files.
 * For interfaces PluginFile the modules need to authenticate with their plugin ids
 * <p/>
 * Created by lnacosta (laion.cj91@gmail.com) on 27/10/2015.
 */
public class PlatformFileSystemAndroidAddonRoot extends AbstractAddon {


    @NeededOsContext
    private Context context;

    private FermatManager fermatManager;

    public PlatformFileSystemAndroidAddonRoot() {
        super(new AddonVersionReference(new Version()));
    }

    @Override
    public final void start() throws CantStartPluginException {

        try {

            fermatManager = new AndroidPlatformFileSystem(context);

            super.start();

        } catch (final Exception e) {

            throw new CantStartPluginException(e, "Platform File System Manager starting.", "Unhandled Exception trying to start the Platform File System manager.");
        }
    }

    public final FermatManager getManager() {
        return fermatManager;
    }

}
