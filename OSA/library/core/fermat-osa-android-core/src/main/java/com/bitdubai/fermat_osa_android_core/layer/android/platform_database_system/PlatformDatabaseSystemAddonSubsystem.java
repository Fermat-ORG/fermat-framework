package com.bitdubai.fermat_osa_android_core.layer.android.platform_database_system;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractAddonSubsystem;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.PlatformDatabaseSystemDeveloperBitDubai;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 26/10/2015.
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
