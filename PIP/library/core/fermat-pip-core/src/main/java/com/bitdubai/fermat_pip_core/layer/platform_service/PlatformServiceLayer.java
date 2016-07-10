package com.bitdubai.fermat_pip_core.layer.platform_service;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterAddonException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_pip_core.layer.platform_service.error_manager.ErrorManagerSubsystem;
import com.bitdubai.fermat_pip_core.layer.platform_service.event_manager.EventManagerSubsystem;
import com.bitdubai.fermat_pip_core.layer.platform_service.location_manager.LocationManagerSubsystem;
import com.bitdubai.fermat_pip_core.layer.platform_service.platform_info.PlatformInfoSubsystem;

/**
 * The class <code>com.bitdubai.fermat_pip_core.layer.platform_service.UserLayer</code>
 * haves all the necessary business logic to start the Platform Service Layer of the PIP Platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 26/10/2015.
 */
public class PlatformServiceLayer extends AbstractLayer {

    public PlatformServiceLayer() {
        super(Layers.PLATFORM_SERVICE);
    }

    public void start() throws CantStartLayerException {

        try {

            registerAddon(new ErrorManagerSubsystem());
            registerAddon(new EventManagerSubsystem());
            registerAddon(new LocationManagerSubsystem());
            registerAddon(new PlatformInfoSubsystem());

        } catch (CantRegisterAddonException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
