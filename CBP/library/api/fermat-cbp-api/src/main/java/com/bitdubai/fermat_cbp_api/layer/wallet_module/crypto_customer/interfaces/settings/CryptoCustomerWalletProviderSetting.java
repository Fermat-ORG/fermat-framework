package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by franklin on 03/12/15.
 * Updated by Nelson Ramirez on 09/02/16.
 */
public interface CryptoCustomerWalletProviderSetting extends Serializable {
    //TODO: Documentar y manejo de excepciones
    UUID getId();

    void setId(UUID id);

    String getCustomerPublicKey();

    void setCustomerPublicKey(String customerPublicKey);

    UUID getPlugin();

    void setPlugin(UUID plugin);

    String getDescription();

    void setDescription(String description);

    Currency getCurrencyFrom();

    Currency getCurrencyTo();

    void setCurrencyFrom(Currency currencyFrom);

    void setCurrencyTo(Currency currencyTo);
}
