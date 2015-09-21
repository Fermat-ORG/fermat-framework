package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

/**
 * Created by eze on 2015.09.19..
 */
public class OutgoingIntraUserTransactionProcessorAgent {

    private ErrorManager                            errorManager;
    private CryptoVaultManager                      cryptoVaultManager;
    private BitcoinWalletManager                    bitcoinWalletManager;
    // TODO: Add **AND SET** dao
    private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;

    public OutgoingIntraUserTransactionProcessorAgent(final ErrorManager                            errorManager,
                                                      final CryptoVaultManager                      cryptoVaultManager,
                                                      final BitcoinWalletManager                    bitcoinWalletManager,
                                                      final CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager) {

        this.errorManager                            = errorManager;
        this.cryptoVaultManager                      = cryptoVaultManager;
        this.bitcoinWalletManager                    = bitcoinWalletManager;
        this.cryptoTransmissionNetworkServiceManager = cryptoTransmissionNetworkServiceManager;
    }
}
