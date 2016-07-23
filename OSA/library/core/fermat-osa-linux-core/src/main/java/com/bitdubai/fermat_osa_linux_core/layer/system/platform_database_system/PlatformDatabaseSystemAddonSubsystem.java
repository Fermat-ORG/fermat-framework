package com.bitdubai.fermat_osa_linux_core.layer.system.platform_database_system;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractAddonSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.PlatformDatabaseSystemDeveloperBitDubai;

/**
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/12/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PlatformDatabaseSystemAddonSubsystem extends AbstractAddonSubsystem {

    public PlatformDatabaseSystemAddonSubsystem() {
        super(new AddonReference(Addons.PLATFORM_DATABASE_SYSTEM));
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            registerDeveloper(new PlatformDatabaseSystemDeveloperBitDubai());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
