package com.bitdubai.fermat_osa_android_core.layer.system.plugin_database_system;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractAddonSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.PluginDatabaseSystemDeveloperBitDubai;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 26/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PluginDatabaseSystemAddonSubsystem extends AbstractAddonSubsystem {

    public PluginDatabaseSystemAddonSubsystem() {
        super(new AddonReference(Addons.PLUGIN_DATABASE_SYSTEM));
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            registerDeveloper(new PluginDatabaseSystemDeveloperBitDubai());
        } catch (Exception e) {
            System.err.println(new StringBuilder().append("Exception: ").append(e.getMessage()).toString());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
