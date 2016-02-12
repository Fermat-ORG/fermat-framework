package com.bitdubai.fermat_osa_linux_core.layer.system;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterAddonException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_osa_linux_core.layer.system.platform_database_system.PlatformDatabaseSystemAddonSubsystem;
import com.bitdubai.fermat_osa_linux_core.layer.system.platform_file_system.PlatformFileSystemAddonSubsystem;
import com.bitdubai.fermat_osa_linux_core.layer.system.plugin_database_system.PluginDatabaseSystemAddonSubsystem;
import com.bitdubai.fermat_osa_linux_core.layer.system.plugin_file_system.PluginFileSystemAddonSubsystem;

/**
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/12/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class SystemLayer extends AbstractLayer {

    public SystemLayer() {
        super(Layers.SYSTEM);
    }

    public void start() throws CantStartLayerException {

        System.out.println("SystemLayer - start()");

        /* register addons here */

        try {

           registerAddon(new PlatformDatabaseSystemAddonSubsystem());
           registerAddon(new PluginDatabaseSystemAddonSubsystem());
           registerAddon(new PlatformFileSystemAddonSubsystem());
           registerAddon(new PluginFileSystemAddonSubsystem());

      } catch(CantRegisterAddonException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register an addon in android layer of osa platform."
            );
        }
    }

}
