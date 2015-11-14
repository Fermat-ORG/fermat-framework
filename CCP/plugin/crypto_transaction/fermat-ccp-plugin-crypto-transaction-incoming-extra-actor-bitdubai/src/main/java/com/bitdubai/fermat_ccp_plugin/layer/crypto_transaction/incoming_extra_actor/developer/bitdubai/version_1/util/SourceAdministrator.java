package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;

import com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;

/**
 * Created by eze on 11/06/15.
 */

/*
 * As the number of transaction senders will increase, this class will be in charge of
 * returning the MonitorAgent the TransactionSender that corresponds to the event source
 * that notifies new transactions
 */
public class SourceAdministrator  {

    private final IncomingCryptoManager incomingCryptoManager;

    public SourceAdministrator(final IncomingCryptoManager incomingCryptoManager) {

        this.incomingCryptoManager = incomingCryptoManager;
    }

    public TransactionProtocolManager<CryptoTransaction> getSourceAdministrator(final EventSource eventSource) throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.SourceNotRecognizedException {
        try {
            // This method will select the correct sender according to the specified source,
            switch (eventSource) {
                case CRYPTO_ROUTER:
                    return incomingCryptoManager.getTransactionManager();
                default:
                    String exceptionMessage = "There is no administrator for this source";
                    String context = "The event source was: " + eventSource.name() + "with code " + eventSource.getCode();
                    String possibleCause = "Value not considered in switch statement";
                    throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.SourceNotRecognizedException(exceptionMessage, null, context, possibleCause);
            }
        } catch (com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.SourceNotRecognizedException e) {
            throw e;
        } catch (Exception e) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.SourceNotRecognizedException("Unexpected Exception", FermatException.wrapException(e),"","");
        }
    }
}
