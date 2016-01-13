package com.bitdubai.fermat_cht_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_cht_core.layer.middleware.MiddlewareLayer;
import com.bitdubai.fermat_cht_core.layer.network_service.NetworkServiceLayer;
import com.bitdubai.fermat_cht_core.layer.sup_app_module.SupAppModuleLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartPlatformException;

/**
 * Haves all the necessary business logic to start the CHT platform.
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 22/12/15.
 */
public class CHTPlatform extends AbstractPlatform {

    public CHTPlatform() {
        super(new PlatformReference(Platforms.CHAT_PLATFORM));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {

            registerLayer(new MiddlewareLayer());
            //registerLayer(new NetworkServiceLayer());
            registerLayer(new SupAppModuleLayer());

        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "CHT Platform.",
                    "Problem trying to register a layer."
            );
        }
    }
}
