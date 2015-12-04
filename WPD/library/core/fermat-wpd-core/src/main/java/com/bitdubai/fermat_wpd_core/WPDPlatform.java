package com.bitdubai.fermat_wpd_core;

import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_wpd_core.layer.engine.EngineLayer;
import com.bitdubai.fermat_wpd_core.layer.identity.IdentityLayer;
import com.bitdubai.fermat_wpd_core.layer.middleware.MiddlewareLayer;
import com.bitdubai.fermat_wpd_core.layer.network_service.NetworkServiceLayer;
import com.bitdubai.fermat_wpd_core.layer.sub_app_module.SubAppModuleLayer;

/**
 * The class <code>WPDPlatform</code>
 * contains all the necessary business logic to start the WPD platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 12/11/2015.
 */
public class WPDPlatform extends AbstractPlatform {

    public WPDPlatform() {
        super(new PlatformReference(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {

            registerLayer(new EngineLayer()        );
            registerLayer(new IdentityLayer()      );
            registerLayer(new MiddlewareLayer()    );
            registerLayer(new NetworkServiceLayer());
            registerLayer(new SubAppModuleLayer()  );

        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "WPD Platform.",
                    "Problem trying to register a layer."
            );
        }
    }
}
