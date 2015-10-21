package com.bitdubai.fermat_ccp_core.layer.transaction;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartLayerException;
import com.bitdubai.fermat_ccp_core.layer.transaction.incoming_extra_user.IncomingExtraUserSubsystem;
import com.bitdubai.fermat_ccp_core.layer.transaction.incoming_intra_user.IncomingIntraUserSubsystem;
import com.bitdubai.fermat_ccp_core.layer.transaction.outgoing_extra_user.OutgoingExtraUserSubsystem;
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

        addPlugin(CCPPlugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION , new IncomingExtraUserSubsystem() );
        addPlugin(CCPPlugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION , new IncomingIntraUserSubsystem() );
        addPlugin(CCPPlugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION , new OutgoingExtraUserSubsystem() );
        addPlugin(CCPPlugins.BITDUBAI_OUTGOING_INTRA_ACTOR_TRANSACTION, new OutgoingIntraActorSubsystem());

    }

}
