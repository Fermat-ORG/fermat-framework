package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.interfaces.TransactionExecutor;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

/**
 * Created by eze on 2015.09.11..
 */
public class IncomingIntraUserTransactionExecutorFactory {
    private BitcoinWalletManager     bitcoinWalletManager;
    private CryptoAddressBookManager cryptoAddressBookManager;
    private EventManager eventManager;


    public IncomingIntraUserTransactionExecutorFactory(final BitcoinWalletManager bitcoinWalletManager, final CryptoAddressBookManager cryptoAddressBookManager,EventManager eventManager){
        this.bitcoinWalletManager     = bitcoinWalletManager;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.eventManager             = eventManager;

    }

    public TransactionExecutor newTransactionExecutor(final ReferenceWallet walletType, final String walletPublicKey) throws CantLoadWalletException {
        try {
            switch (walletType) {
                case BASIC_WALLET_BITCOIN_WALLET:
                    return createBitcoinBasicWalletExecutor(walletPublicKey);
                default:
                    return null;
            }
        } catch (CantLoadWalletException e) {
            throw e;
        } catch (Exception e) {
            throw new CantLoadWalletException("An Unexpected Exception Happened", FermatException.wrapException(e),"","");
        }

    }

    private TransactionExecutor createBitcoinBasicWalletExecutor(final String walletPublicKey) throws CantLoadWalletException {
        return new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure.executors.IncomingIntraUserBitcoinBasicWalletTransactionExecutor(bitcoinWalletManager.loadWallet(walletPublicKey), this.cryptoAddressBookManager, this.eventManager);
    }
}
