package com.bitdubai.fermat_dap_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_dap_core.layer.actor.ActorLayer;
import com.bitdubai.fermat_dap_core.layer.actor_network_service.ActorNetworkServiceLayer;
import com.bitdubai.fermat_dap_core.layer.identity.IdentityLayer;
import com.bitdubai.fermat_dap_core.layer.middleware.MiddlewareLayer;
import com.bitdubai.fermat_dap_core.layer.network_service.NetworkServiceLayer;
import com.bitdubai.fermat_dap_core.layer.wallet.WalletLayer;
import com.bitdubai.fermat_dap_core.layer.wallet_module.WalletModuleLayer;

/**
 * The class <code>com.bitdubai.fermat_dap_core.DAPPlatform</code>
 * This core has the logic to instance all the modules of the DAP Platform.
 * <p/>
 * Created by PatricioGesualdi - (pmgesualdi@hotmail.com) on 11/10/2015.
 */
public final class DAPPlatform extends AbstractPlatform {

    public DAPPlatform() {
        super(new PlatformReference(Platforms.DIGITAL_ASSET_PLATFORM));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {

            registerLayer(new ActorLayer()              );
            registerLayer(new ActorNetworkServiceLayer());
            registerLayer(new IdentityLayer()           );
            registerLayer(new MiddlewareLayer()         );
            registerLayer(new NetworkServiceLayer()     );
            registerLayer(new WalletLayer()             );
            registerLayer(new WalletModuleLayer()       );

        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "",
                    "Problem trying to register a layer in DAP Platform."
            );
        }
    }
}
