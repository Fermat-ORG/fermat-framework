package com.bitdubai.fermat_ccp_core.layer.wallet_module;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartLayerException;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CCPPlugins;
import com.bitdubai.fermat_ccp_core.layer.transaction.incoming_extra_user.IncomingExtraUserSubsystem;
import com.bitdubai.fermat_ccp_core.layer.transaction.incoming_intra_user.IncomingIntraUserSubsystem;
import com.bitdubai.fermat_ccp_core.layer.transaction.outgoing_extra_user.OutgoingExtraUserSubsystem;
import com.bitdubai.fermat_ccp_core.layer.transaction.outgoing_intra_actor.OutgoingIntraActorSubsystem;
import com.bitdubai.fermat_ccp_core.layer.wallet_module.crypto.CryptoSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletModuleLayer extends AbstractLayer {

    @Override
    public void start() throws CantStartLayerException {

        addPlugin(CCPPlugins.BITDUBAI_CRYPTO_WALLET_MODULE , new CryptoSubsystem() );

    }

}
