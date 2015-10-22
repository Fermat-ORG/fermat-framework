package com.bitdubai.fermat_ccp_core.layer.middleware;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartLayerException;
import com.bitdubai.fermat_ccp_core.layer.middleware.wallet_contacts.WalletContactsPluginSubsystem;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CCPPlugins;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MiddlewareLayer extends AbstractLayer {

    public void start() throws CantStartLayerException {

        registerPlugin(
                CCPPlugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE,
                new WalletContactsPluginSubsystem()
        );

    }

}
