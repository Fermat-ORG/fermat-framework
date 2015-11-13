package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.FermatCryptoTransaction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserSourceNotRecognizedException;

/**
 * Created by eze on 2015.09.03..
 */
public class IncomingIntraUserMetadataSourceAdministrator {
    private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;

    public IncomingIntraUserMetadataSourceAdministrator(final CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager){
        this.cryptoTransmissionNetworkServiceManager = cryptoTransmissionNetworkServiceManager;
    }

    public TransactionProtocolManager<FermatCryptoTransaction> getSourceAdministrator(final EventSource eventSource) throws IncomingIntraUserSourceNotRecognizedException {
        try {
            // This method will select the correct sender according to the specified source,
            switch (eventSource) {
                case NETWORK_SERVICE_CRYPTO_TRANSMISSION:
                    return cryptoTransmissionNetworkServiceManager.getTransactionManager();
                default:
                    String exceptionMessage = "There is no administrator for this source";
                    String context = "The event source was: " + eventSource.name() + "with code " + eventSource.getCode();
                    String possibleCause = "Value not considered in switch statement";
                    throw new IncomingIntraUserSourceNotRecognizedException(exceptionMessage, null, context, possibleCause);
            }
        } catch (IncomingIntraUserSourceNotRecognizedException e) {
            throw e;
        } catch (Exception e) {
            throw new IncomingIntraUserSourceNotRecognizedException("Unexpected Exception", FermatException.wrapException(e),"","");
        }
    }
}

