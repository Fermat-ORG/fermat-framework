package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionSender;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;

/**
 * Created by eze on 11/06/15.
 */

/*
 * As the number of transaction senders will increase, this class will be in charge of
 * returning the MonitorAgent the TransactionSender that corresponds to the event source
 * that notifies new transactions
 */
public class SourceAdministrator implements DealsWithCryptoVault {

  private CryptoVaultManager cryptoVaultManager;

  @Override
  public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) {
    this.cryptoVaultManager = cryptoVaultManager;
  }

  public TransactionProtocolManager<CryptoTransaction> getSourceAdministrator(EventSource eventSource){
        // This method will select the correct sender according to the specified source,
        return cryptoVaultManager.getTransactionManager();
  }
}
