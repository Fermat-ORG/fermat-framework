package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequestManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentType;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantApproveCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGenerateCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGetCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantListCryptoPaymentRequestsException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantRejectCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CryptoPaymentRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPayment;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentRegistry;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure.CryptoPaymentRequestRegistry</code>
 * haves all the methods related with the crypto payment request negotiation.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/10/2015.
 */
public class CryptoPaymentRequestRegistry implements CryptoPaymentRegistry {

    private final CryptoPaymentRequestManager cryptoPaymentRequestManager;
    private final ErrorManager                errorManager               ;
    private final OutgoingIntraActorManager   outgoingIntraActorManager  ;
    private final PluginDatabaseSystem        pluginDatabaseSystem       ;
    private final UUID                        pluginId                   ;

    public CryptoPaymentRequestRegistry(final CryptoPaymentRequestManager cryptoPaymentRequestManager,
                                        final ErrorManager                errorManager               ,
                                        final OutgoingIntraActorManager   outgoingIntraActorManager  ,
                                        final PluginDatabaseSystem        pluginDatabaseSystem       ,
                                        final UUID                        pluginId                   ) {

        this.cryptoPaymentRequestManager = cryptoPaymentRequestManager;
        this.errorManager                = errorManager               ;
        this.outgoingIntraActorManager   = outgoingIntraActorManager  ;
        this.pluginDatabaseSystem        = pluginDatabaseSystem       ;
        this.pluginId                    = pluginId                   ;
    }

    @Override
    public void generateCryptoPaymentRequest(String walletPublicKey, String identityPublicKey, CryptoAddress cryptoAddress, String actorPublicKey, String description, long amount) throws CantGenerateCryptoPaymentRequestException {

    }

    @Override
    public void refuseRequest(UUID requestId) throws CantRejectCryptoPaymentRequestException, CryptoPaymentRequestNotFoundException {

    }

    @Override
    public void approveRequest(UUID requestId) throws CantApproveCryptoPaymentRequestException, CryptoPaymentRequestNotFoundException {

    }

    @Override
    public CryptoPayment getRequestById(UUID requestId) throws CantGetCryptoPaymentRequestException, CryptoPaymentRequestNotFoundException {
        return null;
    }

    @Override
    public List<CryptoPayment> listCryptoPaymentRequests(String walletPublicKey, Integer max, Integer offset) throws CantListCryptoPaymentRequestsException {
        return new ArrayList<>();
    }

    @Override
    public List<CryptoPayment> listCryptoPaymentRequestsByState(String walletPublicKey, CryptoPaymentState state, Integer max, Integer offset) throws CantListCryptoPaymentRequestsException {
        return new ArrayList<>();
    }

    @Override
    public List<CryptoPayment> listCryptoPaymentRequestsByType(String walletPublicKey, CryptoPaymentType type, Integer max, Integer offset) throws CantListCryptoPaymentRequestsException {
        return new ArrayList<>();
    }

    @Override
    public List<CryptoPayment> listCryptoPaymentRequestsByTypeAndState(String walletPublicKey, CryptoPaymentState state, CryptoPaymentType type, Integer max, Integer offset) throws CantListCryptoPaymentRequestsException {
        return new ArrayList<>();
    }
}
