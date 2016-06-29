package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.interfaces.TransactionExecutor;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure.executors.IncomingIntraUserCryptoBasicWalletTransactionExecutor;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

/**
 * Created by eze on 2015.09.11..
 */
public class IncomingIntraUserTransactionExecutorFactory {
    private CryptoWalletManager cryptoWalletManager;
    private BitcoinLossProtectedWalletManager lossProtectedWalletManager;
    private CryptoAddressBookManager cryptoAddressBookManager;
    private EventManager eventManager;
    private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;


    public IncomingIntraUserTransactionExecutorFactory(final CryptoWalletManager cryptoWalletManager, final CryptoAddressBookManager cryptoAddressBookManager,EventManager eventManager,CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager,BitcoinLossProtectedWalletManager lossProtectedWalletManager){
        this.cryptoWalletManager = cryptoWalletManager;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.eventManager             = eventManager;
        this.cryptoTransmissionNetworkServiceManager = cryptoTransmissionNetworkServiceManager;
        this.lossProtectedWalletManager = lossProtectedWalletManager;

    }

    public TransactionExecutor newTransactionExecutor(final ReferenceWallet walletType, final String walletPublicKey) throws CantLoadWalletsException {
        try {
            switch (walletType) {
                case BASIC_WALLET_BITCOIN_WALLET:
                    return createBitcoinBasicWalletExecutor(walletPublicKey);
                case BASIC_WALLET_FERMAT_WALLET:
                    return createBitcoinBasicWalletExecutor(walletPublicKey);
                case BASIC_WALLET_LOSS_PROTECTED_WALLET:
                    return createBitcoinBasicLossProtectedWalletExecutor(walletPublicKey);
                default:
                    return null;
            }
        } catch (CantLoadWalletsException e) {
            throw e;
        } catch (Exception e) {
            throw new CantLoadWalletsException("An Unexpected Exception Happened", FermatException.wrapException(e),"","");
        }

    }

    private TransactionExecutor createBitcoinBasicWalletExecutor(final String walletPublicKey) throws CantLoadWalletsException {
        return new IncomingIntraUserCryptoBasicWalletTransactionExecutor(cryptoWalletManager.
                loadWallet(walletPublicKey),
                this.cryptoAddressBookManager,
                this.eventManager,
                cryptoTransmissionNetworkServiceManager);
    }


    private TransactionExecutor createBitcoinBasicLossProtectedWalletExecutor(final String walletPublicKey) throws CantLoadWalletException {
        return new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure.executors.IncomingIntraUserLossProtectedBasicWalletTransactionExecutor(
                lossProtectedWalletManager.loadWallet(walletPublicKey),
                this.cryptoAddressBookManager,
                this.eventManager,
                cryptoTransmissionNetworkServiceManager);
    }

}
