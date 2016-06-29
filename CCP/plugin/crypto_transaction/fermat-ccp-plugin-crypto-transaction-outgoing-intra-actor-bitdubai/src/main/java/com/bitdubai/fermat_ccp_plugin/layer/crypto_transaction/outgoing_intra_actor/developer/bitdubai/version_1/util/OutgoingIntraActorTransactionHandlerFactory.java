package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.interfaces.OutgoingIntraActorTransactionHandler;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure.transactionHandlers.OutgoingIntraActorBitcoinWalletTransactionHandler;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.database.OutgoingIntraActorDao;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantFindHandlerException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure.transactionHandlers.OutgoingIntraActorLossProtectedWalletTransactionHandler;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

/**
 * Created by eze on 2015.09.21..
 */
public class OutgoingIntraActorTransactionHandlerFactory {

    private EventManager         eventManager;
    private CryptoWalletManager cryptoWalletManager;
    private OutgoingIntraActorDao outgoingIntraActorDao;
    private BitcoinLossProtectedWalletManager bitcoinLossProtectedWalletManager;

    public OutgoingIntraActorTransactionHandlerFactory(EventManager eventManager,
                                                       CryptoWalletManager cryptoWalletManager,
                                                       OutgoingIntraActorDao outgoingIntraActorDao,
                                                       BitcoinLossProtectedWalletManager bitcoinLossProtectedWalletManager) {
        this.eventManager         = eventManager;
        this.cryptoWalletManager = cryptoWalletManager;
        this.outgoingIntraActorDao = outgoingIntraActorDao;
        this.bitcoinLossProtectedWalletManager = bitcoinLossProtectedWalletManager;
    }

    public OutgoingIntraActorTransactionHandler getHandler(ReferenceWallet referenceWallet) throws OutgoingIntraActorCantFindHandlerException {
        switch (referenceWallet) {
            case BASIC_WALLET_BITCOIN_WALLET:
                return new OutgoingIntraActorBitcoinWalletTransactionHandler(this.eventManager,this.cryptoWalletManager,this.outgoingIntraActorDao);

            case BASIC_WALLET_FERMAT_WALLET:
                return new OutgoingIntraActorBitcoinWalletTransactionHandler(this.eventManager,this.cryptoWalletManager,this.outgoingIntraActorDao);

            case BASIC_WALLET_LOSS_PROTECTED_WALLET:
                return new OutgoingIntraActorLossProtectedWalletTransactionHandler(this.eventManager,this.bitcoinLossProtectedWalletManager,this.outgoingIntraActorDao);

            default:
                throw new OutgoingIntraActorCantFindHandlerException("No handler was found",null,"","");
        }
    }
}
