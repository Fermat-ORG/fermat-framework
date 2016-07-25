package com.bitdubai.fermat_osa_android_core.layer.system.plugin_file_system;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractAddonSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.PluginFileSystemDeveloperBitDubai;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 27/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PluginFileSystemAddonSubsystem extends AbstractAddonSubsystem {

    public PluginFileSystemAddonSubsystem() {
        super(new AddonReference(Addons.PLUGIN_FILE_SYSTEM));
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            registerDeveloper(new PluginFileSystemDeveloperBitDubai());
        } catch (Exception e) {
            System.err.println(new StringBuilder().append("Exception: ").append(e.getMessage()).toString());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
