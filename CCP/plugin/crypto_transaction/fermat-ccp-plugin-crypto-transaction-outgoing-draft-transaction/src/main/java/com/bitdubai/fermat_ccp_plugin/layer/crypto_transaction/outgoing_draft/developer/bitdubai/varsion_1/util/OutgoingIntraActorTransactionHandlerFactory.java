package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.varsion_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.varsion_1.database.OutgoingDraftTransactionDao;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.varsion_1.exceptions.OutgoingIntraActorCantFindHandlerException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.varsion_1.interfaces.OutgoingIntraActorTransactionHandler;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.varsion_1.util.transactionHandlers.OutgoingIntraActorBitcoinWalletTransactionHandler;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

/**
 * Created by eze on 2015.09.21..
 */
public class OutgoingIntraActorTransactionHandlerFactory {

    private EventManager         eventManager;
    private BitcoinWalletManager bitcoinWalletManager;
    private OutgoingDraftTransactionDao outgoingIntraActorDao;

    public OutgoingIntraActorTransactionHandlerFactory(EventManager eventManager,
                                                       BitcoinWalletManager bitcoinWalletManager,
                                                       OutgoingDraftTransactionDao outgoingIntraActorDao) {
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
