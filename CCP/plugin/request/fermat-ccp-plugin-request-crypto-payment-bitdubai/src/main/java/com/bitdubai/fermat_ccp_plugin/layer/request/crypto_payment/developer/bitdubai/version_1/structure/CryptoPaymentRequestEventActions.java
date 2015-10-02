package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequestManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure.CryptoPaymentRequestEventActions</code>
 * contains all the methods related with the handling of the events raised by the crypto payment request network service.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/10/2015.
 */
public class CryptoPaymentRequestEventActions {

    private final CryptoPaymentRequestManager cryptoPaymentRequestManager;
    private final ErrorManager                errorManager               ;
    private final PluginDatabaseSystem        pluginDatabaseSystem       ;
    private final UUID                        pluginId                   ;

    public CryptoPaymentRequestEventActions(final CryptoPaymentRequestManager cryptoPaymentRequestManager,
                                            final ErrorManager                errorManager               ,
                                            final PluginDatabaseSystem        pluginDatabaseSystem       ,
                                            final UUID                        pluginId                   ) {

        this.cryptoPaymentRequestManager = cryptoPaymentRequestManager;
        this.errorManager                = errorManager               ;
        this.pluginDatabaseSystem        = pluginDatabaseSystem       ;
        this.pluginId                    = pluginId                   ;
    }

    public void handleCryptoPaymentRequestApproved(UUID requestId){

    }

    public void handleCryptoPaymentRequestDenied(UUID requestId){

    }

    public void handleCryptoPaymentRequestRefused(UUID requestId){

    }
}
