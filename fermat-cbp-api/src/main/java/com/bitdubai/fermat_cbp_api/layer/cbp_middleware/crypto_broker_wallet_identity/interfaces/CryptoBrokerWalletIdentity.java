package com.bitdubai.fermat_cbp_api.layer.cbp_middleware.crypto_broker_wallet_identity.interfaces;


import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces.CryptoCustomerIdentity;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 16/9/15.
 */

public interface CryptoBrokerWalletIdentity{

    CryptoCustomerIdentity getIdentity();

    List<UUID> getListWallets();

}
