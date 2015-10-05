package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantConfirmRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequestManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CryptoPaymentRequestNotFoundException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.database.CryptoPaymentRequestDao;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantChangeCryptoPaymentRequestStateException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantHandleCryptoPaymentRequestApprovedEventException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantHandleCryptoPaymentRequestDeniedEventException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantHandleCryptoPaymentRequestRefusedEventException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestEventActionsException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestRegistryException;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure.CryptoPaymentRequestEventActions</code>
 * contains all the methods related with the handling of the events raised by the crypto payment request network service.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/10/2015.
 */
public class CryptoPaymentRequestEventActions {

    private final CryptoPaymentRequestManager cryptoPaymentRequestManager;
    private final PluginDatabaseSystem        pluginDatabaseSystem       ;
    private final UUID                        pluginId                   ;

    private CryptoPaymentRequestDao cryptoPaymentRequestDao;

    public CryptoPaymentRequestEventActions(final CryptoPaymentRequestManager cryptoPaymentRequestManager,
                                            final PluginDatabaseSystem        pluginDatabaseSystem       ,
                                            final UUID                        pluginId                   ) {

        this.cryptoPaymentRequestManager = cryptoPaymentRequestManager;
        this.pluginDatabaseSystem        = pluginDatabaseSystem       ;
        this.pluginId                    = pluginId                   ;
    }

    public void initialize() throws CantInitializeCryptoPaymentRequestEventActionsException {

        try {

            cryptoPaymentRequestDao = new CryptoPaymentRequestDao(pluginDatabaseSystem, pluginId);
            cryptoPaymentRequestDao.initialize();

        } catch (CantInitializeCryptoPaymentRequestDatabaseException e) {

            throw new CantInitializeCryptoPaymentRequestEventActionsException(e);
        }
    }

    public void executePendingRequestActions() {

    }

    /**
     * first, i change the state to approved.
     * then i confirm the request, to delete it in the network service.
     */
    public void handleCryptoPaymentRequestApproved(UUID requestId) throws CantHandleCryptoPaymentRequestApprovedEventException {

        try {

            cryptoPaymentRequestDao.changeState(
                    requestId,
                    CryptoPaymentState.APPROVED
            );

            cryptoPaymentRequestManager.confirmRequest(requestId);

        } catch(CantChangeCryptoPaymentRequestStateException |
                CryptoPaymentRequestNotFoundException        e) {

            throw new CantHandleCryptoPaymentRequestApprovedEventException(e, "RequestId: "+requestId, "Error trying to change the state.");
        } catch(CantConfirmRequestException |
                RequestNotFoundException    e) {
            // TODO what to do in case i didn't find the request.
            throw new CantHandleCryptoPaymentRequestApprovedEventException(e, "RequestId: "+requestId, "Error in network service.");
        }
    }

    public void handleCryptoPaymentRequestDenied(UUID requestId) throws CantHandleCryptoPaymentRequestDeniedEventException {

        try {

            cryptoPaymentRequestDao.changeState(
                    requestId,
                    CryptoPaymentState.DENIED_BY_INCOMPATIBILITY
            );

            cryptoPaymentRequestManager.confirmRequest(requestId);

        } catch(CantChangeCryptoPaymentRequestStateException |
                CryptoPaymentRequestNotFoundException        e) {

            throw new CantHandleCryptoPaymentRequestDeniedEventException(e, "RequestId: "+requestId, "Error trying to change the state.");
        } catch(CantConfirmRequestException |
                RequestNotFoundException    e) {
            // TODO what to do in case i didn't find the request.
            throw new CantHandleCryptoPaymentRequestDeniedEventException(e, "RequestId: "+requestId, "Error in network service.");
        }
    }

    public void handleCryptoPaymentRequestRefused(UUID requestId) throws CantHandleCryptoPaymentRequestRefusedEventException {

        try {

            cryptoPaymentRequestDao.changeState(
                    requestId,
                    CryptoPaymentState.REFUSED
            );

            cryptoPaymentRequestManager.confirmRequest(requestId);

        } catch(CantChangeCryptoPaymentRequestStateException |
                CryptoPaymentRequestNotFoundException        e) {

            throw new CantHandleCryptoPaymentRequestRefusedEventException(e, "RequestId: "+requestId, "Error trying to change the state.");
        } catch(CantConfirmRequestException |
                RequestNotFoundException    e) {
            // TODO what to do in case i didn't find the request.
            throw new CantHandleCryptoPaymentRequestRefusedEventException(e, "RequestId: "+requestId, "Error in network service.");
        }
    }

}
