package com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionSender;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.currency_vault.CryptoVaultManager;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantIdentifyEventSourceException;

/**
 * Created by eze on 11/06/15.
 */

/*
 * As the number of transaction senders will increase, this class will be in charge of
 * returning the MonitorAgent the TransactionSender that corresponds to the event source
 * that notifies new transactions
 */
public class SourceAdministrator {

    private final TransactionSender bitcoinNetworkManager;
    private final TransactionSender fermatNetworkManager;
    private final CryptoVaultManager cryptoVaultManager   ;

  public SourceAdministrator(TransactionSender bitcoinNetworkManager,
                             TransactionSender fermatNetworkManager,
                             CryptoVaultManager cryptoVaultManager   ) {

        this.bitcoinNetworkManager = bitcoinNetworkManager;
      this.fermatNetworkManager = fermatNetworkManager;
        this.cryptoVaultManager    = cryptoVaultManager   ;
  }

  public TransactionProtocolManager<CryptoTransaction> getSourceAdministrator(EventSource eventSource) throws CantIdentifyEventSourceException {
        // This method will select the correct sender according to the specified source,
        switch (eventSource) {
            case CRYPTO_NETWORK_BITCOIN_PLUGIN: return bitcoinNetworkManager.getTransactionManager();
            case CRYPTO_NETWORK_FERMAT_PLUGIN: return fermatNetworkManager.getTransactionManager();
          default:
            throw new CantIdentifyEventSourceException("I could not determine the transaction manager of this source",null,"Source: " +eventSource.name()+"with code: "+eventSource.getCode(),"Source not considered in switch statement");
        }
  }
}
