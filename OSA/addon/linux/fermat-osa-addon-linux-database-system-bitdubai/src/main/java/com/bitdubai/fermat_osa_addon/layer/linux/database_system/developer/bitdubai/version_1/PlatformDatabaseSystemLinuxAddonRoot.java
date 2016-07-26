/*
* @#PlatformDatabaseSystemLinuxAddonRoot.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure.DesktopPlatformDatabaseSystem;

/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.PlatformDatabaseSystemLinuxAddonRoot</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 10/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PlatformDatabaseSystemLinuxAddonRoot extends AbstractAddon {

    private FermatManager fermatManager;

    public PlatformDatabaseSystemLinuxAddonRoot() {
        super(new AddonVersionReference(new Version()));
    }

    @Override
    public final void start() throws CantStartPluginException {

        System.out.println("PlatformDatabaseSystemLinuxAddonRoot - start()");

        try {

            fermatManager = new DesktopPlatformDatabaseSystem();

            super.start();

        } catch (final Exception e) {

            throw new CantStartPluginException(e, "Platform Database System Manager starting.", "Unhandled Exception trying to start the Platform Database System manager.");
        }
    }

    @Override
    public FermatManager getManager() {
        return fermatManager;
    }

}
