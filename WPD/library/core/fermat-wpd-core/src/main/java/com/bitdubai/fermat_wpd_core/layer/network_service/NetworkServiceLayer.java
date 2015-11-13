package com.bitdubai.fermat_wpd_core.layer.network_service;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_wpd_core.layer.network_service.wallet_community.WalletCommunityPluginSubsystem;
import com.bitdubai.fermat_wpd_core.layer.network_service.wallet_resources.WalletResourcesPluginSubsystem;
import com.bitdubai.fermat_wpd_core.layer.network_service.wallet_statistics.WalletStatisticsPluginSubsystem;
import com.bitdubai.fermat_wpd_core.layer.network_service.wallet_store.WalletStorePluginSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 12/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkServiceLayer extends AbstractLayer {

    public NetworkServiceLayer() {
        super(Layers.NETWORK_SERVICE);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new WalletCommunityPluginSubsystem( ));
            registerPlugin(new WalletResourcesPluginSubsystem() );
            registerPlugin(new WalletStatisticsPluginSubsystem());
            registerPlugin(new WalletStorePluginSubsystem()     );

        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "Network Service Layer of WPD Platform.",
                    "Problem trying to register a plugin."
            );
        }
    }

}
