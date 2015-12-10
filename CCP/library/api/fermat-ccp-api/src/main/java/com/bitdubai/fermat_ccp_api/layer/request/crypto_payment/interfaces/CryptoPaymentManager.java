package com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGetCryptoPaymentRegistryException;

/**
 * The interface <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentManager</code>
 * provide the methods to make use of the crypto payment requests features.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/10/2015.
 */
public interface CryptoPaymentManager extends FermatManager {

    CryptoPaymentRegistry getCryptoPaymentRegistry() throws CantGetCryptoPaymentRegistryException;

}
