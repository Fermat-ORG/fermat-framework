package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;

import com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto.DealsWithIncomingCrypto;
import com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions.SourceNotRecognizedException;

/**
 * Created by eze on 11/06/15.
 */

/*
 * As the number of transaction senders will increase, this class will be in charge of
 * returning the MonitorAgent the TransactionSender that corresponds to the event source
 * that notifies new transactions
 */
public class SourceAdministrator implements DealsWithIncomingCrypto {

    private IncomingCryptoManager incomingCryptoManager;

    public TransactionProtocolManager<CryptoTransaction> getSourceAdministrator(final EventSource eventSource) throws SourceNotRecognizedException {
        // This method will select the correct sender according to the specified source,
        switch (eventSource) {
            case CRYPTO_ROUTER:
                return incomingCryptoManager.getTransactionManager();
            default:
                String exceptionMessage = "There is no administrator for this source";
                String context = "The event source was: "+ eventSource.name() + "with code " + eventSource.getCode();
                String possibleCause = "Value not considered in switch statement";
                throw new SourceNotRecognizedException(exceptionMessage,null,context,possibleCause);
        }
    }

    @Override
    public void setIncomingCryptoManager(final IncomingCryptoManager incomingCryptoManager) {
        this.incomingCryptoManager = incomingCryptoManager;
    }
}
