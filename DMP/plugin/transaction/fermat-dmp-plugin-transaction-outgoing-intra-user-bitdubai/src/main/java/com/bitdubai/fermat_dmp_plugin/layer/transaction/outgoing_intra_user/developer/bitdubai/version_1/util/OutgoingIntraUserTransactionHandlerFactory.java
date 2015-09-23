package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.database.OutgoingIntraUserDao;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserCantFindHandlerException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.interfaces.OutgoingIntraUserTransactionHandler;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.structure.transactionHandlers.OutgoingIntraUserBitcoinWalletTransactionHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

/**
 * Created by eze on 2015.09.21..
 */
public class OutgoingIntraUserTransactionHandlerFactory {

    private ErrorManager         errorManager;
    private BitcoinWalletManager bitcoinWalletManager;
    private OutgoingIntraUserDao outgoingIntraUserDao;

    public OutgoingIntraUserTransactionHandlerFactory(ErrorManager         errorManager,
                                                      BitcoinWalletManager bitcoinWalletManager,
                                                      OutgoingIntraUserDao outgoingIntraUserDao) {
        this.errorManager         = errorManager;
        this.bitcoinWalletManager = bitcoinWalletManager;
        this.outgoingIntraUserDao = outgoingIntraUserDao;
    }

    public OutgoingIntraUserTransactionHandler getHandler(ReferenceWallet referenceWallet) throws OutgoingIntraUserCantFindHandlerException {
        switch (referenceWallet) {
            case BASIC_WALLET_BITCOIN_WALLET:
                return new OutgoingIntraUserBitcoinWalletTransactionHandler(this.errorManager,this.bitcoinWalletManager,this.outgoingIntraUserDao);
            default:
                throw new OutgoingIntraUserCantFindHandlerException("No handler was found",null,"","");
        }
    }
}
