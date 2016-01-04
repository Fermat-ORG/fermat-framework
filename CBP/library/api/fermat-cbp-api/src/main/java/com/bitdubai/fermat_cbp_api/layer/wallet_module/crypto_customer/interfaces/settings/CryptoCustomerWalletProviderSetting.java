package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings;

import java.util.UUID;

/**
 * Created by franklin on 03/12/15.
 */
public interface CryptoCustomerWalletProviderSetting {
    //TODO: Documentar y manejo de excepciones
    UUID getId();
    void setId(UUID id);

    String getBrokerPublicKey();
    void   setBrokerPublicKey(String brokerPublicKey);

    UUID getPlugin();
    void setPlugin(UUID plugin);

    String getDescription();
    void setDescription(String description);
}
