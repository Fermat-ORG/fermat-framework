/*
* @#PluginDatabaseSystemDeveloperBitDubai.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddonDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAddonDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.PluginDatabaseSystemLinuxAddonRoot;

/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.PluginDatabaseSystemDeveloperBitDubai</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 10/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PluginDatabaseSystemDeveloperBitDubai extends AbstractAddonDeveloper {


    public PluginDatabaseSystemDeveloperBitDubai() {
        super(new AddonDeveloperReference(Developers.BITDUBAI));
    }

    @Override
    public void start() throws CantStartAddonDeveloperException {

        System.out.println("PluginDatabaseSystemDeveloperBitDubai - start()");

        try {

            this.registerVersion(new PluginDatabaseSystemLinuxAddonRoot());

        } catch (CantRegisterVersionException e) {

            throw new CantStartAddonDeveloperException(e, "", "Error registering addon versions for the developer.");
        }

    }
}
