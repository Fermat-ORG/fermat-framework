package com.bitdubai.fermat_bch_core;

import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_bch_core.layer.crypto_module.CryptoModuleLayer;
import com.bitdubai.fermat_bch_core.layer.crypto_network.CryptoNetworkLayer;
import com.bitdubai.fermat_bch_core.layer.crypto_router.CryptoRouterLayer;
import com.bitdubai.fermat_bch_core.layer.crypto_vault.CryptoVaultLayer;
import com.bitdubai.fermat_bch_core.layer.middleware.MiddlewareLayer;

/**
 * The class <code>com.bitdubai.fermat_bch_core.BCHPlatform</code>
 * haves all the necessary business logic to start the BCH platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/10/2015.
 */
public class BCHPlatform extends AbstractPlatform {

    public BCHPlatform() {
        super(new PlatformReference(Platforms.BLOCKCHAINS));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {

            registerLayer(new CryptoModuleLayer() );
            registerLayer(new CryptoNetworkLayer());
            registerLayer(new CryptoRouterLayer() );
            registerLayer(new CryptoVaultLayer()  );
            registerLayer(new MiddlewareLayer()   );

        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "",
                    "Problem trying to register a layer."
            );
        }
    }
}
