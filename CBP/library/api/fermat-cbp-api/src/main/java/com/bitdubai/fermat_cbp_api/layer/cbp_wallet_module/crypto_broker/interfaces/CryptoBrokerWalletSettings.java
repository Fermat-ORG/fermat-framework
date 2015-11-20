package com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces;

import java.util.List;

/**
 * Created by angel on 17/9/15.
 */
public interface CryptoBrokerWalletSettings {

    String getReferenceCurrency();

    List<String> getMerchandiseAvailable();

    List<String> getAvailablePaymentMethods();

    List<String> getReferenceRates();

    List<String> getPublicInformation();
}
