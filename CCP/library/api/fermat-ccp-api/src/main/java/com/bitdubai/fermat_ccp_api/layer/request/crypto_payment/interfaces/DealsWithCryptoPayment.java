package com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces;

/**
 * The interface <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.DealsWithCryptoPayment</code>
 * indicates that the plugin needs the functionality of a CryptoPaymentManager.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DealsWithCryptoPayment {

    void setCryptoPaymentManager(CryptoPaymentManager cryptoPaymentManager);

}
