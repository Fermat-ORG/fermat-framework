package com.bitdubai.fermat_ccp_core.layer.transaction;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartLayerException;
import com.bitdubai.fermat_ccp_core.layer.transaction.outgoing_intra_actor.OutgoingIntraActorSubsystem;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CCPPlugins;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class TransactionLayer extends AbstractLayer {

    @Override
    public void start() throws CantStartLayerException {

        addPlugin(
                CCPPlugins.BITDUBAI_CRYPTO_PAYMENT_REQUEST,
                new OutgoingIntraActorSubsystem()
        );

    }

}
