package com.bitdubai.fermat_core.layer.ccp.transaction;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_ccp_api.layer.transaction.CantStartSubsystemException;
import com.bitdubai.fermat_ccp_api.layer.transaction.TransactionSubsystem;
import com.bitdubai.fermat_core.layer.ccp.transaction.outgoing.intra_actor.OutgoingIntraActorSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CCPTransactionLayer implements PlatformLayer {

    private Plugin mOutgoingIntraActor;

    @Override
    public void start() throws CantStartLayerException {
        mOutgoingIntraActor = getPlugin(new OutgoingIntraActorSubsystem());
    }

    private Plugin getPlugin(TransactionSubsystem transactionSubsystem) throws CantStartLayerException {
        try {
            transactionSubsystem.start();
            return transactionSubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getOutgoingIntraActorPlugin() {
        return mOutgoingIntraActor;
    }

}
