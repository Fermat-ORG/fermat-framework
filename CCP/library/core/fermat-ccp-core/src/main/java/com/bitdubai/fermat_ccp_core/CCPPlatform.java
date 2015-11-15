package com.bitdubai.fermat_ccp_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_ccp_core.layer.actor.ActorLayer;
import com.bitdubai.fermat_ccp_core.layer.basic_wallet.BasicWalletLayer;
import com.bitdubai.fermat_ccp_core.layer.desktop_module.DesktopModuleLayer;
import com.bitdubai.fermat_ccp_core.layer.identity.IdentityLayer;
import com.bitdubai.fermat_ccp_core.layer.middleware.MiddlewareLayer;
import com.bitdubai.fermat_ccp_core.layer.module.ModuleLayer;
import com.bitdubai.fermat_ccp_core.layer.network_service.NetworkServiceLayer;
import com.bitdubai.fermat_ccp_core.layer.request.RequestLayer;
import com.bitdubai.fermat_ccp_core.layer.crypto_transaction.TransactionLayer;
import com.bitdubai.fermat_ccp_core.layer.wallet_module.WalletModuleLayer;

/**
 * The class <code>com.bitdubai.fermat_ccp_core.CCPPlatform</code>
 * contains all the necessary business logic to start the CCP platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public class CCPPlatform extends AbstractPlatform {

    public CCPPlatform() {
        super(new PlatformReference(Platforms.CRYPTO_CURRENCY_PLATFORM));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {

            registerLayer(new ActorLayer()         );
            registerLayer(new BasicWalletLayer()   );
            registerLayer(new DesktopModuleLayer() );
            registerLayer(new IdentityLayer()      );
            registerLayer(new MiddlewareLayer()    );
            registerLayer(new ModuleLayer()        );
            registerLayer(new NetworkServiceLayer());
            registerLayer(new RequestLayer()       );
            registerLayer(new TransactionLayer()   );
            registerLayer(new WalletModuleLayer());

        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "",
                    "Problem trying to register a layer."
            );
        }
    }
}
