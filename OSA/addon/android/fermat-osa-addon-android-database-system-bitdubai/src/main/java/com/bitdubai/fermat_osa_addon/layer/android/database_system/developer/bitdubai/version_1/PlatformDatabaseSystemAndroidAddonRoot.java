package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1;

import android.content.Context;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededOsContext;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidPlatformDatabaseSystem;

/**
 * This addon handles a layer of database representation.
 * Encapsulates all the necessary functions to manage the database , its tables and records.
 * For interfaces PluginDatabase the modules need to authenticate with their plugin ids
 * <p/>
 * Created by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */

public class PlatformDatabaseSystemAndroidAddonRoot extends AbstractAddon {

    @NeededOsContext
    private Context context;

    private FermatManager platformDatabaseSystemManager;

    /**
     * Constructor without parameters.
     */
    public PlatformDatabaseSystemAndroidAddonRoot() {
        super(new AddonVersionReference(new Version()));
    }

    @Override
    public final void start() throws CantStartPluginException {

        try {

            platformDatabaseSystemManager = new AndroidPlatformDatabaseSystem(context);

            super.start();

        } catch (final Exception e) {

            throw new CantStartPluginException(e, "Platform Database System Manager starting.", "Unhandled Exception trying to start the Platform Database System manager.");
        }
    }

    @Override
    public final FermatManager getManager() {
        return platformDatabaseSystemManager;
    }

}
