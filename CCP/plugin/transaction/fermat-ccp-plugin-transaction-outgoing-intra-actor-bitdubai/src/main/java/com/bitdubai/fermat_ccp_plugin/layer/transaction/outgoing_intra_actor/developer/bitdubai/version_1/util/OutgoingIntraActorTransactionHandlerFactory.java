package com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_intra_actor.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_intra_actor.developer.bitdubai.version_1.interfaces.OutgoingIntraActorTransactionHandler;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure.transactionHandlers.OutgoingIntraActorBitcoinWalletTransactionHandler;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_intra_actor.developer.bitdubai.version_1.database.OutgoingIntraActorDao;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantFindHandlerException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

/**
 * Created by eze on 2015.09.21..
 */
public class OutgoingIntraActorTransactionHandlerFactory {

    private EventManager         eventManager;
    private BitcoinWalletManager bitcoinWalletManager;
    private OutgoingIntraActorDao outgoingIntraActorDao;

    public OutgoingIntraActorTransactionHandlerFactory(EventManager eventManager,
                                                       BitcoinWalletManager bitcoinWalletManager,
                                                       OutgoingIntraActorDao outgoingIntraActorDao) {
        this.eventManager         = eventManager;
        this.bitcoinWalletManager = bitcoinWalletManager;
        this.outgoingIntraActorDao = outgoingIntraActorDao;
    }

    public OutgoingIntraActorTransactionHandler getHandler(ReferenceWallet referenceWallet) throws OutgoingIntraActorCantFindHandlerException {
        switch (referenceWallet) {
            case BASIC_WALLET_BITCOIN_WALLET:
                return new OutgoingIntraActorBitcoinWalletTransactionHandler(this.eventManager,this.bitcoinWalletManager,this.outgoingIntraActorDao);
            default:
                throw new OutgoingIntraActorCantFindHandlerException("No handler was found",null,"","");
        }
    }
}
