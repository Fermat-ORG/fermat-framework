package com.bitdubai.fermat_wpd_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_wpd_core.layer.identity.IdentityLayer;
import com.bitdubai.fermat_wpd_core.layer.sub_app_module.SubAppModuleLayer;
import com.bitdubai.fermat_wpd_core.layer.wallet_module.WalletModuleLayer;

/**
 * The class <code>WPDPlatform</code>
 * contains all the necessary business logic to start the WPD platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 12/11/2015.
 */
public class WPDPlatform extends AbstractPlatform {

    public WPDPlatform() {
        super(new PlatformReference(Platforms.CRYPTO_BROKER_PLATFORM));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {

            registerLayer(new IdentityLayer() );
            registerLayer(new SubAppModuleLayer() );

        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "",
                    "Problem trying to register a layer."
            );
        }
    }
}
