package com.bitdubai.fermat_osa_linux_core.layer.system;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 27/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class SystemLayer extends AbstractLayer {

    public SystemLayer() {
        super(Layers.SYSTEM);
    }

    public void start() throws CantStartLayerException {

        /* register addons here

        try {

           // registerAddon(new PlatformDatabaseSystemAddonSubsystem());
           // registerAddon(new PluginDatabaseSystemAddonSubsystem());
           // registerAddon(new PlatformFileSystemAddonSubsystem());
           // registerAddon(new PluginFileSystemAddonSubsystem());

      } catch(CantRegisterAddonException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register an addon in android layer of osa platform."
            );
        } */
    }

}
