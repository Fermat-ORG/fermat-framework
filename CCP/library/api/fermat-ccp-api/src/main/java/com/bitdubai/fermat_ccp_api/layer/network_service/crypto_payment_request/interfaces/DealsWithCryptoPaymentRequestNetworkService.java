package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces;

/**
 * The interface <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.DealsWithCryptoPaymentRequestNetworkService</code>
 * indicates that the plugin needs the functionality of a CryptoPaymentRequestManager.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DealsWithCryptoPaymentRequestNetworkService {

    void setCryptoPaymentRequestManager(CryptoPaymentRequestManager cryptoPaymentRequestManager);

}
